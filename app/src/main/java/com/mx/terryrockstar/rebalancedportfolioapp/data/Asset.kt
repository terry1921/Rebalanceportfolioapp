package com.mx.terryrockstar.rebalancedportfolioapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assets")
data class Asset(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "group_id") var groupId: Int,
    var name: String = "",
    var amount: Float,
    @ColumnInfo(name = "target_allocation")var targetAllocation: Float,
    var note: String = "",
) {
    constructor(): this(0, 0,"",0.0f,0.0f, "")
}