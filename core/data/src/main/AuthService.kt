import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

//interface for authentication-related API calls to the Spring Boot backend
interface  AuthService {
    @POST("api/auth/register")
    suspend fun register(@Body request:RegisterRequest):Response<MessageResponse>


    @POST("api/auth/login")
    suspend fun login(@Body request:LoginRequest):Response<AuthResponse>

    @GET("api/auth/protected-data")
    suspend fun getProtectedData(@Header("Authorization") token:String):Response<MessageResponse>

}