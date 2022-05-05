package com.mx.terryrockstar.rebalancedportfolioapp.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.terryrockstar.rebalancedportfolioapp.data.Group
import com.mx.terryrockstar.rebalancedportfolioapp.data.Result.Success
import com.mx.terryrockstar.rebalancedportfolioapp.domain.GetGroupsUseCase
import com.mx.terryrockstar.rebalancedportfolioapp.utils.Event
import kotlinx.coroutines.launch

class GroupsViewModel(
    private val getGroupsUseCase: GetGroupsUseCase,
) : ViewModel() {

    private val _items = MutableLiveData<List<Group>>().apply { value = emptyList() }
    val items: LiveData<List<Group>> = _items

    private val _isGroupsObtained = MutableLiveData<Boolean>()
    val isGroupsObtained: LiveData<Boolean> = _isGroupsObtained

    private val _openGroupEvent = MutableLiveData<Event<Long>>()
    val openGroupEvent: LiveData<Event<Long>> = _openGroupEvent

    /**
     * > It launches a coroutine in the viewModelScope, which calls the getGroupsUseCase, and if the
     * result is successful, it sets the items to the result data, and sets the isGroupsObtained to
     * true
     *
     * @param forceUpdate Boolean - if true, the data will be loaded from the server, otherwise from
     * the cache.
     */
    fun loadGroups(forceUpdate: Boolean) = viewModelScope.launch {
        _isGroupsObtained.value = false
        val result = getGroupsUseCase(forceUpdate)

        if (result is Success && result.data.isNotEmpty()) {
            _items.value = result.data
            _isGroupsObtained.value = true
        } else {
            _items.value = emptyList()
        }
    }

    /**
     * > The function `openGroup` sets the value of the `_openGroupEvent` variable to an Event object
     * that contains the groupId
     *
     * @param groupId The id of the group to open.
     */
    fun openGroup(groupId: Long) {
        _openGroupEvent.value = Event(groupId)
    }

}
