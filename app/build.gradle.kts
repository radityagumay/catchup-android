plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AppVersions.targetSdk
    buildToolsVersion = AppVersions.buildVersion

    defaultConfig {
        applicationId = "com.radityalabs"
        minSdk = AppVersions.minSdk
        targetSdk = AppVersions.targetSdk
        versionCode = AppVersions.versionCode
        versionName = AppVersions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    buildFeatures {
        viewBinding = true
    }
    packagingOptions {
        resources {
            excludes.add("META-INF/{AL2.0,LGPL2.1}")
            excludes.add("META-INF/atomicfu.kotlin_module")
        }
    }
}

kapt {
    // For Hilt Setup
    // https://dagger.dev/hilt/gradle-setup
    correctErrorTypes = true
}

hilt {
    // The Hilt configuration option 'enableTransformForLocalTests'
    // is no longer necessary when com.android.tools.build:gradle:4.2.0+ is used.
    // enableTransformForLocalTests = true
    enableAggregatingTask = true

    // see
    // https://github.com/google/dagger/issues/1991
    // https://github.com/google/dagger/issues/970
    enableExperimentalClasspathAggregation = true
}

dependencies {
    // Androidx
    Deps.Androidx.list.forEach(::implementation)

    // Accompanist
    Deps.Accompanist.list.forEach(::implementation)

    // Commons
    Deps.Common.list.forEach(::implementation)

    // Retrofit
    Deps.Network.list.forEach(::implementation)

    // Room
    implementation(Deps.Room.ktx)
    implementation(Deps.Room.runtime)
    add("kapt", Deps.Room.compiler)
    testImplementation(Deps.Room.testing)

    // Hilt
    implementation(Deps.Dagger.hiltAndroid)
    kapt(Deps.Dagger.hiltCompiler)
    kapt(Deps.Dagger.hiltAndroidCompiler)

    // For instrumentation tests
    androidTestImplementation(Deps.Dagger.hiltAndroidTesting)
    kaptAndroidTest(Deps.Dagger.hiltCompiler)

    // For local unit tests
    testImplementation(Deps.Dagger.hiltAndroidTesting)
    kaptTest(Deps.Dagger.hiltCompiler)

    Deps.UnitTesting.list.forEach(::testImplementation)

    androidTestImplementation(Deps.UiTesting.androidxJUnit)
    androidTestImplementation(Deps.UiTesting.androidxEspresso)
}