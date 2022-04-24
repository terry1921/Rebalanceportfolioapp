package com.mx.terryrockstar.rebalancedportfolioapp.data.source

import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset
import com.mx.terryrockstar.rebalancedportfolioapp.data.Group
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result

interface Repository {

    /**
     *
     * Assets
     *
     */

    suspend fun getAssets(forceUpdate: Boolean = false): Result<List<Asset>>

    suspend fun getAsset(assetId: Long, forceUpdate: Boolean = false): Result<Asset>

    suspend fun saveAsset(asset: Asset)

    suspend fun updateAsset(asset: Asset)

    suspend fun deleteAsset(assetId: Long)

    suspend fun deleteAllAssets()

    /**
     *
     * Groups
     *
     */

    suspend fun getGroups(forceUpdate: Boolean = false): Result<List<Group>>

    suspend fun getGroup(groupId: Long, forceUpdate: Boolean = false): Result<Group>

    suspend fun saveGroup(group: Group)

    suspend fun updateGroup(group: Group)

    suspend fun deleteGroup(groupId: Long)

    suspend fun deleteAllGroups()

}