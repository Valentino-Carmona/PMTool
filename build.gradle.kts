plugins {
    // Define el plugin de Java para compilar proyectos en Java
    id("java")
    // Plugin para verificar estándares de código Java
    id("checkstyle")
    id("jacoco")
    id("com.diffplug.spotless") version "6.25.0"
    id("pmd")
}

repositories {
    // Usa Maven Central para las dependencias
    mavenCentral()
}

allprojects {
    repositories {
        // Todas las dependencias se resuelven usando Maven Central
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "checkstyle")
    apply(plugin = "jacoco")
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "pmd")

    dependencies {
        // Dependencia de JUnit 5 para pruebas
        testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")

        // Dependencias de Cucumber
        testImplementation("io.cucumber:cucumber-java:7.14.0")
        testImplementation("io.cucumber:cucumber-junit:7.14.0")
        testImplementation("io.cucumber:cucumber-junit-platform-engine:7.14.0")
        
        // Necesario para ejecutar Cucumber con JUnit 5
        testImplementation("org.junit.platform:junit-platform-suite-api:1.10.0")
        testImplementation("org.junit.platform:junit-platform-suite:1.10.0")
        testRuntimeOnly("org.junit.platform:junit-platform-suite-engine:1.10.0")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
        reports {
            html.required.set(true)
        }
    }

    tasks.register<Test>("cucumberTest") {
        useJUnitPlatform {
            includeTags("cucumber")
        }
        description = "Ejecuta solo los tests de Cucumber"
        group = "verification"

        include("**/*CucumberTest*")
    
        testLogging {
            events("passed", "skipped", "failed")
            showStandardStreams = true
        }
        
        reports {
            html.required.set(true)
        }
        
        systemProperty("cucumber.glue", "org.pmtool.steps")
        systemProperty("cucumber.plugin", "pretty,json:build/reports/cucumber/cucumber.json")
        systemProperty("cucumber.features", "src/test/resources/features/")
        systemProperty("cucumber.publish.quiet", "true")
        
        // Asegurarse de que los tests de Cucumber se ejecuten
        testClassesDirs = files(project.sourceSets.getByName("test").output.classesDirs)
        classpath = project.sourceSets.getByName("test").runtimeClasspath
    }

    // Configuración de Checkstyle
    checkstyle {
        toolVersion = "10.12.5"
        configFile = rootProject.file("config/checkstyle/checkstyle.xml")
        isIgnoreFailures = true
        isShowViolations = true
    }

    spotless {
        java {
            googleJavaFormat()
            removeUnusedImports()
            trimTrailingWhitespace()
            endWithNewline()
        }
        kotlin {
            ktlint()
        }
    }

    pmd {
        toolVersion = "6.55.0"
        isIgnoreFailures = false
        ruleSets = listOf()
        ruleSetFiles = files("config/pmd/ruleset.xml")
    }

    tasks.withType<Pmd>().configureEach {
        reports {
            xml.required.set(false)
            html.required.set(true)
        }
    }

    // Configuración de JaCoCo para codecov
    jacoco {
        toolVersion = "0.8.11"
    }

    tasks.test {
        finalizedBy(tasks.jacocoTestReport)
    }

    tasks.jacocoTestReport {
        dependsOn(tasks.test)
        reports {
            xml.required.set(true)
            html.required.set(true)
        }
        finalizedBy("jacocoTestCoverageVerification")
    }

    tasks.jacocoTestCoverageVerification {
        dependsOn(tasks.jacocoTestReport)
        violationRules {
            rule {
                limit {
                    minimum = "0.80".toBigDecimal()
                }
            }
        }
    }

    tasks.register("validate") {
        group = "verification"
        description = "Ejecuta todas las validaciones de código"
        dependsOn(
            tasks.checkstyleMain,
            tasks.checkstyleTest,
            tasks.spotlessApply,
            tasks.spotlessCheck,
            tasks.pmdMain,
            tasks.pmdTest
        )
    }
}
