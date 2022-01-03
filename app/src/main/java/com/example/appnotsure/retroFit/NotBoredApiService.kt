package com.example.appnotsure.retroFit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NotBoredApiService {

    @GET("activity/")
    suspend fun getActivityByType(@Query("type") type: String,@Query("participants") participants: Int) : Response<BoredResponse>

    @GET("activity/")
    suspend fun getRandomActivity(@Query("participants") participants: Int) : Response<BoredResponse>

}