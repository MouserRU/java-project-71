plugins {
    id("java")
    id ("com.github.ben-manes.versions") version "0.52.0"
    id ("application")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.13.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

// Для плагина application указываем главный класс приложения
application {
    // Входная точка
    mainClass.set("hexlet.code.App")
}