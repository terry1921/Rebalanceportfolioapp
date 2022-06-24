package com.mx.terryrockstar.rebalancedportfolioapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result
import com.mx.terryrockstar.rebalancedportfolioapp.domain.GetAssetsUseCase
import com.mx.terryrockstar.rebalancedportfolioapp.domain.GetGroupUseCase
import com.mx.terryrockstar.rebalancedportfolioapp.utils.Event
import com.mx.terryrockstar.rebalancedportfolioapp.utils.Print
import kotlinx.coroutines.launch

class AssetsViewModel(
    private val getAssetsUseCase: GetAssetsUseCase,
    private val getGroupUseCase: GetGroupUseCase
) : ViewModel() {

    private val _items = MutableLiveData<List<Asset>>().apply { value = emptyList() }
    val items: LiveData<List<Asset>> = _items

    private val _isAssetsObtained = MutableLiveData<Boolean>().apply { value = false }
    val isAssetsObtained: LiveData<Boolean> = _isAssetsObtained

    private val _openAssetEvent = MutableLiveData<Event<Long>>()
    val openAssetEvent: LiveData<Event<Long>> = _openAssetEvent

    fun loadAssets(forceUpdate: Boolean) = viewModelScope.launch {
        _isAssetsObtained.value = false
        val result = getAssetsUseCase(forceUpdate)

        if (result is Result.Success && result.data.isNotEmpty()) {
            _items.value = parse(result.data) //result.data
            _isAssetsObtained.value = true
        } else {
            _items.value = emptyList()
        }
    }

    private suspend fun parse(data: List<Asset>) : List<Asset> {
        val list: MutableList<Asset> = mutableListOf()
        data.forEach { asset ->
            val result = getGroupUseCase(asset.groupId)

            if (result is Result.Success && result.data.name.isNotEmpty()) {
                val titleAsset = Asset(-1L, result.data.name, result.data.targetAllocation)
                if (!list.contains(titleAsset)) {
                    list.add(titleAsset)
                }
                Print.i("group name: ${titleAsset.name} group target: ${titleAsset.getTarget()}")
            }
            list.add(asset)
        }
        return list
    }

    fun openAsset(assetId: Long) {
        _openAssetEvent.value = Event(assetId)
    }

}