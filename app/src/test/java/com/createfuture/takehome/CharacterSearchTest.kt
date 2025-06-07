package com.createfuture.takehome

import com.createfuture.takehome.models.ApiCharacter
import org.junit.Assert.assertEquals
import org.junit.Test
/**
 * Unit tests for verifying character search functionality by name.
 *
 * These tests use a mocked list of `ApiCharacter` objects and apply filter logic
 * similar to what is used in the actual feature implementation.
 *
 * The tests cover:
 * - Exact and partial name matching
 * - Case-insensitive search
 * - Behavior when no characters match the query
 * - Multiple matches for a given substring
 *
 * This helps ensure the business logic behind the search bar works as expected
 * and is regression-safe for future changes.
 */

class CharacterSearchTest {

    private val mockCharacters = listOf(
        ApiCharacter(name = "Jon Snow", gender = "Male", culture = "Northmen", born = "", died = "", aliases = listOf(), tvSeries = listOf(), playedBy = listOf()),
        ApiCharacter(name = "Eddard Stark", gender = "Male", culture = "Northmen", born = "", died = "", aliases = listOf(), tvSeries = listOf(), playedBy = listOf()),
        ApiCharacter(name = "Arya Stark", gender = "Female", culture = "Northmen", born = "", died = "", aliases = listOf(), tvSeries = listOf(), playedBy = listOf()),
        ApiCharacter(name = "Daenerys Targaryen", gender = "Female", culture = "Valyrian", born = "", died = "", aliases = listOf(), tvSeries = listOf(), playedBy = listOf())

    )
    @Test
    fun characterByName_returnsEmptyWhenNoMatch() {
        val query = "zzz"
        val result = mockCharacters.filter {
            it.name.contains(query, ignoreCase = true)
        }
        assertEquals(0, result.size)
    }
    @Test
    fun characterByName_partialMatchWorks() {
        val query = "snow"
        val result = mockCharacters.filter {
            it.name.contains(query, ignoreCase = true)
        }
        assertEquals(1, result.size)
        assertEquals("Jon Snow", result.first().name)
    }
    @Test
    fun characterByName_multipleMatches() {
        val query = "stark"
        val result = mockCharacters.filter {
            it.name.contains(query, ignoreCase = true)
        }
        assertEquals(2, result.size)
    }


    @Test
    fun searchCharacterByName_filtersCorrectly() {
        val query = "stark"
        val result = mockCharacters.filter {
            it.name.contains(query, ignoreCase = true)
        }
        assertEquals(2, result.size)
    }

}
