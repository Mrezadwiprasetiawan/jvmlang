plugins {
  id("c")
}

group = "id.pras.jvmlang.bytecode"
version = "1.0.0"


dependencies {
    // Tambahkan dependensi di sini, misalnya:
    // implementation("org.example:other-library:1.0.0")
}

tasks {
    val build by getting {
        // Aturan build kustom jika diperlukan
    }
}