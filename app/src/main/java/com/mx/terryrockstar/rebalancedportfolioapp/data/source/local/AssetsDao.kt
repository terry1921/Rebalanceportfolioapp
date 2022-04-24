package com.mx.terryrockstar.rebalancedportfolioapp.data.source.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset

interface AssetsDao {

    /**
     * Select all asset from asset table
     *
     * @return all asset
     */
    @Query("SELECT * FROM Assets")
    suspend fun getAssets(): List<Asset>

    /**
     * Select a asset by id.
     *
     * @param assetId Int
     * @return the asset with assetId as possible null
     */
    @Query("SELECT * FROM Assets AS a WHERE a.assetId = :assetId")
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
    @Query("DELETE FROM Assets WHERE assetId = :assetId")
    suspend fun deleteAssetById(assetId: Long): Int

    /**
     * Delete all asset
     */
    @Query("DELETE FROM Assets")
    suspend fun deleteAllAssets()

}