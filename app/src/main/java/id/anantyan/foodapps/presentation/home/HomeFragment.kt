package id.anantyan.foodapps.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.foodapps.NavGraphMainDirections
import id.anantyan.foodapps.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.common.calculateSpanCount
import id.anantyan.foodapps.common.createListDialog
import id.anantyan.foodapps.data.remote.model.DataItem
import id.anantyan.foodapps.data.remote.model.MealsItem
import id.anantyan.foodapps.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), Toolbar.OnMenuItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    @Inject lateinit var usersAdapter: UsersAdapter
    @Inject lateinit var categoriesAdapter: CategoriesAdapter
    @Inject lateinit var mealsAdapter: MealsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback())
        bindObserver()
        bindView()
    }

    private fun onBackPressedCallback() = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    }

    private fun bindView() {
        binding.toolbar.title = "Home"
        binding.toolbar.isTitleCentered = true
        binding.toolbar.setOnMenuItemClickListener(this)

        binding.rvAvatar.setHasFixedSize(true)
        binding.rvAvatar.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvAvatar.itemAnimator = DefaultItemAnimator()
        binding.rvAvatar.isNestedScrollingEnabled = true

        binding.rvCategories.setHasFixedSize(true)
        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvCategories.itemAnimator = DefaultItemAnimator()
        binding.rvCategories.isNestedScrollingEnabled = true

        binding.rvMeals.setHasFixedSize(true)
        binding.rvMeals.layoutManager = StaggeredGridLayoutManager(requireActivity().windowManager.calculateSpanCount(), RecyclerView.VERTICAL)
        binding.rvMeals.itemAnimator = DefaultItemAnimator()
        binding.rvMeals.isNestedScrollingEnabled = true

        usersAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        usersAdapter.addLoadStateListener { states: CombinedLoadStates ->
            if (states.source.refresh is LoadState.NotLoading) {
                binding.rvAvatar.adapter = usersAdapter
            }
        }
        usersAdapter.onClick { _, item ->
            val destination = HomeFragmentDirections.actionHomeFragmentToProfileFragment(item.id ?: -1)
            findNavController().navigate(destination)
        }

        categoriesAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        categoriesAdapter.onClick { _, item ->
            viewModel.mealsResultsByCategory(item.strCategory ?: "")
        }

        mealsAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        mealsAdapter.onClick { _, item ->
            val destination = HomeFragmentDirections.actionHomeFragmentToRecipeFragment(item.idMeal.toString().toInt())
            findNavController().navigate(destination)
        }
    }

    private fun bindObserver() {
        viewModel.mealsResults()

        viewModel.userResults().observe(viewLifecycleOwner) { list: PagingData<DataItem> ->
            usersAdapter.submitData(viewLifecycleOwner.lifecycle, list)
        }

        viewModel.categoryResults().observe(viewLifecycleOwner) { list: List<MealsItem> ->
            categoriesAdapter.submitList(list)
            binding.rvCategories.adapter = categoriesAdapter
        }

        viewModel.mealsResults.observe(viewLifecycleOwner) { list: UIState<List<MealsItem>> ->
            when (list) {
                is UIState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.imgLost.isVisible = false
                    binding.rvMeals.isVisible = false
                }
                is UIState.Success -> {
                    binding.progressBar.isVisible = false
                    binding.imgLost.isVisible = false
                    binding.rvMeals.isVisible = true
                    mealsAdapter.submitList(list.data)
                    binding.rvMeals.adapter = mealsAdapter
                }
                is UIState.Error -> {
                    binding.progressBar.isVisible = false
                    binding.imgLost.isVisible = true
                    binding.rvMeals.isVisible = false
                    Toast.makeText(requireContext(), getString(list.message!!), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.rangeCountry -> {
                val list = listOf("American", "British", "Canadian", "Chinese", "French", "Italian", "Japanese", "Turkish")
                MaterialAlertDialogBuilder(requireContext()).createListDialog("Choose Best Country :", list, onItemClick = { country ->
                    viewModel.mealsResultsByArea(country)
                })
                true
            }
            else -> false
        }
    }
}