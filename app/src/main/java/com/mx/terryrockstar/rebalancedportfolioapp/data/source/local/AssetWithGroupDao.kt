package com.mx.terryrockstar.rebalancedportfolioapp.data.source.local

import androidx.room.Query
import androidx.room.Transaction
import com.mx.terryrockstar.rebalancedportfolioapp.data.AssetWithGroup

interface AssetWithGroupDao {

    @Transaction
    @Query("SELECT * FROM Assets")
    fun getAssetAndGroup(): List<AssetWithGroup>

}