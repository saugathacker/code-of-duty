package com.example.aimsapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://api.appery.io/rest/1/apiexpress/api/DispatcherMobileApp/"
private const val DRIVER_ID = "CodeOfDuty"
private const val API_KEY = "f20f8b25-b149-481c-9d2c-41aeb76246ef"

/**
 * Converts Json adapter to data
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Method that pulls from the API
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

/**
 * interface that adds api calls Driver_ID and API_KEy
 */
interface TripApiService {
    @GET("GetDetailedTripListByDriver/{driverId}?apiKey=$API_KEY")
    fun getProperties(
        @Path("driverId") driverId: String
    ):
            Deferred<Response>
}

/**
 * instantiating retrofitService
 * lazy initialization is the tactic of delaying the creation of an object
 */
object TripApi {
    val retrofitService: TripApiService by lazy {
        retrofit.create(TripApiService::class.java)
    }
}