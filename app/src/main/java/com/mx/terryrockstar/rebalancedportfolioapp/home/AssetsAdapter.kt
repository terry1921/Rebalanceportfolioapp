package com.mx.terryrockstar.rebalancedportfolioapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.terryrockstar.rebalancedportfolioapp.R
import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset
import com.mx.terryrockstar.rebalancedportfolioapp.databinding.ItemAssetBinding

class AssetsAdapter(private val viewModel: AssetsViewModel) :
ListAdapter<Asset, AssetsAdapter.ViewHolder>(AssetDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) as Asset
        holder.bind(viewModel, item)
    }

    class ViewHolder private constructor(private val binding: ItemAssetBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(viewModel: AssetsViewModel, item: Asset) {
                binding.viewmodel = viewModel
                binding.asset = item
                if (item.id == -1L) {
                    binding.groupName.setBackgroundResource(R.color.colorPrimaryDark)

                    binding.assetContainer.visibility = View.GONE
                    binding.groupName.visibility = View.VISIBLE
                } else {
                    binding.groupName.visibility = View.GONE
                    binding.assetContainer.visibility = View.VISIBLE
                }

            }

            companion object {
                fun from(parent: ViewGroup) : ViewHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = ItemAssetBinding.inflate(layoutInflater, parent, false)

                    return ViewHolder(binding)
                }
            }

    }

}

class AssetDiffCallback : DiffUtil.ItemCallback<Asset>() {
    override fun areItemsTheSame(oldItem: Asset, newItem: Asset): Boolean {
        return "${oldItem.id}-${oldItem.name}" == "${newItem.id}-${newItem.name}"
    }

    override fun areContentsTheSame(oldItem: Asset, newItem: Asset): Boolean {
        return oldItem == newItem
    }
}