package com.mx.terryrockstar.rebalancedportfolioapp.data.source

import com.mx.terryrockstar.rebalancedportfolioapp.data.Group
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result

interface GroupDataSource {

    suspend fun getGroups(): Result<List<Group>>

    suspend fun getGroup(groupId: Long): Result<Group>

    suspend fun saveGroup(group: Group)

    suspend fun updateGroup(group: Group): Boolean

    suspend fun deleteGroup(groupId: Long): Boolean

    suspend fun deleteAllGroups()

}