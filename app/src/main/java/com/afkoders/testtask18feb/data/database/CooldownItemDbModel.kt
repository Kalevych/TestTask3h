package com.afkoders.testtask18feb.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.afkoders.testtask18feb.domain.models.ACTION_TYPE
import com.afkoders.testtask18feb.domain.models.CooldownItem

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */

@Entity(tableName = "cooldownItemsDb")
data class CooldownItemDbModel constructor(
    val nextAvailableTime: Long,
    @PrimaryKey
    val actionType: ACTION_TYPE
)


fun CooldownItemDbModel.asDomainModel(): CooldownItem = CooldownItem(this.nextAvailableTime, this.actionType)

