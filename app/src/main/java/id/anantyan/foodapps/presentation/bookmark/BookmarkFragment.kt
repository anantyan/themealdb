package id.anantyan.foodapps.presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.foodapps.common.calculateSpanCount
import id.anantyan.foodapps.databinding.FragmentBookmarkBinding
import id.anantyan.foodapps.domain.model.RecipeModel
import id.anantyan.foodapps.presentation.home.HomeFragmentDirections
import id.anantyan.foodapps.presentation.home.MealsAdapter
import javax.inject.Inject

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BookmarkViewModel by viewModels()
    @Inject lateinit var mealsAdapter: MealsLocalAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback())
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.toolbar.title = "Bookmark"
        binding.toolbar.isTitleCentered = true

        binding.rvBookmark.setHasFixedSize(true)
        binding.rvBookmark.layoutManager = StaggeredGridLayoutManager(requireActivity().windowManager.calculateSpanCount(), RecyclerView.VERTICAL)
        binding.rvBookmark.itemAnimator = DefaultItemAnimator()
        binding.rvBookmark.isNestedScrollingEnabled = true
        binding.rvBookmark.adapter = mealsAdapter

        mealsAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        mealsAdapter.onClick { _, item ->
            val destination = BookmarkFragmentDirections.actionBookmarkFragmentToRecipeFragment(item.id ?: -1)
            findNavController().navigate(destination)
        }
    }

    private fun bindObserver() {
        viewModel.results().observe(viewLifecycleOwner) { list: List<RecipeModel> ->
            if (list.isNotEmpty()) {
                mealsAdapter.submitList(list)
                binding.imgLost.isVisible = false
            } else {
                binding.imgLost.isVisible = true
            }
        }
    }

    private fun onBackPressedCallback() = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}