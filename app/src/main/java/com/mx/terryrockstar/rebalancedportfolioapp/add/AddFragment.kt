package com.mx.terryrockstar.rebalancedportfolioapp.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.mx.terryrockstar.rebalancedportfolioapp.R
import com.mx.terryrockstar.rebalancedportfolioapp.data.Group
import com.mx.terryrockstar.rebalancedportfolioapp.databinding.FragmentAddAssetBinding
import com.mx.terryrockstar.rebalancedportfolioapp.databinding.FragmentAddGroupBinding
import com.mx.terryrockstar.rebalancedportfolioapp.groups.FROM_GROUP
import com.mx.terryrockstar.rebalancedportfolioapp.home.FROM_HOME
import com.mx.terryrockstar.rebalancedportfolioapp.utils.getViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {

    private var _bindingAsset: FragmentAddAssetBinding? = null
    private var _bindingGroup: FragmentAddGroupBinding? = null
    private val bindingAsset get() = _bindingAsset!!
    private val bindingGroup get() = _bindingGroup!!

    private val viewModel by viewModels<AddEditViewModel> { getViewModelFactory() }

    private var isFrom: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val safeArgs: AddFragmentArgs by navArgs()
        isFrom = safeArgs.from

        return when (isFrom) {
            FROM_HOME -> {
                val view = inflater.inflate(R.layout.fragment_add_asset, container, false)

                _bindingAsset = FragmentAddAssetBinding.bind(view).apply {
                    this.viewmodel = viewModel
                }
                bindingAsset.root
            }
            else -> {
                val view = inflater.inflate(R.layout.fragment_add_group, container, false)
                _bindingGroup = FragmentAddGroupBinding.bind(view).apply {
                    viewmodel = viewModel
                }
                bindingGroup.root
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isFrom == FROM_HOME) {
            val myAdapter = BindableSpinnerAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, mutableListOf())

            viewModel.items.observe(viewLifecycleOwner) { data ->
                data?.forEach {
                    myAdapter.add(it)
                }
            }
            bindingAsset.groups.apply {
                adapter = myAdapter
            }

            viewModel.loadGroups(true)
        }

        if (isFrom == FROM_GROUP) {
            //bindingGroup.text.text = "I'm from Groups"

        }

    }

}