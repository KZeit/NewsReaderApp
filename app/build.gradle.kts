plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.newsreaderapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.newsreaderapp"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("androidx.appcompat:appcompat:1.2.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation ("com.google.android.material:material:1.2.1")
    implementation ("androidx.recyclerview:recyclerview:1.1.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("androidx.room:room-runtime:2.2.5")
    annotationProcessor ("androidx.room:room-compiler:2.2.5")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("androidx.recyclerview:recyclerview:1.2.0")
    implementation ("androidx.room:room-runtime:2.4.2")
    annotationProcessor ("androidx.room:room-compiler:2.4.2")
    implementation ("androidx.room:room-ktx:2.4.2")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.8.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.10")
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation ("androidx.room:room-runtime:2.5.0")
    annotationProcessor ("androidx.room:room-compiler:2.5.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.google.android.material:material:1.4.0")
    implementation ("androidx.appcompat:appcompat:1.3.1")
    implementation ("androidx.drawerlayout:drawerlayout:1.1.1")
    }