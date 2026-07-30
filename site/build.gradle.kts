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

val prepareStaticApi =
    tasks.register("prepareStaticApi") {
        description = "Prepares the static API files for the application by generating JSON files for home and blog data."

        val outputDir = layout.buildDirectory.dir("generated/static-api")
        outputs.dir(outputDir)

        doLast {
            val apiDir = outputDir.get().asFile.resolve("public/api")
            apiDir.mkdirs()

            val homeJson =
                """
                {
                  "hero": {
                    "title": "Results You'll Love, Without the Guesswork",
                    "subtitle": "I believe that achieving great results shouldn't come with a side of overwhelm. I’m here to streamline the entire process, giving you total clarity and confidence from day one.",
                    "image": "/Logo.jpg"
                  },
                  "stats": [
                    "Backed by #1 Independent Brokerage in CA",
                    "106,000+ clients served broker-wide",
                    "61+ office locations expanding across the US"
                  ],
                  "statsNotice": "Brokerage stats provided by FTRE based on 2025 year-end data.",
                  "help": {
                    "title": "How Can I Help?",
                    "description": "Whether you are a seller, buyer, or both, I'm here to guide you along every step of the process.",
                    "boxes": [
                      {
                        "image": "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.lgo.org.uk%2Fassets%2Finline%2F5856%2FWEB-Man-with-clipboard-knocking-on-door.jpg&f=1&nofb=1&ipt=f942d90fdfabc624fa58d7b327915b35e9ba626fcfaeaa053b0ea3a2efa76ecd",
                        "title": "Sellers",
                        "description": "Tailored strategies to maximize your return."
                      },
                      {
                        "image": "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.lgo.org.uk%2Fassets%2Finline%2F5856%2FWEB-Man-with-clipboard-knocking-on-door.jpg&f=1&nofb=1&ipt=f942d90fdfabc624fa58d7b327915b35e9ba626fcfaeaa053b0ea3a2efa76ecd",
                        "title": "Buyers",
                        "description": "Achieve homeownership—minus the guesswork."
                      }
                    ]
                  },
                  "about": {
                    "title": "Hi! I'm Ruchika",
                    "description": "I help buyers, and sellers navigate the OC real estate market with confidence — and I’ve been licensed since January 2022.\nOriginally from India, I’ve called OC home for the past 20 years and have lived in Anaheim since then.\n\nI’m known for being personable, organized, and honest — but also a strong advocate when it matters most.\nOutside of real estate, I’m a big traveler. When I’m not working, you can usually find me playing board games with my kids or finding a new coffee shop somewhere.",
                    "image": "https://lh3.googleusercontent.com/aida-public/AB6AXuCuwNDb-CwDlopOQd4M9z3qBsg47Jva-z3IKYTWAqKhXGqgBv2NtxGRCt-jSRpohSDwMsX40mGSIGNOz1apgYvVFiwjYWU-Hr9gDe9tl3LB2AtgcF9HBpYMqEc4hgpCT-QjcjVm9ziJAGwY14iXUG09Izkj-tWX-_1ms4BS2xhq1Lf7ZXLMJL9tpGfKAdYRfbEQb9HgLYxMpq20gtvpZPknpotYaCYkfxyGkojJSeOyL2LaDJEqrdnx7qKd-slF0Ub2NRLljwbqyEc"
                  },
                  "insta": {
                    "title": "The Unfiltered Version",
                    "description": "No perfectly staged content here — just home tours, market truths, and the real stories behind every OC deal. Come hang out on Instagram, where it's a lot more fun than a regular open house.",
                    "followLink": "https://www.instagram.com/ruchika.realtor/",
                    "posts": [
                      "https://www.instagram.com/p/DR49lRfAfe2/embed/",
                      "https://www.instagram.com/p/DaS1JrPv02w/embed/"
                    ]
                  },
                  "youtube": {
                    "title": "Learn Before You Leap",
                    "description": "Buying or selling a home comes with a lot of decisions — and a lot of ways to get it wrong. On my YouTube channel, I break down what actually matters: how to avoid costly pitfalls, what smart buyers and sellers do differently, and everything I wish more people knew before they signed on the dotted line.",
                    "followLink": "https://www.youtube.com/channel/UCbPMvIhONGrwsFiFZmu_sgg",
                    "posts": [
                      "https://youtube.com/embed/rgIC0NPFwyA?si=rapKvSrJKQEy0hDV?rel=0",
                      "https://youtube.com/embed/xeqV4rADEEM?si=xZ3v5wqfptIG8jy1?rel=0"
                    ]
                  },
                  "testimonial": {
                    "author": "Ruchika",
                    "quote": "Wow wowow owowow this is a rly long review that says a lot of good things and taht i hope you change rly quickly pls",
                    "imageSrc": "/Logo.jpg"
                  }
                }
                """.trimIndent()

            val blogJson =
                """
                [
                  {
                    "id": "00000000-0000-0000-0000-000000000001",
                    "title": "Market Trends 2026",
                    "summary": "Navigating the shifting landscape of Cheshire real estate this coming year...",
                    "content": "# THIS IS LOTS OF CONTENT YES YES YES YESYES YES ESYES ESE YS E\n## THIS IS MORE CONTENT!!!\n### EVEN MORE!!!!!",
                    "imageSrc": "https://lh3.googleusercontent.com/aida-public/AB6AXuCPQZL2d06Aa7tXDLhenIb7TMP3AlZsEgxvn6BmxXa4p8CtEwaidUKayGU9xEwH9DNifWfz76cqSg3eEZx3ACe5L_aESgI_5CrB58aCYrdz-AcUD1x3JBhNJ4UpA0kl1RreUGJ4mMwgSu0MvKiqsc_fLmAf_FIVlyC_1aoDZZPR9zDzQTXQNR8luWCnNXDlkAZhOQb8sjoVsZlpE6XwnmKj5Yis3rSucsMp1EIwL6HFctIC9ZMk_cUiYYCeiJweUgEFlFl5F-JItmY",
                    "tags": ["Market Update", "Buying", "Selling"],
                    "date": "2026-01-01"
                  }
                ]
                """.trimIndent()

            apiDir.resolve("home").writeText(homeJson)
            apiDir.resolve("blog").writeText(blogJson)
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

        jsMain {
            resources.srcDir(prepareStaticApi)
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
            exclude("api/**")
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
