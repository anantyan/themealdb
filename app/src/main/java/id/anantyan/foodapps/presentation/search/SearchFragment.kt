package id.anantyan.foodapps.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.search.SearchView
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.common.calculateSpanCount
import id.anantyan.foodapps.data.remote.model.MealsItem
import id.anantyan.foodapps.databinding.FragmentSearchBinding
import id.anantyan.foodapps.presentation.MainActivity
import id.anantyan.foodapps.presentation.home.HomeFragmentDirections
import id.anantyan.foodapps.presentation.home.MealsAdapter
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    @Inject lateinit var mealsAdapter: MealsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback())
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.searchBar.isDefaultScrollFlagsEnabled = false

        binding.searchView.setupWithSearchBar(binding.searchBar)
        binding.searchView.editText.addTextChangedListener(onTextWatcher())
        binding.searchView.addTransitionListener(onSearchViewShow())

        binding.rvSearch.setHasFixedSize(true)
        binding.rvSearch.layoutManager = StaggeredGridLayoutManager(requireActivity().windowManager.calculateSpanCount(), RecyclerView.VERTICAL)
        binding.rvSearch.itemAnimator = DefaultItemAnimator()
        binding.rvSearch.isNestedScrollingEnabled = true

        mealsAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        mealsAdapter.onClick { _, item ->
            val destination = SearchFragmentDirections.actionSearchFragmentToRecipeFragment(item.idMeal.toString().toInt())
            findNavController().navigate(destination)
        }
    }

    private fun onTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0.isNullOrEmpty()) {
                mealsAdapter.submitList(emptyList())
            } else {
                viewModel.results(p0.toString())
            }
        }
        override fun afterTextChanged(p0: Editable?) { }
    }

    private fun onSearchViewShow() = SearchView.TransitionListener { _, _, newState ->
        if (newState == SearchView.TransitionState.SHOWN) {
            (requireActivity() as MainActivity).bottomNav(false)
        } else {
            (requireActivity() as MainActivity).bottomNav(true)
        }
    }

    private fun bindObserver() {
        viewModel.results.observe(viewLifecycleOwner) { state: UIState<List<MealsItem>> ->
            when (state) {
                is UIState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.rvSearch.isVisible = false
                }
                is UIState.Success -> {
                    binding.progressBar.isVisible = false
                    binding.rvSearch.isVisible = true
                    mealsAdapter.submitList(state.data)
                    binding.rvSearch.adapter = mealsAdapter
                }
                is UIState.Error -> {
                    binding.progressBar.isVisible = false
                    binding.rvSearch.isVisible = false
                    Toast.makeText(requireContext(), getString(state.message!!), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun onBackPressedCallback() = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.searchView.isShowing) {
                binding.searchView.hide()
            } else {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}