package com.afkoders.testtask18feb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.afkoders.testtask18feb.repository.ActionButtonRepository

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */

class ActionButtonViewModel(application: Application) : AndroidViewModel(application) {

    private val actionButtonRepository = ActionButtonRepository()

    init {
        // refreshActionsFromNetwork()
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ActionButtonViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ActionButtonViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}