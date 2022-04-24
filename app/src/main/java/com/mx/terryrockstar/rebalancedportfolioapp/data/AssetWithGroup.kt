package com.mx.terryrockstar.rebalancedportfolioapp.data

import androidx.room.Embedded
import androidx.room.Relation

data class AssetWithGroup(
    @Embedded val asset: Asset,
    @Relation(
        parentColumn = "assetId",
        entityColumn = "groupId"
    )
    val group: Group
)