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
      generatorDir.absolutePath+"1",
      "$javaDir/Gen.java"
    )
    standardOutput = System.out
    errorOutput = System.err
  }
  doLast {
    println("Compile success")
  }
}

tasks.register<Exec>("CompileGen2") {
  dependsOn("CompileGen")
  doFirst {
    println("Compiling Generator class")
    commandLine(
      "${System.getenv("JAVA_HOME")}/bin/javac",
      "-d",
      "${generatorDir.absolutePath}2",
      "$javaDir/GenJavadocComment.java"
    )
    standardOutput = System.out
    errorOutput = System.err
  }
  doLast {
    println("Compile success")
  }
}

var runGeneratorBaseTasks = ""
sourcesDir.listFiles()?.forEachIndexed { index, file ->
  val taskName = "runGeneratorBase"
  tasks.register("$taskName$index", JavaExec::class.java) {
    classpath = files(file("${generatorDir.absolutePath}1"))
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
    if (index == 0) dependsOn("CompileGen2") else dependsOn("$taskName${index-1}")
  }
  runGeneratorBaseTasks = "$taskName$index"
}

var runGeneratorJavadocTasks="";
sourcesDir.listFiles()?.forEachIndexed { index, file ->
  val taskName = "runGeneratorJavadocTask"
  tasks.register("$taskName$index", JavaExec::class.java) {
    classpath = files(file("${generatorDir.absolutePath}2"))
    mainClass.set("id.pras.jvmlang.bytecode.GenJavadocComment")
    args = listOf(
      "-i",
      "$projectDir/src/main/java/id/pras/jvmlang/bytecode/${file.nameWithoutExtension}.java",
      "-o",
      "$projectDir/src/main/java/id/pras/jvmlang/bytecode/${file.nameWithoutExtension}.java"
    )
    if (index == 0) dependsOn(runGeneratorBaseTasks) else dependsOn("$taskName${index-1}")
  }
  runGeneratorJavadocTasks = "$taskName$index"
}

tasks.register("GenJavaFiles"){
  dependsOn(runGeneratorJavadocTasks)
  doLast{
    println("generated successfully")
  }
}

tasks.named<JavaCompile>("compileJava"){
 exclude("**/Gen.java","**/GenJavadocComment.java")
}

tasks.named<Delete>("clean"){
  doFirst{
    println("start to cleaning generated java files")
    val workfiles=fileTree(javaDir){
      include("**/*.java")
      exclude("**/Gen.java")
      exclude("**/GenJavadocComment.java")
    }
    workfiles.forEach{ file->
      println("filename $file")
    }
    delete(workfiles)
  }
  doLast{
    println("successfully")
  }
}