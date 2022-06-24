package com.mx.terryrockstar.rebalancedportfolioapp.add

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.mx.terryrockstar.rebalancedportfolioapp.data.Group

class BindableSpinnerAdapter(context: Context, textViewResourceId: Int, private val values: List<Group>) :
    ArrayAdapter<Group>(context, textViewResourceId, values) {

    override fun getCount() = values.size

    override fun getItem(position: Int) = values[position]

    override fun getItemId(position: Int) = values[position].id

    fun getPosition(id: Long): Int {
        for (value in values) {
            if (value.id == id) {
                return super.getPosition(value)
            }
        }
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.text = values[position].name
        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView
        label.text = values[position].name
        return label
    }

}