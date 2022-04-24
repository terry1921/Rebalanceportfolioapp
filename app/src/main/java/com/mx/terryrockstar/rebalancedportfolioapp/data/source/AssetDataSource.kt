package com.mx.terryrockstar.rebalancedportfolioapp.data.source

import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result

interface AssetDataSource {

    suspend fun getAssets(): Result<List<Asset>>

    suspend fun getAsset(assetId: Long): Result<Asset>

    suspend fun saveAsset(asset: Asset)

    suspend fun updateAsset(asset: Asset): Boolean

    suspend fun deleteAsset(assetId: Long): Boolean

    suspend fun deleteAllAssets()

}