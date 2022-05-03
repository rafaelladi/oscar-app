package com.dietrich.myapplication

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.Query
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

class RetrofitConfig {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://172.20.253.96:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val loginAPI: LoginAPI
    get() = retrofit.create()
}

interface LoginAPI {
    @POST("/login")
    fun login(@Body dto: LoginRequest): Call<LoginResponse>

    @GET("/movies")
    fun getMovies(): Call<List<Movie>>

    @GET("/directors")
    fun getDirectors(): Call<List<Director>>

    @POST("{userId}/vote")
    fun vote(@Path("userId") userId: String,
             @Query("token") token: String,
             @Query("movieId") movieId: Long?,
             @Query("directorId") directorId: Long?): Call<VoteSuccess>
}