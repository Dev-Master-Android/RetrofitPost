package com.example.retrofitpost

import retrofit2.http.GET

interface ApiService {
    @GET("woof.json?ref=apilist.fun")
    suspend fun getRandomDog(): ApiData
}
