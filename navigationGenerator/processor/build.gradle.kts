plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.6.21-1.0.5"
}

ksp {
    arg("autoserviceKsp.verify", "true")
    arg("autoserviceKsp.verbose", "true")
}

android {
    namespace = "composektx.navigationgenerator.processor"
    compileSdk = 32

    defaultConfig {
        minSdk = 23
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(project(":navigationGenerator:annotation"))
    implementation(kotlin("stdlib"))

    implementation("com.squareup:kotlinpoet:1.11.0")
    implementation("com.squareup:kotlinpoet-ksp:1.11.0")
    implementation("com.google.devtools.ksp:symbol-processing-api:1.6.21-1.0.5")
    implementation("com.google.auto.service:auto-service-annotations:1.0.1")

    ksp("dev.zacweers.autoservice.auto-service-ksp:1.0.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.4.32")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.4.8")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.4.8")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview"
}