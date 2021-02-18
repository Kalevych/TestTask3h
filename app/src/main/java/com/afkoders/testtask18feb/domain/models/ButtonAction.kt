package com.afkoders.testtask18feb.domain.models

import com.afkoders.testtask18feb.util.calendarInstance
import java.util.*

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */

data class ButtonAction(
    val cool_down: Long,
    val enabled: Boolean,
    val priority: Int,
    val type: ACTION_TYPE,
    val valid_days: List<Int>
)

val EmptyAction = ButtonAction(-1, false, -1, ACTION_TYPE.UNKNOWN, listOf())

fun ButtonAction.isValidDay() = this.valid_days.contains(calendarInstance.get(Calendar.DAY_OF_WEEK))

fun List<ButtonAction>.getHighestPriorityAction(): ButtonAction {
    return this.maxByOrNull { it.priority } ?: EmptyAction
}

fun List<ButtonAction>.filterValidDays(): List<ButtonAction> {
    return this.filter { it.isValidDay() }
}


enum class ACTION_TYPE {
    ACTION_ANIMATION, ACTION_TOAST, ACTION_CALL, ACTION_NOTIFICATION, UNKNOWN
}