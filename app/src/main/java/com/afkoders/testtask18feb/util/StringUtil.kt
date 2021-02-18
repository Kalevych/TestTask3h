package com.afkoders.testtask18feb.util

import com.afkoders.testtask18feb.domain.models.ACTION_TYPE

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */

fun String.convertTypeToEnum(): ACTION_TYPE {
    return when {
        this == "animation" -> ACTION_TYPE.ACTION_ANIMATION
        this == "call" -> ACTION_TYPE.ACTION_CALL
        this == "notification" -> ACTION_TYPE.ACTION_NOTIFICATION
        this == "toast" -> ACTION_TYPE.ACTION_TOAST
        else -> ACTION_TYPE.UNKNOWN
    }
}