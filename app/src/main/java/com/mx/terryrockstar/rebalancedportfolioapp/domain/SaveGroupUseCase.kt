package com.mx.terryrockstar.rebalancedportfolioapp.domain

import com.mx.terryrockstar.rebalancedportfolioapp.data.Group
import com.mx.terryrockstar.rebalancedportfolioapp.data.source.Repository

class SaveGroupUseCase(private val repository: Repository) {

    suspend operator fun invoke(group: Group) {
        return repository.saveGroup(group)
    }

}