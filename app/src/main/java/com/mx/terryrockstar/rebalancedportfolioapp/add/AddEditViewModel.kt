package com.mx.terryrockstar.rebalancedportfolioapp.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.terryrockstar.rebalancedportfolioapp.data.Group
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result
import com.mx.terryrockstar.rebalancedportfolioapp.domain.GetGroupUseCase
import com.mx.terryrockstar.rebalancedportfolioapp.domain.GetGroupsUseCase
import com.mx.terryrockstar.rebalancedportfolioapp.domain.SaveGroupUseCase
import kotlinx.coroutines.launch

class AddEditViewModel(
    private val getGroupUseCase: GetGroupUseCase,
    private val getGroupsUseCase: GetGroupsUseCase,
    private val saveGroupUseCase: SaveGroupUseCase
) : ViewModel() {

    private val _items = MutableLiveData<List<Group>>().apply { value = emptyList() }
    val items: LiveData<List<Group>> = _items

    init {
        //loadGroups(true)
    }

    fun loadGroups(forceUpdate: Boolean) {
        viewModelScope.launch {
            val result = getGroupsUseCase(forceUpdate)
            if (result is Result.Success) {
                if (result.data.isEmpty()) {
                    val list = mutableListOf<Group>()
                    list.add(Group(-1, "Others", 0.0f))
                    _items.value = list
                } else {
                    _items.value = result.data
                }
            } else {
                val list = mutableListOf<Group>()
                list.add(Group(-1, "Others", 0.0f))
                _items.value = list
            }
        }
    }
}