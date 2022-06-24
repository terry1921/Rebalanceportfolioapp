package com.mx.terryrockstar.rebalancedportfolioapp.home

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mx.terryrockstar.rebalancedportfolioapp.R
import com.mx.terryrockstar.rebalancedportfolioapp.databinding.FragmentHomeBinding
import com.mx.terryrockstar.rebalancedportfolioapp.utils.*


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AssetsViewModel> { getViewModelFactory() }

    private lateinit var assetAdapter: AssetsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        _binding = FragmentHomeBinding.bind(view).apply {
            this.viewmodel = viewModel
        }
        setHasOptionsMenu(true)
        Print.d("onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        setupAssetAdapter()
        setupShowListAssets()
        setupNavigation()

        val isEdited = Preferences().getPreference(ON_EDIT, Boolean::class.java) as Boolean? ?: false
        viewModel.loadAssets(isEdited)
        if (isEdited) {
            Preferences().setPreference(ON_EDIT, NO_EDITED)
        }
        Print.d("onViewCreated")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Print.d("onAttach")
    }

    override fun onResume() {
        super.onResume()
        Print.d("onResume")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Print.d("onViewStateRestored")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add) {
            val action = HomeFragmentDirections.actionAdd(FROM_HOME)
            findNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * > The `viewModel.openAssetEvent` is observed by the `viewLifecycleOwner` and when the event is
     * triggered, the `openAssetDetails` function is called
     */
    private fun setupNavigation() {
        viewModel.openAssetEvent.observe(viewLifecycleOwner, EventObserver(this::openAssetDetails))
    }

    /**
     * > This function is called when the user clicks on an asset in the list. It navigates to the
     * asset details screen
     *
     * @param assetId The id of the asset to be opened.
     */
    private fun openAssetDetails(assetId: Long) {
        val action = HomeFragmentDirections.actionEdit(FROM_HOME, assetId)
        findNavController().navigate(action)
    }

    /**
     * > This function sets up the adapter for the RecyclerView
     */
    private fun setupAssetAdapter() {
        val viewModel = binding.viewmodel
        if (viewModel != null) {
            assetAdapter = AssetsAdapter(viewModel)
            binding.assetsList.apply {
                setHasFixedSize(true)
                adapter = assetAdapter
            }
        } else {
            Print.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    /**
     * > If the assets are obtained, then setup the list groups, otherwise setup the empty assets
     */
    private fun setupShowListAssets() {
        viewModel.isAssetsObtained.observe(viewLifecycleOwner) { isAssetsObtained ->
            if (isAssetsObtained) {
                setupListGroups()
                return@observe
            }
            setupEmptyAssets()
        }
    }

    /**
     * `setupListGroups()` is a private function that sets up the list groups
     */
    private fun setupListGroups() {
        binding.noData.root.visibility = View.GONE
        binding.assetsContainer.visibility = View.VISIBLE
    }

    /**
     * If the assets list is empty, hide the assets container and show the no data container
     */
    private fun setupEmptyAssets() {
        binding.assetsContainer.visibility = View.GONE
        binding.noData.root.visibility = View.VISIBLE
        binding.noData.titleNoData.text = getString(R.string.no_assets)
    }

}