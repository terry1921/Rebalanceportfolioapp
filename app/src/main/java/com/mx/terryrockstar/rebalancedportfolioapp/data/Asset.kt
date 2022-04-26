package com.mx.terryrockstar.rebalancedportfolioapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson

@Entity(tableName = "assets")
data class Asset(

    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "group_id")
    var groupId: Long,

    var name: String = "",

    var amount: Float,

    @ColumnInfo(name = "target_allocation")
    var targetAllocation: Float,

    var note: String = "",
) {
    constructor(): this(0, 0,"",0.0f,0.0f, "")
    constructor(groupId: Long, name: String, amount: Float, targetAllocation: Float, note: String): this(0, groupId, name, amount, targetAllocation, note)

    override fun toString(): String {
        return Gson().toJson(this)
    }
}