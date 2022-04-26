package com.mx.terryrockstar.rebalancedportfolioapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class Group(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var name: String = "",
    @ColumnInfo(name = "target_allocation")var targetAllocation: Float,
    var note: String = "",
) {
    constructor(): this(0,"",0.0f, "")
}