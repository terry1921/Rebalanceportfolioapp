package com.mx.terryrockstar.rebalancedportfolioapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset
import com.mx.terryrockstar.rebalancedportfolioapp.data.AssetWithGroup
import com.mx.terryrockstar.rebalancedportfolioapp.data.Group

@Database(entities = [Group::class, Asset::class, AssetWithGroup::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun groupsDao(): GroupsDao

    abstract fun assetsDao(): AssetsDao

    abstract fun assetWithGroupDao(): AssetWithGroupDao

}