package id.novian.challengechapter7.model.network.client

import id.novian.challengechapter7.model.network.model.details.DetailsResponse
import id.novian.challengechapter7.model.network.model.popular.MoviePopularResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getMoviePopular(@Query("api_key") apiKey: String): MoviePopularResponse

    @GET("movie/{id}")
    suspend fun getDetails(@Path("id") id: Int, @Query("api_key") apiKey: String): DetailsResponse
}