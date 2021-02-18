package com.afkoders.testtask18feb.domain.models

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

enum class ACTION_TYPE {
    ACTION_ANIMATION, ACTION_TOAST, ACTION_CALL, ACTION_NOTIFICATION, UNKNOWN
}

val EmptyAction = ButtonAction(-1, false, -1, ACTION_TYPE.UNKNOWN, listOf())