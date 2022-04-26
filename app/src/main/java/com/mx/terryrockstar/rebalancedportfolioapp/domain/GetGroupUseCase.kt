package com.mx.terryrockstar.rebalancedportfolioapp.domain

import com.mx.terryrockstar.rebalancedportfolioapp.data.Group
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result
import com.mx.terryrockstar.rebalancedportfolioapp.data.source.Repository

class GetGroupUseCase(private val repository: Repository) {

    suspend operator fun invoke(groupId: Long): Result<Group> {
        return repository.getGroup(groupId)
    }

}