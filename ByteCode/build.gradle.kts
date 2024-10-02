plugins {
  java
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

val currentDir = projectDir.absolutePath
val sourcesDir = file("$currentDir/sources")
val javaDir = file("$currentDir/src/main/java/id/pras/jvmlang/bytecode")
val generatorDir = file("$projectDir/build/generator")

tasks.register<Copy>("prepareGeneratorDir") {
  from(generatorDir)
  into(generatorDir)
}

tasks.register<Exec>("CompileGen") {
  doFirst {
    println("Compiling Generator class")
    commandLine(
      "${System.getenv("JAVA_HOME")}/bin/javac",
      "-d",
      generatorDir.absolutePath,
      "$javaDir/Gen.java"
    )
    standardOutput = System.out
    errorOutput = System.err
  }
  doLast {
    println("Compile success")
  }
}

var runGeneratorTasks = ""
sourcesDir.listFiles()?.forEachIndexed { index, file ->
  val taskName = "runGenerator"
  tasks.register("$taskName$index", JavaExec::class.java) {
    classpath = files(generatorDir)
    mainClass.set("id.pras.jvmlang.bytecode.Gen")
    args = listOf(
      "-i",
      file.absolutePath,
      "-o",
      "$projectDir/src/main/java/id/pras/jvmlang/bytecode/${file.nameWithoutExtension}.java",
      "-p",
      "id.pras.jvmlang.bytecode",
      when (file.nameWithoutExtension) {
        "ConstantPool" -> "-cp"
        "Header" -> "-H"
        "AccessFlags" -> "-af"
        else -> ""
      }
    )
    if (index == 0) dependsOn("CompileGen") else dependsOn("$taskName${index-1}")
  }
  runGeneratorTasks = "$taskName$index"
}

tasks.register("GenJavaFiles"){
  dependsOn(runGeneratorTasks)
  doLast{
    println("generated successfully")
  }
}