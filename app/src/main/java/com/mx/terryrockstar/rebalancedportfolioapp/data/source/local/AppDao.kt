package com.mx.terryrockstar.rebalancedportfolioapp.data.source.local

import androidx.room.*
import androidx.room.Dao
import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset
import com.mx.terryrockstar.rebalancedportfolioapp.data.Group

@Dao
interface AppDao {

    /**
     * Select all asset from asset table
     *
     * @return all asset
     */
    @Query("SELECT * FROM assets ORDER BY group_id ASC")
    suspend fun getAssets(): List<Asset>

    /**
     * Select a asset by id.
     *
     * @param assetId Int
     * @return the asset with assetId as possible null
     */
    @Query("SELECT * FROM assets WHERE id = :assetId")
    suspend fun getAssetById(assetId: Long): Asset?

    /**
     * Insert a asset in the database.
     * If the asset already exist, replace it.
     *
     * @param asset the Asset to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsset(asset: Asset)

    /**
     * Update a asset
     *
     * @param asset Asset to be updated
     * @return the number of asset updated. This should always be 1
     */
    @Update
    suspend fun updateAsset(asset: Asset): Int

    /**
     * Delete a asset by id.
     *
     * @param assetId Int
     * @return the number of asset deleted. This should always be 1.
     */
    @Query("DELETE FROM assets WHERE id = :assetId")
    suspend fun deleteAssetById(assetId: Long): Int

    /**
     * Delete all asset
     */
    @Query("DELETE FROM assets")
    suspend fun deleteAllAssets()

    /**
     * Select all groups from groups table
     *
     * @return all groups
     */
    @Query("SELECT * FROM groups")
    suspend fun getGroups(): List<Group>

    /**
     * Select a group by id.
     *
     * @param groupId Int
     * @return the group with groupId as possible null
     */
    @Query("SELECT * FROM groups WHERE id = :groupId")
    suspend fun getGroupById(groupId: Long): Group?

    /**
     * Insert a group in the database.
     * If the group already exist, replace it.
     *
     * @param group the Group to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: Group)

    /**
     * Update a group
     *
     * @param group Group to be updated
     * @return the number of groups updated. This should always be 1
     */
    @Update
    suspend fun updateGroup(group: Group): Int

    /**
     * Delete a group by id.
     *
     * @param groupId Int
     * @return the number of groups deleted. This should always be 1.
     */
    @Query("DELETE FROM groups WHERE id = :groupId")
    suspend fun deleteGroupById(groupId: Long): Int

    /**
     * Delete all groups
     */
    @Query("DELETE FROM groups")
    suspend fun deleteAllGroups()

}