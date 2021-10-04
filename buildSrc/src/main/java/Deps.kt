object Deps {
    object Androidx {
        const val appCompat = "androidx.appcompat:appcompat:1.3.1"
        const val annotation = "androidx.annotation:annotation:1.2.0"

        const val coreKtx = "androidx.core:core-ktx:1.6.0"

        const val lifeCycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.0"

        const val material = "com.google.android.material:material:1.4.0"

        const val lifeCycleExtension = "androidx.lifecycle:lifecycle-extensions:2.2.0"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.3.6"
        const val activityKtx = "androidx.activity:activity-ktx:1.3.1"
        const val lifeCycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:2.4.0-alpha03"

        val list = listOf(
            appCompat, annotation, coreKtx, lifeCycleRuntime, constraintLayout,
            material, lifeCycleExtension, fragmentKtx, activityKtx, lifeCycleLiveData
        )
    }

    object Dagger {
        const val hiltAndroid = "com.google.dagger:hilt-android:2.38.1"
        const val hiltCompiler = "com.google.dagger:hilt-compiler:2.38.1"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:2.38.1"

        const val hiltAndroidTesting = "com.google.dagger:hilt-android-testing:2.38.1"
    }

    object Room {
        const val roomVersion = "2.3.0"

        const val ktx = "androidx.room:room-ktx:$roomVersion"
        const val runtime = "androidx.room:room-runtime:$roomVersion"
        const val compiler = "androidx.room:room-compiler:$roomVersion"
        const val testing = "androidx.room:room-testing:$roomVersion"
    }

    object Network {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:2.9.0"
        const val okhttp = "com.squareup.okhttp3:okhttp:4.8.1"
        const val log = "com.squareup.okhttp3:logging-interceptor:4.8.1"
        val list = listOf(retrofit, retrofitConverterGson, okhttp, log)
    }

    object Accompanist {
        const val insets = "com.google.accompanist:accompanist-insets:0.15.0"
        const val coil = "com.google.accompanist:accompanist-coil:0.15.0"
        const val pager = "com.google.accompanist:accompanist-pager:0.15.0"
        const val pagerIndicators = "com.google.accompanist:accompanist-pager-indicators:0.15.0"
        const val systemuicontroller =
            "com.google.accompanist:accompanist-systemuicontroller:0.15.0"

        val list = listOf(insets, coil, pager, pagerIndicators, systemuicontroller)
    }

    object Common {
        const val flowBinding =
            "io.github.reactivecircus.flowbinding:flowbinding-android:1.0.0-alpha04"

        val list = listOf(flowBinding)
    }

    object UnitTesting {
        const val junit = "junit:junit:4.13.2"
        const val mockito = "org.mockito.kotlin:mockito-kotlin:3.2.0"
        const val mockitoCore = "org.mockito:mockito-core:3.10.0"
        const val coroutineTest =  "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1"
        const val turbin = "app.cash.turbine:turbine:0.6.0"

        val list = listOf(junit, mockito, mockitoCore, coroutineTest, turbin)
    }

    object UiTesting {
        const val androidxJUnit = "androidx.test.ext:junit:1.1.3"
        const val androidxEspresso = "androidx.test.espresso:espresso-core:3.4.0"
    }
}