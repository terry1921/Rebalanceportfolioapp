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

class AssetsAdapter(private val viewModel: AssetsViewModel) : ListAdapter<Asset, AssetsAdapter.ViewHolder>(AssetDiffCallback()){

    private var positionHeader = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) as Asset
        if (item.id == -1L) {
            holder.bindHeader(item, positionHeader)
            positionHeader++
        } else {
            holder.bind(viewModel, item, positionHeader)
        }
    }

    class ViewHolder private constructor(private val binding: ItemAssetBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(viewModel: AssetsViewModel, item: Asset, positionHeader: Int) {
                binding.viewmodel = viewModel
                binding.asset = item

                if (positionHeader % 2 == 0) {
                    binding.groupIndicator.setBackgroundResource(R.color.police_blue)
                } else {
                    binding.groupIndicator.setBackgroundResource(R.color.ebony_clay)
                }

                binding.groupName.visibility = View.GONE
                binding.groupIndicator.visibility = View.VISIBLE
                binding.assetContainer.visibility = View.VISIBLE
            }

            fun bindHeader(item: Asset, positionHeader: Int) {
                binding.asset = item
                if (positionHeader % 2 == 0) {
                    binding.groupName.setBackgroundResource(R.color.ebony_clay)
                    binding.groupIndicator.setBackgroundResource(R.color.ebony_clay)
                } else {
                    binding.groupName.setBackgroundResource(R.color.police_blue)
                    binding.groupIndicator.setBackgroundResource(R.color.police_blue)
                }
                binding.assetContainer.visibility = View.GONE
                binding.groupName.visibility = View.VISIBLE
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
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: Asset, newItem: Asset): Boolean {
        return oldItem == newItem
    }
}