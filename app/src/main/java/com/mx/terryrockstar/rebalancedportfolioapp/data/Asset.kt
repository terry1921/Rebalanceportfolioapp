package com.mx.terryrockstar.rebalancedportfolioapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assets")
data class Asset(
    @PrimaryKey val assetId: Long,
    var groupId: Int,
    var name: String = "",
    var amount: Float,
    var targetAllocation: Float,
    var note: String = "",
)