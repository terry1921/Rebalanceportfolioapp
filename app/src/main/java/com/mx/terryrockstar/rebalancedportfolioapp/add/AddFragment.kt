package com.mx.terryrockstar.rebalancedportfolioapp.add

import android.os.Bundle
import android.view.*
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mx.terryrockstar.rebalancedportfolioapp.R
import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset
import com.mx.terryrockstar.rebalancedportfolioapp.data.Group
import com.mx.terryrockstar.rebalancedportfolioapp.databinding.FragmentAddAssetBinding
import com.mx.terryrockstar.rebalancedportfolioapp.databinding.FragmentAddGroupBinding
import com.mx.terryrockstar.rebalancedportfolioapp.groups.FROM_GROUP
import com.mx.terryrockstar.rebalancedportfolioapp.home.FROM_HOME
import com.mx.terryrockstar.rebalancedportfolioapp.settings.CURRENCY_PREFERENCE
import com.mx.terryrockstar.rebalancedportfolioapp.utils.EventObserver
import com.mx.terryrockstar.rebalancedportfolioapp.utils.Preferences
import com.mx.terryrockstar.rebalancedportfolioapp.utils.getViewModelFactory

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {

    private var _bindingAsset: FragmentAddAssetBinding? = null
    private var _bindingGroup: FragmentAddGroupBinding? = null
    private val bindingAsset get() = _bindingAsset!!
    private val bindingGroup get() = _bindingGroup!!

    private val viewModel by viewModels<AddEditViewModel> { getViewModelFactory() }

    private var isFrom: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
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
            initAssetUI()
        }

        if (isFrom == FROM_GROUP) {
            initGroupUI()
        }

    }

    private fun initGroupUI() {
        setupSeekBarAllocation()
        setupNavigation()
    }

    private fun setupNavigation() {
        viewModel.groupUpdateEvent.observe(viewLifecycleOwner, EventObserver {
            activity?.onBackPressed()
        })
    }

    private fun setupSeekBarAllocation() {
        bindingGroup.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                bindingGroup.allocation.setText(progress.toString())

            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })
    }

    private fun initAssetUI() {
        val currency = Preferences().getPreference(CURRENCY_PREFERENCE, String::class.java) as String?
        if (!currency.isNullOrEmpty()) {
            bindingAsset.currency.text = currency
        }
        // load groups
        val myAdapter = BindableSpinnerAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, mutableListOf())
        bindingAsset.groups.apply {
            adapter = myAdapter
        }
        // component seekbar allocation
        bindingAsset.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                bindingAsset.allocation.setText(progress.toString())

            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })
        viewModel.items.observe(viewLifecycleOwner) { data ->
            data?.forEach {
                myAdapter.add(it)
            }
        }
        viewModel.loadGroups(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.check) {
            if (isFrom == FROM_HOME) {
                saveAsset()
            }
            if (isFrom == FROM_GROUP) {
                saveGroup()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveGroup() {
        val name = bindingGroup.name.text.toString()
        var allocation = bindingGroup.allocation.text.toString()
        val note = bindingGroup.note.text.toString()

        if (name.isEmpty()) {
            bindingGroup.name.error = getString(R.string.required)
            bindingGroup.name.requestFocus()
            return
        }

        if (allocation.isEmpty()) {
            allocation = getString(R.string.zero)
        }

        val group = Group(name, allocation.toFloat(), note)
        viewModel.saveGroup(group)
    }

    private fun saveAsset() {
        val group = bindingAsset.groups.selectedItem as Group
        val name = bindingAsset.name.text.toString()
        val mount = bindingAsset.mount.text.toString()
        var allocation = bindingAsset.allocation.text.toString()
        val note = bindingAsset.note.text.toString()

        if (name.isEmpty()) {
            bindingAsset.name.error = getString(R.string.required)
            bindingAsset.name.requestFocus()
            return
        }

        if (mount.isEmpty()) {
            bindingAsset.mount.error = getString(R.string.required)
            bindingAsset.mount.requestFocus()
            return
        }

        if (allocation.isEmpty()) {
            allocation = getString(R.string.zero)
        }

        val asset = Asset(group.id, name, mount.toFloat(), allocation.toFloat(), note)
        viewModel.saveAsset(asset)
    }

}