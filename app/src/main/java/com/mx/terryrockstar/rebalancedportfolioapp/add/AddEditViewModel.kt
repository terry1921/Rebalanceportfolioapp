package com.mx.terryrockstar.rebalancedportfolioapp.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset
import com.mx.terryrockstar.rebalancedportfolioapp.data.Group
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result
import com.mx.terryrockstar.rebalancedportfolioapp.domain.*
import com.mx.terryrockstar.rebalancedportfolioapp.utils.Event
import kotlinx.coroutines.launch

class AddEditViewModel(
    private val getGroupUseCase: GetGroupUseCase,
    private val getAssetUseCase: GetAssetUseCase,
    private val getGroupsUseCase: GetGroupsUseCase,
    private val saveGroupUseCase: SaveGroupUseCase,
    private val saveAssetUseCase: SaveAssetUseCase,
    private val updateGroupUseCase: UpdateGroupUseCase
) : ViewModel() {

    private val _items = MutableLiveData<List<Group>>().apply { value = emptyList() }
    val items: LiveData<List<Group>> = _items

    private val _group = MutableLiveData<Group>().apply { value }
    val group: LiveData<Group> = _group

    private val _asset = MutableLiveData<Asset>().apply { value }
    val asset: LiveData<Asset> = _asset

    private val _updateEvent = MutableLiveData<Event<Unit>>()
    val updateEvent: LiveData<Event<Unit>> = _updateEvent

    fun loadGroups(forceUpdate: Boolean) = viewModelScope.launch {
        val result = getGroupsUseCase(forceUpdate)
        val list = mutableListOf<Group>()
        list.add(Group(0L, "Otros", 0.0f))

        if (result is Result.Success && result.data.isNotEmpty()) {
            list.addAll(result.data)
        }
        _items.postValue(list)
    }

    fun saveAsset(asset: Asset) = viewModelScope.launch {
        saveAssetUseCase(asset)
        _updateEvent.value = Event(Unit)
    }

    fun saveGroup(group: Group) = viewModelScope.launch {
        saveGroupUseCase(group)
        _updateEvent.value = Event(Unit)
    }

    fun updateGroup(group: Group) = viewModelScope.launch {
        updateGroupUseCase(group)
        _updateEvent.value = Event(Unit)
    }

    fun startGroup(groupId: Long) = viewModelScope.launch {
        getGroupUseCase(groupId).let { result ->
            if (result is Result.Success) {
                onGroupLoaded(result.data)
            }
        }
    }

    fun startAsset(assetId: Long) = viewModelScope.launch {
        getAssetUseCase(assetId).let { result ->
            if (result is Result.Success) {
                onAssetLoaded(result.data)
            }
        }
    }

    private fun onGroupLoaded(group: Group) {
        _group.value = group
    }

    private fun onAssetLoaded(asset: Asset) {
        _asset.value = asset
    }

}