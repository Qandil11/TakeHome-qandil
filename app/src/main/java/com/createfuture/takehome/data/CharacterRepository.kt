package com.createfuture.takehome.data

import com.createfuture.takehome.models.ApiCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Retrofit service interface that defines the API endpoints
 * to retrieve Game of Thrones character data from the server.
 */
interface CharacterService {

    /**
     * Makes a GET request to retrieve a list of characters.
     *
     * @param token The authorization token in the format "Bearer <token>".
     * @return A [Response] containing a list of [ApiCharacter] objects.
     */
    @GET("/characters")
    suspend fun getCharacters(@Header("Authorization") token: String): Response<List<ApiCharacter>>
}

/**
 * Repository class that abstracts the logic of fetching characters
 * from the remote API using [CharacterService]. It is responsible
 * for making network calls on a background thread and returning
 * the results to the ViewModel or caller.
 *
 * @property service The Retrofit API service used to fetch data.
 */
class CharacterRepository(private val service: CharacterService) {

    /**
     * Fetches a list of characters from the remote API.
     *
     * @param token The authorization token in the format "Bearer <token>".
     * @return A list of [ApiCharacter] if the request is successful, or null if it fails.
     */
    suspend fun fetchCharacters(token: String): List<ApiCharacter>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getCharacters(token)
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                // Log the error in real implementation
                null
            }
        }
    }
}
