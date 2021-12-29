package com.example.appnotsure.retroFit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface NotBoredApiService {

    @GET
    suspend fun getActivityByType(@Url type: String) : Response<BoredResponse>

}