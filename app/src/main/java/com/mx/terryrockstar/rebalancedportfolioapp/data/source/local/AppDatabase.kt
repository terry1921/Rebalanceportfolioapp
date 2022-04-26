package com.mx.terryrockstar.rebalancedportfolioapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset
import com.mx.terryrockstar.rebalancedportfolioapp.data.Group


@Database(entities = [Group::class, Asset::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

}