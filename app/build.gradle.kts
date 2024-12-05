plugins {
    id("com.android.application")
    kotlin("android") version "1.8.22"
}

android {
    namespace = "kr.pknu.roulletepractice"
    compileSdk = 34

    defaultConfig {
        applicationId = "kr.pknu.roulletepractice"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
    packagingOptions {
        exclude("META-INF/androidx.localbroadcastmanager_localbroadcastmanager.version")
        exclude("META-INF/androidx.appcompat_appcompat.version")
        exclude("META-INF/androidx.core_core.version")
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.5.0")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Naver Map API 추가
    implementation("com.naver.maps:map-sdk:3.13.0")

    // LuckyWheel 라이브러리 추가
    implementation("com.github.mmoamenn:LuckyWheel_Android:0.3.0") {
        exclude(group = "com.android.support")
        exclude(group = "androidx.core")
    }

    // Kotlin 표준 라이브러리 추가
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.22")
}
