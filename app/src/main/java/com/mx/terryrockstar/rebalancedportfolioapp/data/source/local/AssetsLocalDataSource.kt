package com.mx.terryrockstar.rebalancedportfolioapp.data.source.local

import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result.Error
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result.Success
import com.mx.terryrockstar.rebalancedportfolioapp.data.source.AssetDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AssetsLocalDataSource internal constructor(
    private val assetsDao: AssetsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AssetDataSource {

    override suspend fun getAssets(): Result<List<Asset>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(assetsDao.getAssets())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getAsset(assetId: Long): Result<Asset> = withContext(ioDispatcher) {
        try {
            val asset = assetsDao.getAssetById(assetId)
            if (asset != null) {
                return@withContext Success(asset)
            } else {
                return@withContext Error(Exception("Asset not found"))
            }
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    }

    override suspend fun saveAsset(asset: Asset) = withContext(ioDispatcher) {
        assetsDao.insertAsset(asset)
    }

    override suspend fun updateAsset(asset: Asset) = withContext(ioDispatcher) {
        val updated = assetsDao.updateAsset(asset)
        return@withContext (updated == 1)
    }

    override suspend fun deleteAsset(assetId: Long): Boolean = withContext(ioDispatcher) {
        val deleted = assetsDao.deleteAssetById(assetId)
        return@withContext (deleted == 1)
    }

    override suspend fun deleteAllAssets() = withContext(ioDispatcher) {
        assetsDao.deleteAllAssets()
    }

}