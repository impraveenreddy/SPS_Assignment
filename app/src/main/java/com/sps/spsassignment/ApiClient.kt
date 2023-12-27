import com.sps.spsassignment.TmdbApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiOGUxYjhlMTEyZWYxOTFlNTJkZjUzMmU2MDEyNTc3YSIsInN1YiI6IjY1ODE3ZDk4ZGY4NmE4MDkzN2U3ZGRlYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Yp6UnW-cAnTz0QnXsKoJbIFA4JM_CO6SfoYKpXLP1t0")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    val apiService: TmdbApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApiService::class.java)
    }
}
