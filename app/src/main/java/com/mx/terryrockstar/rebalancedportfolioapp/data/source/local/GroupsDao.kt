package com.mx.terryrockstar.rebalancedportfolioapp.data.source.local

import androidx.room.*
import com.mx.terryrockstar.rebalancedportfolioapp.data.Group

@Dao
interface GroupsDao {

    /**
    * Select all groups from groups table
    *
    * @return all groups
    */
    @Query("SELECT * FROM Groups")
    suspend fun getGroups(): List<Group>

    /**
     * Select a group by id.
     *
     * @param groupId Int
     * @return the group with groupId as possible null
     */
    @Query("SELECT * FROM Groups AS g WHERE g.groupId = :groupId")
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
    @Query("DELETE FROM Groups WHERE groupId = :groupId")
    suspend fun deleteGroupById(groupId: Long): Int

    /**
     * Delete all groups
     */
    @Query("DELETE FROM Groups")
    suspend fun deleteAllGroups()



}