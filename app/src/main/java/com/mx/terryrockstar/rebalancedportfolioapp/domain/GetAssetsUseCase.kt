package com.mx.terryrockstar.rebalancedportfolioapp.domain

import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result
import com.mx.terryrockstar.rebalancedportfolioapp.data.source.Repository

class GetAssetsUseCase(private val repository: Repository) {

    suspend operator fun invoke(forceUpdate: Boolean): Result<List<Asset>> {
        return repository.getAssets(forceUpdate)
    }

}