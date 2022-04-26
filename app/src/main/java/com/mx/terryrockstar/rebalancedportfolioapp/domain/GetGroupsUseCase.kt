package com.mx.terryrockstar.rebalancedportfolioapp.domain

import com.mx.terryrockstar.rebalancedportfolioapp.data.Group
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result
import com.mx.terryrockstar.rebalancedportfolioapp.data.source.Repository

class GetGroupsUseCase(private val repository: Repository) {

    suspend operator fun invoke(forceUpdate: Boolean): Result<List<Group>> {
        return repository.getGroups(forceUpdate)
    }
}