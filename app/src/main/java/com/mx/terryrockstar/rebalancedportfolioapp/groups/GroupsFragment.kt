package com.mx.terryrockstar.rebalancedportfolioapp.groups

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mx.terryrockstar.rebalancedportfolioapp.R
import com.mx.terryrockstar.rebalancedportfolioapp.databinding.FragmentGroupsBinding
import com.mx.terryrockstar.rebalancedportfolioapp.utils.EventObserver
import com.mx.terryrockstar.rebalancedportfolioapp.utils.FROM_GROUP
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
        savedInstanceState: Bundle?): View {
        _binding = FragmentGroupsBinding.inflate(inflater, container, false).apply {
            this.viewmodel = viewModel
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        setupGroupAdapter()
        setupShowListGroups()
        setupNavigation()
        viewModel.loadGroups(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add) {
            val action = GroupsFragmentDirections.actionAdd(FROM_GROUP)
            findNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * > The `viewModel.openGroupEvent` is observed by the `viewLifecycleOwner` and when the event is
     * triggered, the `openGroupDetails` function is called
     */
    private fun setupNavigation() {
        viewModel.openGroupEvent.observe(viewLifecycleOwner, EventObserver(this::openGroupDetails))
    }

    /**
     * > This function navigates to the AddFragment, passing in the groupId and the FROM_GROUP constant
     *
     * @param groupId The id of the group to open
     */
    private fun openGroupDetails(groupId: Long) {
        val action = GroupsFragmentDirections.actionEdit(FROM_GROUP, groupId)
        findNavController().navigate(action)
    }

    /**
     * > If the groups are obtained, then setup the list of groups, otherwise setup the empty groups
     */
    private fun setupShowListGroups() {
        viewModel.isGroupsObtained.observe(viewLifecycleOwner) { isGroupsObtained ->
            if (isGroupsObtained) {
                setupListGroups()
                return@observe
            }
            setupEmptyGroups()
        }
    }

    /**
     * `setupGroupAdapter()` sets up the `GroupAdapter` for the `RecyclerView` in the `Fragment`'s
     * layout
     */
    private fun setupGroupAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            groupAdapter = GroupsAdapter(viewModel)
            binding.groupList.adapter = groupAdapter
        } else {
            Print.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    /**
     * If there are no groups, hide the groups container and show the no data container
     */
    private fun setupEmptyGroups() {
        binding.groupsContainer.visibility = View.GONE
        binding.noData.root.visibility = View.VISIBLE
        binding.noData.titleNoData.text = getString(R.string.no_groups)
    }

    /**
     * `setupListGroups()` is a private function that sets the visibility of the `noData` and
     * `groupsContainer` views to `View.GONE` and `View.VISIBLE` respectively
     */
    private fun setupListGroups() {
        binding.noData.root.visibility = View.GONE
        binding.groupsContainer.visibility = View.VISIBLE
    }

}