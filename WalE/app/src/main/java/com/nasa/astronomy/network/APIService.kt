package com.nasa.astronomy.network

import com.nasa.astronomy.data.AstronomyPicture
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("planetary/apod")
    suspend fun getAstronomyPicture(@Query("api_key") apiKey: String): Response<AstronomyPicture>

}