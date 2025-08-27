package com.mark.data


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

const val BASE_URL= "http://postgres-production-45b8.up.railway.app"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{
    private val gson:Gson = GsonBuilder()
        .setLenient()
        .create()
    @Singleton
    @Provides
    fun provideAuthService(@Named("AuthRetrofit") retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
    @Singleton
    @Provides
    fun provideGSON(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }


    @Singleton
    @Provides
    @Named("LoggingInterceptor")

    fun provideLoggingIntercepor(): Interceptor
    {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    @Singleton
    @Provides
    @Named("AuthInterceptor")
    fun provideAuthInterceptor(tokenRepository:TokenRepository): Interceptor {
        return Interceptor{ chain->
            val originalRequest= chain.request()
            val token= tokenRepository.getToken()
            val requestBuilder= originalRequest.newBuilder()
            if (!token.isNullOrBlank()){
                requestBuilder.header("Authorization", "Bearer $token")
            }
            chain.proceed(requestBuilder.build())

        }
    }
    //    oKHTTP Client that includes the Auth Interceptor
    @Singleton
    @Provides
    @Named("AuthOkHttpClient")
    fun provideAuthOkHttpClient(
        @Named("LoggingInterceptor")
        loggingInterceptor: Interceptor,
        @Named("AuthInterceptor")
        authInterceptor: Interceptor

    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    //    retrofit instance for authenticated
    @Singleton
    @Provides
    @Named("AuthRetrofit")
    fun provideAuthRetrofit(
        @Named("AuthOkHttpClient")
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    @Singleton
    @Provides
    fun provideProfileService(@Named("AuthRetrofit") retrofit: Retrofit):Profile{
        return retrofit.create(Profile::class.java)
    }
    suspend fun <T>  safeApiCall(
        apiCall: suspend()-> Response<T>
    ):Result<T>{
        return try {
            val response= apiCall()
            if (response.isSuccessful) {
                val body= response.body()
                if (body != null){
                    Result.success(body)
                }else{
                    Result.failure(SajiliApiException("Api returned empty body", response.code()))
                }

            }else{
                val errorBodyString= response.errorBody()?.string()
                val errorResponse= try{
                    gson.fromJson(errorBodyString, ErrorResponse::class.java)
                } catch (e:Exception){
                    null
                }
                val errorMessage= errorResponse?.message ?:"Unknown error occurred"
                Result.failure(SajiliApiException(errorMessage, response.code(),errorResponse))
            }
        }
        catch (e:Exception){
            //catch network errors, JSON parsing errors
            Result.failure(e)
        }

    }

}