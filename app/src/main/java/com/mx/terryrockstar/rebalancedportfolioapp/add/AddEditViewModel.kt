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
    private val _selectedItem = MutableLiveData<Group>().apply { value = Group() }
    val selectedItem: LiveData<Group> = _selectedItem

    init {
        //loadGroups(true)
    }

    fun loadGroups(forceUpdate: Boolean) {
        viewModelScope.launch {
            val result = getGroupsUseCase(forceUpdate)
            val list = mutableListOf<Group>()
            list.add(Group(-1, "Otros", 0.0f))

            if (result is Result.Success && result.data.isNotEmpty()) {
                _items.postValue(result.data)
            } else {
                _items.postValue(list)
            }
        }
    }
}