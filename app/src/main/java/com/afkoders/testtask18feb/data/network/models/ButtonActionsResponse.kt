package com.afkoders.testtask18feb.data.network.models

import com.afkoders.testtask18feb.data.database.ActionButtonDbModel
import com.afkoders.testtask18feb.domain.models.ButtonAction
import com.afkoders.testtask18feb.util.convertTypeToEnum
import com.google.gson.annotations.SerializedName

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */

class ButtonActionsResponse : ArrayList<ButtonActionsResponseItem>()

data class ButtonActionsResponseItem(
    @SerializedName("cool_down")
    val cool_down: Long,
    @SerializedName("enabled")
    val enabled: Boolean,
    @SerializedName("priority")
    val priority: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("valid_days")
    val valid_days: List<Int>
)

fun ButtonActionsResponse.parseEnabledActions(): List<ButtonActionsResponseItem> {
    return this.filter { it.enabled }
}

fun List<ButtonActionsResponseItem>.asDomainModels(): List<ButtonAction> {
    return this.map {
        ButtonAction(
            it.cool_down,
            it.enabled,
            it.priority,
            it.type.convertTypeToEnum(),
            it.valid_days
        )
    }
}


/**
 * Convert Network results to database objects
 */
fun List<ButtonActionsResponseItem>.asDatabaseModel(): List<ActionButtonDbModel> {
    return this.map {
        ActionButtonDbModel(
            it.cool_down,
            it.enabled,
            it.priority,
            it.type.convertTypeToEnum(),
            it.valid_days.map { it.toString() })
    }
}