import java.util.Properties

plugins {
  java
  `maven-publish`
  signing
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

val currentDir = projectDir.absolutePath
val sourcesDir = file("$currentDir/sources")
val javaDir = file("$currentDir/src/main/java/id/pras/jvmlang/bytecode")
val generatorDir = file("$projectDir/build/generator")

tasks.register<Copy>("javadocCopy") {
  dependsOn("javadoc")
  from("$buildDir/docs/javadoc")
  into("./javadocs")
}

tasks.register<Exec>("CompileGen") {
  commandLine(
    "${System.getenv("JAVA_HOME")}/bin/javac",
    "-d",
    "${generatorDir.absolutePath}1",
    "$javaDir/Gen.java"
  )
}

tasks.register<Exec>("CompileGen2") {
  dependsOn("CompileGen")
  commandLine(
    "${System.getenv("JAVA_HOME")}/bin/javac",
    "-d",
    "${generatorDir.absolutePath}2",
    "$javaDir/GenJavadocComment.java"
  )
}

val runGeneratorBaseTasks = sourcesDir.listFiles()?.mapIndexed { index, file ->
  val taskName = "runGeneratorBase$index"
  tasks.register(taskName, JavaExec::class.java) {
    classpath = files(file("${generatorDir.absolutePath}1"))
    mainClass.set("id.pras.jvmlang.bytecode.Gen")
    args = listOf(
      "-i", file.absolutePath,
      "-o", "$projectDir/src/main/java/id/pras/jvmlang/bytecode/${file.nameWithoutExtension}.java",
      "-p", "id.pras.jvmlang.bytecode",
      when (file.nameWithoutExtension) {
        "ConstantPool" -> "-cp"
        "Header" -> "-H"
        "AccessFlags" -> "-af"
        else -> ""
      }
    )
    if (index > 0) dependsOn("runGeneratorBase${index - 1}") else dependsOn("CompileGen2")
  }
  taskName
} ?: emptyList()

val runGeneratorJavadocTasks = sourcesDir.listFiles()?.mapIndexed { index, file ->
  val taskName = "runGeneratorJavadocTask$index"
  tasks.register(taskName, JavaExec::class.java) {
    classpath = files(file("${generatorDir.absolutePath}2"))
    mainClass.set("id.pras.jvmlang.bytecode.GenJavadocComment")
    args = listOf(
      "-i", "$projectDir/src/main/java/id/pras/jvmlang/bytecode/${file.nameWithoutExtension}.java",
      "-o", "$projectDir/src/main/java/id/pras/jvmlang/bytecode/${file.nameWithoutExtension}.java"
    )
    if (index > 0) dependsOn("runGeneratorJavadocTask${index - 1}") else dependsOn(runGeneratorBaseTasks)
  }
  taskName
} ?: emptyList()

tasks.register("GenJavaFiles") {
  dependsOn(runGeneratorJavadocTasks)
}

tasks.named<JavaCompile>("compileJava") {
  exclude("**/Gen.java", "**/GenJavadocComment.java")
}

tasks.named<Delete>("clean") {
  doFirst {
    val workfiles = fileTree(javaDir) {
      include("**/*.java")
      exclude("**/Gen.java", "**/GenJavadocComment.java")
    }
    delete(workfiles)
    delete("javadocs")
  }
}

// ------------------------------------
// PUBLISHING TO GITHUB CONFIGURATION
// ------------------------------------
object Meta {
  const val GROUP = "id.pras.jvmlang.bytecode"
  const val ARTIFACT_ID = "release-java"
  const val VERSION = "0.1.0"
  const val DESC = "jvmlang bytecode project"
  const val LICENSE = "Apache-2.0"
  const val LICENSE_URL = "https://opensource.org/licenses/Apache-2.0"
  const val GITHUB_REPO = "Mrezadwiprasetiawan/jvmlang"
  const val DEVELOPER_ID = "Mrezadwiprasetiawan"
  const val DEVELOPER_NAME = "M Reza Dwi Prasetiawan"
}

val githubUsername = readProperties("../local.properties","githubUsername") ?: System.getenv("GITHUB_USERNAME") ?: ""
val githubToken = readProperties("../local.properties","githubToken") ?: System.getenv("GITHUB_PASSWORD") ?: ""

tasks.register<Jar>("javadocJar") {
  dependsOn("javadoc")
  archiveClassifier.set("javadoc")
  from("$buildDir/docs/javadoc")
}

tasks.register<Jar>("sourcesJar") {
  archiveClassifier.set("sources")
  from(sourceSets.main.get().allSource)
}

publishing {
  publications {
    create<MavenPublication>("github") {
      groupId = Meta.GROUP
      artifactId = Meta.ARTIFACT_ID
      version = Meta.VERSION

      from(components["java"])
      artifact(tasks["javadocJar"])
      artifact(tasks["sourcesJar"])

      pom {
        name.set(Meta.ARTIFACT_ID)
        description.set(Meta.DESC)
        url.set("https://github.com/${Meta.GITHUB_REPO}")

        licenses {
          license {
            name.set(Meta.LICENSE)
            url.set(Meta.LICENSE_URL)
          }
        }

        developers {
          developer {
            id.set(Meta.DEVELOPER_ID)
            name.set(Meta.DEVELOPER_NAME)
          }
        }

        scm {
          connection.set("scm:git:git://github.com/${Meta.GITHUB_REPO}.git")
          developerConnection.set("scm:git:ssh://github.com/${Meta.GITHUB_REPO}.git")
          url.set("https://github.com/${Meta.GITHUB_REPO}")
        }
      }
    }
  }

  signing {
  
    useInMemoryPgpKeys(
      readProperties("../local.properties","sign.keyId") ?: System.getenv("SIGNING_KEY_ID"),
      readProperties("../local.properties","sign.key") ?: System.getenv("SIGNING_KEY"), 
      readProperties("../local.properties","sign.pass") ?: System.getenv("SIGNING_PASSWORD")
    )
    sign(publications["github"])
  }

  repositories {
    maven {
      name = "GitHubPackages"
      url = uri("https://maven.pkg.github.com/${Meta.GITHUB_REPO}")
      credentials {
        username = githubUsername
        password = githubToken
      }
    }
  }
}

fun readProperties(path: String, varname: String) = readProperties(file(path), varname)
fun readProperties(propertiesFile: File, varName: String): String? {
    return if (propertiesFile.exists()) {
        Properties().apply {
            propertiesFile.inputStream().use { fis ->
                load(fis)
            }
        }[varName] as String?
    } else {
        null // Kembalikan null jika file tidak ada
    }
}