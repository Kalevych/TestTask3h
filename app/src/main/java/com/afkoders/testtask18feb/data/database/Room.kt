package com.afkoders.testtask18feb.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.afkoders.testtask18feb.data.database.converters.Converters

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */

@Dao
interface ButtonActionsDao {
    @Query("select * from buttonActionsDb")
    fun getButtonActions(): LiveData<List<ActionButtonDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(buttonActions: List<ActionButtonDbModel>)
}

@Dao
interface CooldownItemsDao {
    @Query("select * from cooldownItemsDb")
    fun getCooldownItems(): List<CooldownItemDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cooldownItems: List<CooldownItemDbModel>)
}


@Database(entities = [ActionButtonDbModel::class, CooldownItemDbModel::class], version = 2)
@TypeConverters(Converters::class)
abstract class ButtonActionsDatabase : RoomDatabase() {
    abstract val buttonActionsDao: ButtonActionsDao
    abstract val cooldownItemsDao: CooldownItemsDao
}

private lateinit var INSTANCE: ButtonActionsDatabase

fun getDatabase(context: Context): ButtonActionsDatabase {
    synchronized(ButtonActionsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ButtonActionsDatabase::class.java,
                "videos"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}
