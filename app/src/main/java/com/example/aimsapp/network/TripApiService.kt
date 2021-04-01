package com.example.aimsapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.appery.io/rest/1/apiexpress/api/DispatcherMobileApp/"
private const val DRIVER_ID = "D1"
private const val API_KEY = "f20f8b25-b149-481c-9d2c-41aeb76246ef"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface TripApiService {
    @GET("GetTripListDetailByDriver/$DRIVER_ID?apiKey=$API_KEY")
    fun getProperties():
            Deferred<Response>
}

object TripApi{
    val retrofitService: TripApiService by lazy {
        retrofit.create(TripApiService::class.java)
    }
}