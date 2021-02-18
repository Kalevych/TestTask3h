package com.afkoders.testtask18feb.repository

import androidx.lifecycle.MutableLiveData
import com.afkoders.testtask18feb.domain.models.ButtonAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */


class ActionButtonRepository() {

    val availableActions: MutableLiveData<List<ButtonAction>> = MutableLiveData(listOf())

    suspend fun refreshActions() {
        withContext(Dispatchers.IO) {
        }
    }

}