package com.mx.terryrockstar.rebalancedportfolioapp.groups

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mx.terryrockstar.rebalancedportfolioapp.R
import com.mx.terryrockstar.rebalancedportfolioapp.databinding.FragmentGroupsBinding
import com.mx.terryrockstar.rebalancedportfolioapp.utils.Print
import com.mx.terryrockstar.rebalancedportfolioapp.utils.getViewModelFactory

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class GroupsFragment : Fragment() {

    private var _binding: FragmentGroupsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<GroupsViewModel> { getViewModelFactory() }

    private lateinit var groupAdapter: GroupsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentGroupsBinding.inflate(inflater, container, false).apply {
            this.viewmodel = viewModel
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add) {
            val action = GroupsFragmentDirections.actionAdd(FROM_GROUP)
            findNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        setupGroupAdapter()
        setupShowListGroups()
        viewModel.loadGroups(true)
    }

    private fun setupShowListGroups() {
        viewModel.isGroupsObtained.observe(viewLifecycleOwner) { isGroupsObtained ->
            if (isGroupsObtained) {
                setupListGroups()
                return@observe
            }
            setupEmptyGroups()
        }
    }

    private fun setupGroupAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            groupAdapter = GroupsAdapter(viewModel)
            binding.groupList.adapter = groupAdapter
        } else {
            Print.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun setupEmptyGroups() {
        binding.noData.root.visibility = View.VISIBLE
        binding.noData.titleNoData.text = getString(R.string.no_groups)
    }

    private fun setupListGroups() {
        binding.noData.root.visibility = View.GONE
        binding.groupsContainer.visibility = View.VISIBLE
    }

}

const val FROM_GROUP = 2