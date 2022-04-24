package com.mx.terryrockstar.rebalancedportfolioapp.data.source.local

import com.mx.terryrockstar.rebalancedportfolioapp.data.Group
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result
import com.mx.terryrockstar.rebalancedportfolioapp.data.source.GroupDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class GroupsLocalDataSource internal constructor(
    private val groupsDao: GroupsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ): GroupDataSource {

    override suspend fun getGroups(): Result<List<Group>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(groupsDao.getGroups())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getGroup(groupId: Long): Result<Group> = withContext(ioDispatcher) {
        try {
            val group = groupsDao.getGroupById(groupId)
            if (group != null) {
                return@withContext Result.Success(group)
            } else {
                return@withContext Result.Error(Exception("Group not found"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override suspend fun saveGroup(group: Group) = withContext(ioDispatcher) {
        groupsDao.insertGroup(group)
    }

    override suspend fun updateGroup(group: Group) = withContext(ioDispatcher) {
        val updated = groupsDao.updateGroup(group)
        return@withContext (updated == 1)
    }

    override suspend fun deleteGroup(groupId: Long): Boolean = withContext(ioDispatcher) {
        val deleted = groupsDao.deleteGroupById(groupId)
        return@withContext (deleted == 1)
    }

    override suspend fun deleteAllGroups() = withContext(ioDispatcher) {
        groupsDao.deleteAllGroups()
    }
}