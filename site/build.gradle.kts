@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import org.gradle.api.tasks.Copy
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
}

group = "xyz.malefic.guptarealty"
version = "1.0.0"

val localProperties =
    Properties().apply {
        val file = rootProject.file("local.properties")
        if (file.exists()) file.inputStream().use(::load)
    }

kobweb {
    pagesPackage = "xyz.malefic.guptarealty.client.pages"
    app {
        index {
            description.set("Gupta Real Estate - Your Orange County Realtor")
            head.add {
                link(
                    rel = "stylesheet",
                    href =
                        "https://fonts.googleapis.com/css2?family=Cinzel:wght@400;600;700&family=Plus+Jakarta+Sans:ital,wght@0,400;0,500;0,600;1,400&display=swap",
                )
                link(
                    rel = "stylesheet",
                    href = "https://uicdn.toast.com/editor/latest/toastui-editor.min.css",
                )
            }
        }
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }

    configAsKobwebApplication("guptarealty")

    jvm {
        mainRun {
            mainClass = "xyz.malefic.guptarealty.server.MainKt"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kermit)
        }

        jsMain.dependencies {
            implementation(npm("@js-joda/timezone", "2.25.1"))
            implementation(npm("@toast-ui/editor", "3.2.2"))
            implementation(npm("minisearch", "7.2.0"))
            implementation(libs.bundles.compose)
            implementation(libs.bundles.kobweb)
            implementation(libs.kutint)
            implementation(wrappers.js)
        }

        jvmMain.dependencies {
            implementation(libs.bundles.http4k)
            compileOnly(libs.kobweb.api)
        }
    }

    compilerOptions {
        optIn.add("kotlin.uuid.ExperimentalUuidApi")
    }
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JVM_21)
    }
}

val jvmJar = tasks.named<Jar>("jvmJar")
val dockerRuntime =
    tasks.register<Copy>("dockerRuntime") {
        description = "Prepares the application for Docker by copying the necessary files into a build directory."

        dependsOn(jvmJar)
        dependsOn("compileProductionExecutableKotlinJs")
        dependsOn("jsBrowserDistribution")
        dependsOn("kobwebExport")

        into(layout.buildDirectory.dir("docker"))

        from(jvmJar) {
            rename { "app.jar" }
        }

        from(configurations.getByName("jvmRuntimeClasspath")) {
            into("lib")
        }

        from(layout.buildDirectory.dir("site/export")) {
            into("site/static")
        }

        from(layout.buildDirectory.dir("dist/js/productionExecutable")) {
            include("*.js", "*.js.map", "*.js.LICENSE.txt")
            into("site/static")
        }

        from(layout.buildDirectory.dir("dist/js/productionExecutable/public")) {
            into("site/static")
        }
    }

configurations.configureEach {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlin" && requested.name.startsWith("kotlin-test")) {
            useVersion(libs.versions.kotlin.get())
        }
    }
}

tasks.named("build") {
    dependsOn(dockerRuntime)
}

afterEvaluate {
    afterEvaluate {
        tasks.named<JavaExec>("jvmRun") {
            dependsOn(dockerRuntime)
            (localProperties["FUB_API_KEY"] ?: System.getenv("FUB_API_KEY"))?.let {
                systemProperty("FUB_API_KEY", it)
            }
            (localProperties["BEARER_TOKEN"] ?: System.getenv("BEARER_TOKEN"))?.let {
                systemProperty("BEARER_TOKEN", it)
            }
        }
    }
}
