package com.mx.terryrockstar.rebalancedportfolioapp.data.source

import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset
import com.mx.terryrockstar.rebalancedportfolioapp.data.Group
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result

interface DataSource {

    suspend fun getAssets(): Result<List<Asset>>

    suspend fun getAsset(assetId: Long): Result<Asset>

    suspend fun saveAsset(asset: Asset)

    suspend fun updateAsset(asset: Asset): Boolean

    suspend fun deleteAsset(assetId: Long): Boolean

    suspend fun deleteAllAssets()

    suspend fun getGroups(): Result<List<Group>>

    suspend fun getGroup(groupId: Long): Result<Group>

    suspend fun saveGroup(group: Group)

    suspend fun updateGroup(group: Group): Boolean

    suspend fun deleteGroup(groupId: Long): Boolean

    suspend fun deleteAllGroups()

}