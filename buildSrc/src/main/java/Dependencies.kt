object Dependencies {
    val kotlinStandardLibrary = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val ktx = "androidx.core:core-ktx:${Versions.ktx}"

    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    val coroutineViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"
    val coroutineLiveData =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}"

    // room db ------- can be grouped together as an enhancements
    val roomRunTime = "androidx.room:room-runtime:${Versions.roomVersion}"
    val roomKTX = "androidx.room:room-ktx:${Versions.roomVersion}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    val roomRxJava2 = "androidx.room:room-rxjava2:${Versions.roomVersion}"

    // network ----- can be grouped together as an enhancements
    val okHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpInterceptor}"
    val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    val retrofitRxJava2Adapter =
        "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:${Versions.retrofitRxJava2Adapter}"

    // di framework  ----- can be grouped together as an enhancements
    val koinAndroid = "org.koin:koin-android:${Versions.koin_version}"
    val koinScope = "org.koin:koin-android-scope:${Versions.koin_version}"
    val koinViewModel = "org.koin:koin-android-viewmodel:${Versions.koin_version}"


    // test   ----- can be grouped together as an enhancements
    val junit = "junit:junit:${Versions.junit}"
    val mockitoCore = "org.mockito:mockito-core:${Versions.unitTestCore}"
    val mockitoInline = "org.mockito:mockito-inline:${Versions.unitTestInline}"
    val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archCoreTesting}"
    val coroutineTesting = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutineTesting}"

    val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"


    // UI dependencies
    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"
    val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2}"
    val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val material = "com.google.android.material:material:${Versions.material}"
    val legacySupport = "androidx.legacy:legacy-support-v4:${Versions.legacySupport}"
}
