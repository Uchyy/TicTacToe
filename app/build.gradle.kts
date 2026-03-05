plugins {
    id("com.android.application")
    id ("com.google.gms.google-services")
}

android {
    namespace = "com.meshach.tictactoe"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.meshach.tictactoe"
        minSdk = 26
        targetSdk = 35
        versionCode = 2
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        create("customDebugType") {
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.navigation:navigation-fragment:2.9.7")
    implementation("androidx.navigation:navigation-ui:2.9.7")
    implementation("com.google.android.gms:play-services-ads:25.0.0")
    implementation("androidx.preference:preference:1.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    androidTestImplementation("androidx.test:runner:1.7.0")
    androidTestImplementation("androidx.test:rules:1.7.0")
    androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.7.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    implementation(platform("com.google.firebase:firebase-bom:34.10.0"))
    implementation("com.google.firebase:firebase-analytics")
}