package com.afkoders.testtask18feb.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.afkoders.testtask18feb.data.database.ButtonActionsDatabase
import com.afkoders.testtask18feb.data.database.CooldownItemDbModel
import com.afkoders.testtask18feb.data.database.asDomainModel
import com.afkoders.testtask18feb.data.network.ButtonActionServiceImpl
import com.afkoders.testtask18feb.data.network.models.asDatabaseModel
import com.afkoders.testtask18feb.data.network.models.parseEnabledActions
import com.afkoders.testtask18feb.domain.models.ACTION_TYPE
import com.afkoders.testtask18feb.domain.models.ButtonAction
import com.afkoders.testtask18feb.domain.models.CooldownItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */


class ActionButtonRepository(private val database: ButtonActionsDatabase) {

    // Synchronious by intention for now, but could be an livedata in best world
    var cooldownsList: List<CooldownItem> = listOf()

    val availableActions: LiveData<List<ButtonAction>> =
        Transformations.map(database.buttonActionsDao.getButtonActions()) {
            it.filter { action -> (cooldownsList).none { (it.actionType == action.type) && (it.nextAvailableTime > System.currentTimeMillis()) } }.asDomainModel()
        }

    suspend fun refreshActions() {
        withContext(Dispatchers.IO) {

            cooldownsList = database.cooldownItemsDao.getCooldownItems().map {
                it.asDomainModel()
            }

            val actionsFromNetwork =
                ButtonActionServiceImpl.buttonActionsServiceImpl.getButtonActions()
                    .parseEnabledActions()

            database.buttonActionsDao.insertAll(actionsFromNetwork.asDatabaseModel())
        }
    }

    suspend fun cooldownItemCreated(actionType: ACTION_TYPE) {
        withContext(Dispatchers.IO) {
            val coolDownToUpdate =
                System.currentTimeMillis() + (availableActions.value?.firstOrNull { it.type == actionType }?.cool_down?:0L)

            val cooldownItemDbModel = CooldownItemDbModel(coolDownToUpdate, actionType)
            database.cooldownItemsDao.insertAll(listOf(cooldownItemDbModel))

        }
    }

}