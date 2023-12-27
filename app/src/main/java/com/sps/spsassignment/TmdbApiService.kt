package com.sps.spsassignment

import retrofit2.http.GET

interface TmdbApiService : ApiService {
    @GET("trending/all/week?language=en-US")
    suspend fun getTrendingMovies(): TmdbResponse
}

data class TmdbResponse(
    val results: List<Movie>
)
