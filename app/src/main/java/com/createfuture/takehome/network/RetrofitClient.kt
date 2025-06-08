package com.createfuture.takehome.network

import com.createfuture.takehome.data.CharacterService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object that provides a configured Retrofit instance
 * for making network requests to the Game of Thrones API.
 *
 * This ensures only one instance of [CharacterService] is created
 * and reused throughout the app.
 */
object RetrofitClient {

    /**
     * Lazily initialized [CharacterService] interface used to define
     * network endpoints and perform API calls.
     *
     * The Retrofit instance is configured with:
     * - Base URL of the API
     * - Gson converter for JSON deserialization
     */
    val characterService: CharacterService by lazy {
        Retrofit.Builder()
            .baseUrl("https://yj8ke8qonl.execute-api.eu-west-1.amazonaws.com") // Base API URL
            .addConverterFactory(GsonConverterFactory.create()) // Convert JSON to Kotlin objects
            .build()
            .create(CharacterService::class.java) // Create service interface instance
    }
}
