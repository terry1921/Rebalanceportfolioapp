package com.mx.terryrockstar.rebalancedportfolioapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlin.math.roundToInt

@Entity(tableName = "groups")
data class Group(
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    var name: String = "",

    @ColumnInfo(name = "target_allocation")
    var targetAllocation: Float,

    var note: String = "",
) {
    constructor(): this(0L,"",0.0f, "")
    constructor(name: String, targetAllocation: Float, note: String): this(0L, name, targetAllocation, note)

    override fun toString(): String {
        return Gson().toJson(this)
    }

    fun getTarget() : String {
        return "${getTargetNumber()}%"
    }

    fun getTargetNumber() : String {
        val target = if (targetAllocation % 1 == 0.0f) {
            targetAllocation.roundToInt().toString()
        } else {
            targetAllocation.toString()
        }
        return target
    }

    fun getTargetInt() : Int {
        return targetAllocation.roundToInt()
    }

}