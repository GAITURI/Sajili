//package com.mark.data
//
//import androidx.test.espresso.core.internal.deps.dagger.Module
//import androidx.test.espresso.core.internal.deps.dagger.Provides
//import com.google.gson.Gson
//import com.google.gson.GsonBuilder
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import okhttp3.Interceptor
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.util.concurrent.TimeUnit
//import javax.inject.Named
//import javax.inject.Singleton
//import kotlin.Exception
//
////URL OF DEPLOYED SPRING BOOT BACKEND ON RAILWAY needs to be rectified
//
//
//
////a singleton object
//// A singleton ensures there is only one instance of this class throughout the application's lifecycle, which is ideal for managing shared resources like a network client.
//object RetrofitClient{
//    //converting a JSON to kotlin objects and vice versa
//
////A login interceptor for debugging network requests an instance of  HttpLogging interceptor
//private val loggingInterceptor = HttpLoggingInterceptor().apply {
//    level = HttpLoggingInterceptor.Level.BODY
//
//}
//
////interceptor to add JWT Token  to authenticated requests
// //this interceptor needs a way to get the current token but for now we'll make it a lambda for the token
//    //the token is retrieved dynamically via the getToken lambda
//    fun createAuthInterceptor(getToken: () -> String): Interceptor {
//        //this uses a lambda expression to create an instance of the interceptor functional interface
//        //the chain object provides access to the request being processes
//        return Interceptor { chain ->
//            val originalRequest= chain.request()
//            val token= getToken() //dynamically get the token from
//
//            val requestBuilder= originalRequest.newBuilder()
//            if (!token.isNullOrBlank()){
//                requestBuilder.header("Authorization", "Bearer $token")
//            }
//            //build the modified request from the requestbuilder
//            //passes the new request to chain.proveed
//                chain.proceed(requestBuilder.build())
//        }
//    }
//    //create a function to create and configure OKHttpClient dynamically based on whether a token is available or not
//    //it builds an OKHttp client, adds the logginginterceptor and the authentication interceptor
//    //it also sets connection, read and write timeouts to prevent requests from stalling
//    fun createOkHttpClient(getToken: () -> String? = { null }): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor) //add logging for debugging
//            .addInterceptor(createAuthInterceptor{getToken()?:""}) //add JWT interceptor
//            .connectTimeout(30,TimeUnit.SECONDS) //Connection timeout
//            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
//            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
//            .build()
//
//    }
////initialize Lazy
////the val api: Api by lazy{}
////this declares a property named authService of type AuthService(API interface)
////the lazy delegate optimizes performance by avoiding unnecessary setup during app startup
////the retrofit.builder() is responsible for building the retrofit instance and creating the API Service
////.addConverterFactory, adds the Gson converter factory to the retrofit instance
//
//val authService: AuthService by lazy{
//  getApiService()
//}
// //function to get API Service, allowing dynamic token provision for authenticated calls
// fun getApiService(getToken: () -> String? = {null}) : AuthService {
//     val okHttpClient= createOkHttpClient(getToken)
//     return Retrofit.Builder()
//         .baseUrl(BASE_URL)
//         .client(okHttpClient)
//         .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create(gson))
//         .build()
//         .create(AuthService::class.java)
// }
//    //lets handle a few errors for retrofit responses
//
//
//}
//
