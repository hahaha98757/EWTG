import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "kr.hahaha98757"
version = "2.0-remake"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:26.0.2")
    implementation("org.apache.poi:poi:5.4.1")
    implementation("org.apache.poi:poi-ooxml:5.4.1")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.0")
}

tasks.named<Jar>("jar") { isEnabled = false }

tasks.named<ShadowJar>("shadowJar") {
    archiveBaseName.set("EWTG")
    archiveClassifier.set("")
    manifest { attributes["Main-Class"] = "kr.hahaha98757.ewtg.MainFrame" }
}

tasks.register<Exec>("packageExe") {
    commandLine(
        "jpackage",
        "--type", "app-image",
        "--input", "build/libs",
        "--name", "EWTG-$version",
        "--main-jar", "EWTG-$version.jar",
        "--dest", "build/jpackage",
        "--win-console"
    )
    doLast { file("build/jpackage/EWTG-$version/EWTG-$version.ico").delete() }
}

tasks.named("build") {
    dependsOn("shadowJar")
    finalizedBy("packageExe")
}