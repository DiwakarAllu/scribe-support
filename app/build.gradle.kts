plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.scribesupport"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.scribesupport"
        minSdk = 22
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
implementation(libs.firebase.storage)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)


    //
//    implementation(libs.appcompat)
//    implementation(libs.material)
//    implementation(libs.activity)
//    implementation(libs.constraintlayout)
//    implementation(libs.firebase.database)
//    implementation(libs.firebase.auth)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.ext.junit)
//    androidTestImplementation(libs.espresso.core)

    dependencies {
        implementation("com.github.clans:fab:1.6.4") // Clans FloatingActionButton library
        implementation("androidx.appcompat:appcompat:1.6.1") // AppCompat library
        implementation("com.google.android.material:material:1.11.0") // Material Components library
        implementation("androidx.activity:activity:1.8.2") // Activity library
        implementation("androidx.constraintlayout:constraintlayout:2.1.4") // ConstraintLayout library
        implementation("com.google.firebase:firebase-database:20.3.1") // Firebase Realtime Database library
        implementation("com.google.firebase:firebase-auth:22.3.1") // Firebase Authentication library
        testImplementation("junit:junit:4.+") // JUnit library for tests
        androidTestImplementation("androidx.test.ext:junit:1.1.5") // JUnit library for Android tests
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Espresso library for UI tests

        implementation("com.github.bumptech.glide:glide:4.14.2")

            // Import the BoM for the Firebase platform
            implementation(platform("com.google.firebase:firebase-bom:32.8.1"))

            // Add the dependency for the Firebase Authentication library
            // When using the BoM, you don't specify versions in Firebase library dependencies
            implementation("com.google.firebase:firebase-auth")



        implementation ("androidx.recyclerview:recyclerview:1.2.1")
        implementation ("androidx.legacy:legacy-support-v4:1.0.0")
        implementation ("com.firebaseui:firebase-ui-database:8.0.1")

//            implementation ("com.etebarian:meow-bottom-navigation:1.3.1")

        implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.61")
        implementation ("com.google.android.material:material:1.3.0-alpha03")


     
        implementation("net.objecthunter:exp4j:0.4.8")



    }

}