package com.mx.terryrockstar.rebalancedportfolioapp.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.terryrockstar.rebalancedportfolioapp.data.Group
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result.Success
import com.mx.terryrockstar.rebalancedportfolioapp.domain.GetGroupsUseCase
import kotlinx.coroutines.launch

class GroupsViewModel(
    private val getGroupsUseCase: GetGroupsUseCase,
) : ViewModel() {

    private val _items = MutableLiveData<List<Group>>().apply { value = emptyList() }
    val items: LiveData<List<Group>> = _items

    private val _isGroupsObtained = MutableLiveData<Boolean>()
    val isGroupsObtained: LiveData<Boolean> = _isGroupsObtained

    init {
        // loadGroups(true)
    }

    fun loadGroups(forceUpdate: Boolean) {
        viewModelScope.launch {
            _isGroupsObtained.value = false
            val result = getGroupsUseCase(forceUpdate)

            if (result is Success && result.data.isNotEmpty()) {
                _items.value = result.data
                _isGroupsObtained.value = true
            } else {
                _items.value = emptyList()
            }
        }
    }

    fun openGroup(groupId: Long) {

    }

}
