package com.mx.terryrockstar.rebalancedportfolioapp.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.terryrockstar.rebalancedportfolioapp.data.Group
import com.mx.terryrockstar.rebalancedportfolioapp.groups.GroupsAdapter

/**
 * [BindingAdapter]s for the [Group]s list.
 */
@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Group>){
    (listView.adapter as GroupsAdapter).submitList(items)
}