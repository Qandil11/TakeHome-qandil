package com.createfuture.takehome.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.createfuture.takehome.data.CharacterRepository

/**
 * Factory class for creating an instance of [CharacterViewModel] with constructor parameters.
 *
 * ViewModels are typically created using parameterless constructors, but when a ViewModel
 * requires dependencies (like a [CharacterRepository]), a custom [ViewModelProvider.Factory]
 * is used to provide those dependencies.
 *
 * @property repository The [CharacterRepository] instance used to fetch character data.
 */
class CharacterViewModelFactory(
    private val repository: CharacterRepository
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the given [modelClass].
     *
     * @param modelClass The class of the ViewModel to create.
     * @return A [CharacterViewModel] instance with the provided [repository].
     * @throws IllegalArgumentException if the modelClass is not assignable from [CharacterViewModel].
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
