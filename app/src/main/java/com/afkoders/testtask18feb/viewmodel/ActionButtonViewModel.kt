package com.afkoders.testtask18feb.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.afkoders.testtask18feb.data.database.getDatabase
import com.afkoders.testtask18feb.domain.models.ACTION_TYPE
import com.afkoders.testtask18feb.domain.models.CooldownItem
import com.afkoders.testtask18feb.domain.models.filterValidDays
import com.afkoders.testtask18feb.domain.models.getHighestPriorityAction
import com.afkoders.testtask18feb.repository.ActionButtonRepository
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */

class ActionButtonViewModel(application: Application) : AndroidViewModel(application) {

    private val actionButtonRepository = ActionButtonRepository(getDatabase(application))

    val availableActions = actionButtonRepository.availableActions


    val actionToExecute = Transformations.map(availableActions) {
        _loading.value = false
        it.filterValidDays()
            .getHighestPriorityAction()
    }

    init {
        // refreshActionsFromNetwork()
    }

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean>
        get() = _loading


    fun runActionClicked() {
        refreshActionFromNetwork()
    }


    fun addCooldownItem(coolDown: Long, actionAnimation: ACTION_TYPE) {
        viewModelScope.launch {
            actionButtonRepository.cooldownItemCreated(coolDown, actionAnimation)
        }
    }

    private fun refreshActionFromNetwork() {
        viewModelScope.launch {
            try {
                _loading.value = true
                actionButtonRepository.refreshActions()
                _eventNetworkError.value = false

            } catch (networkError: IOException) {
                if (availableActions.value.isNullOrEmpty())
                    _eventNetworkError.value = true
                _loading.value = false
            }
        }
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