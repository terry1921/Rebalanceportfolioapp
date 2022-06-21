package com.mx.terryrockstar.rebalancedportfolioapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.math.roundToInt

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
    constructor(id: Long, name: String, targetAllocation: Float): this(id, 0, name, 0.0f, targetAllocation, "")
    constructor(groupId: Long, name: String, amount: Float, targetAllocation: Float, note: String): this(0, groupId, name, amount, targetAllocation, note)

    override fun toString(): String {
        return Gson().toJson(this)
    }

    fun getTarget() : String {
        return "${getTargetNumber()}%"
    }

    private fun getTargetNumber() : String {
        val target = if (targetAllocation % 1 == 0.0f) {
            targetAllocation.roundToInt().toString()
        } else {
            targetAllocation.toString()
        }
        return target
    }

    fun getMountCurrency(): String {
        val formatter: NumberFormat = DecimalFormat("#,###.##")
        return "$${formatter.format(amount)}"
    }

    fun getTitleHeader() : String {
        return "$name (${getTarget()})"
    }

}