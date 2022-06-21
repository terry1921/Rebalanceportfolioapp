package com.mx.terryrockstar.rebalancedportfolioapp.data.source

import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset
import com.mx.terryrockstar.rebalancedportfolioapp.data.Group
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result.Error
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result.Success
import com.mx.terryrockstar.rebalancedportfolioapp.utils.ERROR_FETCHING_LOCAL
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class DefaultRepository(
    private val localDataSource: DataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): Repository {

    private var cachedAssets: ConcurrentMap<Long, Asset>? = null
    private var cachedGroups: ConcurrentMap<Long, Group>? = null

    override suspend fun getAssets(forceUpdate: Boolean): Result<List<Asset>> {
        return withContext(ioDispatcher) {
            // Respond immediately with cache if available and not dirty
            if (!forceUpdate) {
                cachedAssets?.let { cachedAssets ->
                    return@withContext Success(cachedAssets.values.sortedBy { it.groupId })
                }
            }

            val newAssets = fetchAssetsFromLocal()

            // Refresh the cache with the new assets
            (newAssets as? Success)?.let { refreshAssetCache(it.data) }

            cachedAssets?.values?.let { assets ->
                return@withContext Success(assets.sortedBy { it.groupId })
            }

            (newAssets as? Success)?.let {
                if (it.data.isEmpty()) {
                    return@withContext Success(it.data)
                }
            }

            return@withContext Error(Exception("Illegal state"))
        }
    }

    override suspend fun getAsset(assetId: Long, forceUpdate: Boolean): Result<Asset> {
        return withContext(ioDispatcher) {
            // Respond immediately with cache if available
            if (!forceUpdate) {
                getAssetWithId(assetId)?.let {
                    return@withContext Success(it)
                }
            }

            val newAsset = fetchAssetsFromLocal(assetId)

            // Refresh the cache with the new tasks
            (newAsset as? Success)?.let { cacheAsset(it.data) }

            return@withContext newAsset
        }
    }

    override suspend fun saveAsset(asset: Asset) {
        assetCacheAndPerform(asset) {
            coroutineScope {
                launch { localDataSource.saveAsset(it) }
            }
        }
    }

    override suspend fun updateAsset(asset: Asset) {
        assetCacheAndPerform(asset) {
            coroutineScope {
                launch { localDataSource.updateAsset(it) }
            }
        }
    }

    override suspend fun deleteAsset(assetId: Long) {
        coroutineScope {
            launch { localDataSource.deleteAsset(assetId) }
        }

        cachedAssets?.remove(assetId)
    }

    override suspend fun deleteAllAssets() {
        withContext(ioDispatcher) {
            coroutineScope {
                launch { localDataSource.deleteAllAssets() }
            }
        }
        cachedAssets?.clear()
    }

    /**
     *
     * Groups
     *
     */

    override suspend fun getGroups(forceUpdate: Boolean): Result<List<Group>> {
        return withContext(ioDispatcher) {
            // Respond immediately with cache if available and not dirty
            if (!forceUpdate) {
                cachedGroups?.let { cachedGroups ->
                    return@withContext Success(cachedGroups.values.sortedBy { it.name })
                }
            }

            val newGroups = fetchGroupsFromLocal()

            // Refresh the cache with the new assets
            (newGroups as? Success)?.let { refreshGroupCache(it.data) }

            cachedGroups?.values?.let { groups ->
                return@withContext Success(groups.sortedBy { it.name })
            }

            (newGroups as? Success)?.let {
                if (it.data.isEmpty()) {
                    return@withContext Success(it.data)
                }
            }

            return@withContext Error(Exception("Illegal state"))
        }
    }

    override suspend fun getGroup(groupId: Long, forceUpdate: Boolean): Result<Group> {
        return withContext(ioDispatcher) {
            // Respond immediately with cache if available
            if (!forceUpdate) {
                getGroupWithId(groupId)?.let {
                    return@withContext Success(it)
                }
            }

            val newGroup = fetchGroupsFromLocal(groupId)

            // Refresh the cache with the new tasks
            (newGroup as? Success)?.let { cacheGroup(it.data) }

            return@withContext newGroup
        }
    }

    override suspend fun saveGroup(group: Group) {
        groupCacheAndPerform(group) {
            coroutineScope {
                launch { localDataSource.saveGroup(it) }
            }
        }
    }

    override suspend fun updateGroup(group: Group) {
        groupCacheAndPerform(group) {
            coroutineScope {
                launch { localDataSource.updateGroup(it) }
            }
        }
    }

    override suspend fun deleteGroup(groupId: Long) {
        coroutineScope {
            launch { localDataSource.deleteGroup(groupId) }
        }

        cachedGroups?.remove(groupId)
    }

    override suspend fun deleteAllGroups() {
        withContext(ioDispatcher) {
            coroutineScope {
                launch { localDataSource.deleteAllGroups() }
            }
        }
        cachedGroups?.clear()
    }

    /**
     *
     * Private functions assets
     *
     * */

    private suspend fun fetchAssetsFromLocal(): Result<List<Asset>> {
        // Local
        val localAssets = localDataSource.getAssets()
        if (localAssets is Success) return localAssets
        return Error(Exception(ERROR_FETCHING_LOCAL))
    }

    private suspend fun fetchAssetsFromLocal(assetId: Long) : Result<Asset> {
        val localAsset = localDataSource.getAsset(assetId)
        if (localAsset is Success) return localAsset
        return Error(Exception(ERROR_FETCHING_LOCAL))
    }

    private fun refreshAssetCache(data: List<Asset>) {
        cachedAssets?.clear()
        data.sortedBy { it.groupId }.forEach {
            assetCacheAndPerform(it) {}
        }
    }

    private fun getAssetWithId(assetId: Long) = cachedAssets?.get(assetId)

    private fun cacheAsset(asset: Asset): Asset {
        val cachedAsset = Asset(asset.id, asset.groupId, asset.name, asset.amount, asset.targetAllocation, asset.note)
        // Create if it doesn't exist.
        if (cachedAssets == null) {
            cachedAssets = ConcurrentHashMap()
        }
        cachedAssets?.put(cachedAsset.id, cachedAsset)
        return cachedAsset
    }

    private inline fun assetCacheAndPerform(asset: Asset, perform: (Asset) -> Unit) {
        val cachedAsset = cacheAsset(asset)
        perform(cachedAsset)
    }

    /**
     *
     * Private functions groups
     *
     * */

    private suspend fun fetchGroupsFromLocal(): Result<List<Group>> {
        // Local
        val localGroups = localDataSource.getGroups()
        if (localGroups is Success) return localGroups
        return Error(Exception(ERROR_FETCHING_LOCAL))
    }

    private suspend fun fetchGroupsFromLocal(groupId: Long) : Result<Group> {
        val localGroup = localDataSource.getGroup(groupId)
        if (localGroup is Success) return localGroup
        return Error(Exception(ERROR_FETCHING_LOCAL))
    }

    private fun refreshGroupCache(data: List<Group>) {
        cachedGroups?.clear()
        data.sortedBy { it.id }.forEach {
            groupCacheAndPerform(it) {}
        }
    }

    private fun getGroupWithId(groupId: Long) = cachedGroups?.get(groupId)

    private fun cacheGroup(group: Group): Group {
        val cachedGroup = Group(group.id, group.name, group.targetAllocation, group.note)
        // Create if it doesn't exist.
        if (cachedGroups == null) {
            cachedGroups = ConcurrentHashMap()
        }
        cachedGroups?.put(cachedGroup.id, cachedGroup)
        return cachedGroup
    }

    private inline fun groupCacheAndPerform(group: Group, perform: (Group) -> Unit) {
        val cachedGroup = cacheGroup(group)
        perform(cachedGroup)
    }

}