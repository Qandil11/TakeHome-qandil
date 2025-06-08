package com.createfuture.takehome

import com.createfuture.takehome.data.CharacterRepository
import com.createfuture.takehome.data.CharacterService
import com.createfuture.takehome.models.ApiCharacter
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

/**
 * Unit test for [CharacterRepository].
 *
 * This test ensures that the repository correctly fetches data from the [CharacterService]
 * when the API call is successful.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CharacterRepositoryTest {

    /**
     * Verifies that [CharacterRepository.fetchCharacters] returns data when the API call
     * is successful and returns a valid response body.
     */
    @Test
    fun fetchCharacters_returnsData_whenApiSuccessful(): Unit = runTest {
        // Arrange: Mock the CharacterService to return a successful response
        val service = mockk<CharacterService>()
        val mockResponse = listOf(
            ApiCharacter(
                name = "Jon Snow",
                gender = "Male",
                culture = "Northmen",
                born = "In 283 AC",
                died = "",
                aliases = listOf("Lord Snow", "Ned Stark's Bastard"),
                tvSeries = listOf("Season 1", "Season 2"),
                playedBy = listOf("Kit Harington")
            )
        )

        coEvery { service.getCharacters(any()) } returns Response.success(mockResponse)

        val repository = CharacterRepository(service)

        // Act: Call the repository
        val result = repository.fetchCharacters("Bearer dummyToken")

        // Assert: Ensure the result matches the mocked data
        assertEquals(mockResponse, result)
    }
}
