plugins {
    application
    id("com.github.ben-manes.versions") version "0.52.0"
    checkstyle
    jacoco
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

repositories {
    mavenCentral()
}

checkstyle {
    toolVersion = "11.0.0"
}

dependencies {
    // Main implementation dependencies
    implementation("info.picocli:picocli:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.3")

    // Annotation processors
    annotationProcessor("info.picocli:picocli-codegen:4.7.7")

    // Test dependencies
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("started", "passed", "skipped", "failed")
    }
    finalizedBy(tasks.jacocoTestReport)
}

application {
    mainClass = "hexlet.code.App"
}

jacoco {
    toolVersion = "0.8.13"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
        html.required = true
        csv.required = false
        xml.outputLocation = layout.buildDirectory.file("reports/jacoco/test.xml")
        html.outputLocation = layout.buildDirectory.dir("reports/jacoco/html")
    }
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.jacocoTestReport)
    violationRules {
        rule {
            limit {
                counter = "LINE"
                minimum = "0.5".toBigDecimal()
            }
        }
    }
    classDirectories.setFrom(tasks.jacocoTestReport.get().classDirectories)
}