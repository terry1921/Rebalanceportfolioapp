package com.mx.terryrockstar.rebalancedportfolioapp.domain

import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result
import com.mx.terryrockstar.rebalancedportfolioapp.data.source.Repository

class GetAssetUseCase(private val repository: Repository) {

    suspend operator fun invoke(assetId: Long): Result<Asset> {
        return repository.getAsset(assetId)
    }

}