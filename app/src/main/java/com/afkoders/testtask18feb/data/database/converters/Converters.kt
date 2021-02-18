package com.afkoders.testtask18feb.data.database.converters

import androidx.room.TypeConverter
import com.afkoders.testtask18feb.domain.models.ACTION_TYPE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */

class Converters {

    @TypeConverter
    fun toActionType(value: String) = enumValueOf<ACTION_TYPE>(value)

    @TypeConverter
    fun fromActionType(value: ACTION_TYPE) = value.name

    //TODO: Damn i've missed we have List in model. My bad, here will be a crutch

    @TypeConverter
    fun restoreValidDays(days: String?): MutableList<String?>? {
        return Gson().fromJson<List<String>>(
            days,
            object : TypeToken<List<String?>?>() {}.type
        ).toMutableList()
    }

    @TypeConverter
    fun saveValidDays(listOfDays: List<String?>?): String? {
        return Gson().toJson(listOfDays)
    }
}