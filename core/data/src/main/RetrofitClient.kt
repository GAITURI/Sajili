object RetrofitClient{
//URL OF DEPLOYED SPRING BOOT BACKEND ON RAILWAY

private const val BASE_URL= ""


//A login interceptor for debugging network requests
private val loggingInterceptor= HttpLoggingInterceptor().apply{
    level= HttpLoggingInterceptor.Level.BODY


    //Create an OkHttpClient with the logging intercptor
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}
//initialize Lazy
val authService:AuthService by lazy{
    retrofit.create(AuthService::class.jav)
}
}