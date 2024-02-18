import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

private val envProp by lazy {
    Properties().apply {
        load(FileInputStream(File("env.properties")))
    }
}

android {
    namespace = "com.arif.pokemonlist"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.arif.pokemonlist"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }


    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        all {
            val baseUrl = "BASE_URL"
            val imageUrl = "IMAGE_URL"
            buildConfigField(
                "String",
                baseUrl,
                String.format("\"%s\"", envProp.getProperty(baseUrl))
            )
            buildConfigField(
                "String",
                imageUrl,
                String.format("\"%s\"", envProp.getProperty(imageUrl))
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    allprojects {
        tasks.withType(JavaCompile::class.java) {
            options.compilerArgs.add("-Xlint:deprecation")
        }
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests {
            all {
                it.useJUnitPlatform()
            }
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Coroutines
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")

    //retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    //ktx
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")

    //okhttp
    val okhttpVersion = "4.10.0"
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")

    // GSON
    implementation("com.google.code.gson:gson:2.10.1")

    // Hilt
    val hiltVersion = "2.50"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")

    // Paging 3
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")

    // Room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-paging:${roomVersion}")
    ksp("androidx.room:room-compiler:$roomVersion")

    // Mockk
    testImplementation("io.mockk:mockk:1.12.4")

    // JUnit 5
    testImplementation(kotlin("test-junit5"))
    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("androidx.arch.core:core-testing:2.2.0")
}

kapt {
    correctErrorTypes = true
}