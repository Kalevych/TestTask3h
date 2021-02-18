package com.afkoders.testtask18feb.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.afkoders.testtask18feb.domain.models.ACTION_TYPE
import com.afkoders.testtask18feb.domain.models.ButtonAction

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */

@Entity(tableName = "buttonActionsDb")
data class ActionButtonDbModel constructor(
    val cool_down: Long,
    val enabled: Boolean,
    val priority: Int,
    @PrimaryKey
    val type: ACTION_TYPE,
    // my bad, terrible code. Crutch for storing list in Room database
    val valid_days: List<String>
)


/**
 * Map DatabaseVideos to domain entities
 */
fun List<ActionButtonDbModel>.asDomainModel(): List<ButtonAction> {
    return map {
        ButtonAction(
            it.cool_down, it.enabled, it.priority, it.type, it.valid_days.map { it.toIntOrNull()?:0 }
        )
    }
}
