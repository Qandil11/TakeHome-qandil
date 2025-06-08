package com.createfuture.takehome.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.createfuture.takehome.data.CharacterRepository
import com.createfuture.takehome.models.ApiCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * [CharacterViewModel] is responsible for managing and exposing UI-related data
 * to the [ComposeHomeFragment] while handling lifecycle-aware background tasks.
 *
 * This ViewModel:
 * - Loads character data from [CharacterRepository]
 * - Manages loading and error states using [StateFlow]
 * - Exposes immutable data streams to the UI for observation
 *
 * @param repository the data source used to fetch character information
 */
class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {

    // Backing property for character list
    private val _characters = MutableStateFlow<List<ApiCharacter>>(emptyList())

    /**
     * Exposed stream of character list, collected by UI
     */
    val characters: StateFlow<List<ApiCharacter>> = _characters

    // Backing property for loading status
    private val _loading = MutableStateFlow(true)

    /**
     * Indicates if data is being fetched from the API
     */
    val loading: StateFlow<Boolean> = _loading

    // Backing property for error status
    private val _error = MutableStateFlow(false)

    /**
     * Indicates whether an error occurred during data loading
     */
    val error: StateFlow<Boolean> = _error

    /**
     * Initiates character data fetching from the repository.
     *
     * @param token the API bearer token for authorization
     */
    fun loadCharacters(token: String) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.fetchCharacters(token)
            if (result != null) {
                _characters.value = result
                _error.value = false
            } else {
                _characters.value = emptyList()
                _error.value = true
            }
            _loading.value = false
        }
    }
}
