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
    private val saveAssetUseCase: SaveAssetUseCase
) : ViewModel() {

    private val _items = MutableLiveData<List<Group>>().apply { value = emptyList() }
    val items: LiveData<List<Group>> = _items

    private val _group = MutableLiveData<Group>().apply { value }
    val group: LiveData<Group> = _group

    private val _groupUpdateEvent = MutableLiveData<Event<Unit>>()
    val groupUpdateEvent: LiveData<Event<Unit>> = _groupUpdateEvent

    private var isDataLoaded = false

    fun loadGroups(forceUpdate: Boolean) {
        viewModelScope.launch {
            val result = getGroupsUseCase(forceUpdate)
            val list = mutableListOf<Group>()
            list.add(Group(-1, "Otros", 0.0f))

            if (result is Result.Success && result.data.isNotEmpty()) {
                list.addAll(result.data)
            }
            _items.postValue(list)
        }
    }

    fun saveAsset(asset: Asset) {
        viewModelScope.launch {
            saveAssetUseCase(asset)
        }
    }

    fun saveGroup(group: Group) = viewModelScope.launch {
        saveGroupUseCase(group)
        _groupUpdateEvent.value = Event(Unit)
    }

    fun startGroup(groupId: Long) {
        if (isDataLoaded) {
            // No need to populate, already have data.
            return
        }

        viewModelScope.launch {
            getGroupUseCase(groupId).let { result ->
                if (result is Result.Success) {
                    onGroupLoaded(result.data)
                }
            }
        }

    }

    private fun onGroupLoaded(group: Group) {
        _group.value = group
        isDataLoaded = true
    }

}