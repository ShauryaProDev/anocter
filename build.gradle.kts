plugins {
    id("java")
}

group = "com.anocter"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.openpnp:opencv:4.9.0-0")
    implementation("io.github.cdimascio:dotenv-java:3.2.0")
    implementation("com.google.code.gson:gson:2.14.0")

    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
}

tasks.withType<JavaExec> {
    systemProperty(
        "java.library.path",
        file("src/main/native").absolutePath
    )
}