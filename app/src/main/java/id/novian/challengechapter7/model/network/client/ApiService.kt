package id.novian.challengechapter7.model.network.client

import id.novian.challengechapter7.model.network.model.details.DetailsResponse
import id.novian.challengechapter7.model.network.model.popular.MoviePopularResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getMoviePopular(@Query("api_key") apiKey: String): MoviePopularResponse

    @GET("movie/{id}")
    fun getDetails(@Query("api_key") apiKey: String, @Path("id") id: Int): DetailsResponse
}