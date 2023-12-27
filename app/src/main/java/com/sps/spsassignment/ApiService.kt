package com.sps.spsassignment

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("trending/all/week?language=en-US")
    suspend fun getTrendingMovies(@Header("Authorization") token: String): Response<MovieResponse>

    @GET("search/tv?include_adult=false&language=en-US&page=1")
    suspend fun searchMovies(
        @Header("Authorization") token: String,
        @Query("query") query: String
    ): Response<MovieResponse>

    @GET("movie/{movieId}/similar?&language=en-US&page=1")
    suspend fun getRelatedMovies(
        @Header("Authorization") apiKey: String,
        @Path("movieId") movieId: Int
    ): Response<MovieResponse>
}
