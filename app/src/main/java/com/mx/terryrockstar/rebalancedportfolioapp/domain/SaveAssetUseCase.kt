package com.mx.terryrockstar.rebalancedportfolioapp.domain

import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset
import com.mx.terryrockstar.rebalancedportfolioapp.data.source.Repository

class SaveAssetUseCase(private val repository: Repository) {

    suspend operator fun invoke(asset: Asset) {
        return repository.saveAsset(asset)
    }

}