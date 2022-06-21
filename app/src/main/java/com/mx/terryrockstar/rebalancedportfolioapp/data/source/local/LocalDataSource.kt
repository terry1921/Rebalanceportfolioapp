package com.mx.terryrockstar.rebalancedportfolioapp.data.source.local

import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset
import com.mx.terryrockstar.rebalancedportfolioapp.data.Group
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result.Error
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result.Success
import com.mx.terryrockstar.rebalancedportfolioapp.data.source.DataSource
import com.mx.terryrockstar.rebalancedportfolioapp.utils.Print
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource internal constructor(
    private val assetsDao: AppDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DataSource {

    override suspend fun getAssets(): Result<List<Asset>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(assetsDao.getAssets())
        } catch (e: Exception) {
            Print.e(javaClass.name, e.message, e.cause)
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
            Print.e(javaClass.name, e.message, e.cause)
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

    override suspend fun getGroups(): Result<List<Group>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(assetsDao.getGroups())
        } catch (e: java.lang.Exception) {
            Print.e(javaClass.name, e.message, e.cause)
            Error(e)
        }
    }

    override suspend fun getGroup(groupId: Long): Result<Group> = withContext(ioDispatcher) {
        try {
            val group = assetsDao.getGroupById(groupId)
            if (group != null) {
                return@withContext Success(group)
            } else {
                return@withContext Error(java.lang.Exception("Group not found"))
            }
        } catch (e: java.lang.Exception) {
            Print.e(javaClass.name, e.message, e.cause)
            return@withContext Error(e)
        }
    }

    override suspend fun saveGroup(group: Group) = withContext(ioDispatcher) {
        assetsDao.insertGroup(group)
    }

    override suspend fun updateGroup(group: Group) = withContext(ioDispatcher) {
        val updated = assetsDao.updateGroup(group)
        return@withContext (updated == 1)
    }

    override suspend fun deleteGroup(groupId: Long): Boolean = withContext(ioDispatcher) {
        val deleted = assetsDao.deleteGroupById(groupId)
        return@withContext (deleted == 1)
    }

    override suspend fun deleteAllGroups() = withContext(ioDispatcher) {
        assetsDao.deleteAllGroups()
    }

}