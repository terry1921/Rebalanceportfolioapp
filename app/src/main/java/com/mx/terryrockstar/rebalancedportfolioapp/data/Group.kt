package com.mx.terryrockstar.rebalancedportfolioapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class Group(
    @PrimaryKey val groupId: Long,
    var name: String = "",
    var targetAllocation: Float,
    var note: String = "",
)