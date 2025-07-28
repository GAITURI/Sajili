import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

//URL OF DEPLOYED SPRING BOOT BACKEND ON RAILWAY needs to be rectified
const val BASE_URL= "postgresql://postgres:WRwyNDHKOhUVrtpUlmTkuoIuBTLjKjKg@postgres.railway.internal:5432/railway"
class SajiliApiException(
    message:String,
    val statusCode:Int? = null,
    val errorBody:ErrorResponse?= null,
    cause:Throwable?= null
):Exception(message, cause)
object RetrofitClient{
    private val gson:Gson= GsonBuilder()
        .setLenient()
        .create()
//A login interceptor for debugging network requests
private val loggingInterceptor= HttpLoggingInterceptor().apply {
    level= HttpLoggingInterceptor.Level.BODY

}

//interceptor to add JWT Token  to authenticated requests
 //this interceptor needs a way to get the current token but for now we'll make it a lambda for the token
    fun createAuthInterceptor(getToken: () -> String):Interceptor{
        return Interceptor { chain ->
            val originalRequest= chain.request()
            val token= getToken() //dynamically get the token from

            val requestBuilder= originalRequest.newBuilder()
            if (!token.isNullOrBlank()){
                requestBuilder.header("Authorization", "Bearer $token")
            }
                chain.proceed(requestBuilder.build())
        }
    }
    //create a function to create and configure OKHttpClient dynamically based on whether a token is available or not

    fun createOkHttpClient(getToken: () -> String= {null}): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) //add logging for debugging
            .addInterceptor(createAuthInterceptor(getToken)) //add JWT interceptor
            .connectTimeout(30,TimeUnit.SECONDS) //Connection timeout
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build()

    }
//initialize Lazy
//the val api: Api by lazy{}
//this declares a property named authService of type AuthService(API interface)
//the lazy delegate optimizes performance by avoiding unnecessary setup during app startup
//the retrofit.builder() is responsible for building the retrofit instance and creating the API Service
//.addConverterFactory, adds the Gson converter factory to the retrofit instance
val authService:AuthService by lazy{
  getApiService()
}
 //function to get API Service, allowing dynamic token provision for authenticated calls
 fun getApiService(getToken: () -> String= {null}) :AuthService{
     val okHttpClient= createOkHttpClient(getToken)
     return Retrofit.Builder()
         .baseUrl(BASE_URL)
         .client(okHttpClient)
         .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create(gson))
         .build()
         .create(AuthService::class.java)
 }
    //lets handle a few errors for retrofit responses

    suspend fun <T>  safeApiCall(
        apiCall: suspend()-> Response<T>
    ):Result<T>{

    }
}