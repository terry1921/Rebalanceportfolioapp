package com.mx.terryrockstar.rebalancedportfolioapp.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mx.terryrockstar.rebalancedportfolioapp.R
import com.mx.terryrockstar.rebalancedportfolioapp.data.Asset
import com.mx.terryrockstar.rebalancedportfolioapp.data.Group
import com.mx.terryrockstar.rebalancedportfolioapp.databinding.FragmentAddEditBinding
import com.mx.terryrockstar.rebalancedportfolioapp.settings.CURRENCY_PREFERENCE
import com.mx.terryrockstar.rebalancedportfolioapp.utils.*
import kotlin.math.roundToInt

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class AddEditFragment : Fragment() {

    private lateinit var myAdapter: BindableSpinnerAdapter
    private var asset: Asset? = null
    private var _binding: FragmentAddEditBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: AddEditFragmentArgs by navArgs()

    private val viewModel by viewModels<AddEditViewModel> { getViewModelFactory() }

    private var isFrom: Int = FROM_DEFAULT
    private var id: Long = DEFAULT_ID

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        getArgs()

        _binding = FragmentAddEditBinding.inflate(inflater, container, false).apply {
            this.viewmodel = viewModel
        }

        return binding.root
    }

    /**
     * It gets the arguments from the SafeArgs class.
     */
    private fun getArgs() {
        isFrom = safeArgs.from
        id = safeArgs.id
    }

    override fun onResume() {
        super.onResume()
        if (isFrom == FROM_HOME) initAssetUI()
        if (isFrom == FROM_GROUP) initGroupUI()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.check) {
            when(isFrom) {
                FROM_HOME -> saveAsset()
                FROM_GROUP -> saveGroup()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * > Create/Edit Group UI
     */
    private fun initGroupUI() {
        setupView(FROM_GROUP)
        setupSeekBarAllocation()
        setupAllocationSeekBar()
        setupNavigation()
        setupLoadGroup()
    }

    /**
     * > Create/Edit Asset UI
     */
    private fun initAssetUI() {
        setupView(FROM_HOME)
        setupPopulateGroupSpinner()
        setupCurrency()
        setupSeekBarAllocation()
        setupAllocationSeekBar()
        setupNavigation()
        setupLoadAsset()
    }

    /**
     * > When the user is coming from the group screen, hide the mount and group containers. Otherwise,
     * show them
     *
     * @param from The source of the call.
     */
    private fun setupView(from: Int) {
        when(from) {
            FROM_GROUP -> {
                binding.containerMount.visibility = View.GONE
                binding.containerGroups.visibility = View.GONE
            }
            FROM_HOME -> {
                binding.containerMount.visibility = View.VISIBLE
                binding.containerGroups.visibility = View.VISIBLE
            }
        }
    }

    /**
     * > We're setting up a listener for the seekbar, and when the user stops touching the seekbar,
     * we're updating the allocation textview with the progress of the seekbar
     */
    private fun setupSeekBarAllocation() {
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.allocation.setText(progress.toString())
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })
    }

    /**
     * > This function adds a TextWatcher to the EditText and updates the SeekBar's progress when the
     * EditText's text changes
     */
    private fun setupAllocationSeekBar() {
        binding.allocation.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                s?.let { number ->
                    if (number.isNotEmpty()) {
                        val progress = number.toString().toFloat().roundToInt()
                        if (progress > 100) {
                            binding.allocation.setText(getString(R.string._100))
                        }
                        binding.seekbar.progress = progress
                        binding.allocation.setSelection(number.length)
                    }
                }
            }
        })
    }

    /**
     * > Observe the `groupUpdateEvent` LiveData object and when it changes, call the `onBackPressed`
     * function on the activity
     */
    private fun setupNavigation() {
        viewModel.updateEvent.observe(viewLifecycleOwner, EventObserver {
            Preferences().setPreference(ON_EDIT, EDITED)
            activity?.onBackPressed()
        })
    }

    /**
     * > If the id is not the default id, then start the group and observe the group
     */
    private fun setupLoadGroup() {
        if (id != DEFAULT_ID) {
            viewModel.startGroup(id)
            viewModel.group.observe(viewLifecycleOwner) { group ->
                binding.name.setText(group.name)
                binding.note.setText(group.note)
                binding.seekbar.progress = group.getTargetInt()
                binding.allocation.setText(group.getTargetNumber())
            }
        }
    }

    /**
     * > This function sets the currency text in the UI to the currency saved in the shared preferences
     */
    private fun setupCurrency() {
        val currency = Preferences().getPreference(CURRENCY_PREFERENCE, String::class.java) as String?
        if (!currency.isNullOrEmpty()) {
            binding.currency.text = currency
        }
    }

    /**
     * > Creating a new adapter, setting it to the spinner, and then observing the data from the
     * view model
     */
    private fun setupPopulateGroupSpinner() {
        myAdapter = BindableSpinnerAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, mutableListOf())
        binding.groups.apply {
            adapter = myAdapter
        }
        viewModel.items.observe(viewLifecycleOwner) { data ->
            data?.forEach {
                myAdapter.add(it)
            }
            if (id != DEFAULT_ID && data.isNotEmpty()) {
                asset?.let {
                    selectGroupByAsset(it.groupId)
                }
            }
        }
        viewModel.loadGroups(true)
    }

    /**
     * > If the id is not the default id, then start the asset and observe the asset
     */
    private fun setupLoadAsset() {
        if (id != DEFAULT_ID) {
            viewModel.startAsset(id)
            viewModel.asset.observe(viewLifecycleOwner) {
                this.asset = it
                fillAssetForm(it)
            }
        }
    }

    /**
     * > This function takes an asset object and fills the form with the asset's data
     *
     * @param asset Asset - the asset object that is being edited
     */
    private fun fillAssetForm(asset: Asset) {
        binding.name.setText(asset.name)
        binding.note.setText(asset.note)
        binding.mount.setText(asset.getMountString())
        binding.seekbar.progress = asset.getTargetInt()
        binding.allocation.setText(asset.getTargetNumber())
    }

    /**
     * > The function takes a groupId as a parameter, finds the position of the groupId in the adapter,
     * and then sets the selection of the spinner to that position
     *
     * @param groupId The id of the group you want to select.
     */
    private fun selectGroupByAsset(groupId: Long) {
        val position = myAdapter.getPosition(groupId)
        binding.groups.setSelection(position)
    }

    /**
     * > The function saves a group to the database
     *
     * @return A group object is being returned.
     */
    private fun saveGroup() {
        val group = getDataGroup() ?: return
        if (id != DEFAULT_ID) {
            viewModel.updateGroup(group)
            return
        }
        viewModel.saveGroup(group)
    }

    /**
     * It saves an asset to the database
     *
     * @return the value of the function.
     */
    private fun saveAsset() {
        val group = binding.groups.selectedItem as Group
        val name = binding.name.text.toString()
        val mount = binding.mount.text.toString()
        var allocation = binding.allocation.text.toString()
        val note = binding.note.text.toString()

        if (name.isEmpty()) {
            binding.name.error = getString(R.string.required)
            binding.name.requestFocus()
            return
        }

        if (mount.isEmpty()) {
            binding.mount.error = getString(R.string.required)
            binding.mount.requestFocus()
            return
        }

        if (allocation.isEmpty()) {
            allocation = getString(R.string.zero)
        }

        val asset = Asset(group.id, name, mount.toFloat(), allocation.toFloat(), note)
        viewModel.saveAsset(asset)
    }

    /**
     * If the name field is empty, set an error message and return null. Otherwise, if the allocation
     * field is empty, set it to zero. Otherwise, return a new Group object with the values from the
     * fields
     *
     * @return A Group object
     */
    private fun getDataGroup() : Group? {
        val name = binding.name.text.toString()
        if (name.isEmpty()) {
            binding.name.error = getString(R.string.required)
            binding.name.requestFocus()
            return null
        }
        var allocation = binding.allocation.text.toString()
        if (allocation.isEmpty()) {
            allocation = getString(R.string.zero)
        }
        val note = binding.note.text.toString()

        return Group(id, name, allocation.toFloat(), note)
    }

}