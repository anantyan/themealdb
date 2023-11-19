package id.anantyan.foodapps.presentation.recipe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.ViewSizeResolver
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.foodapps.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.remote.model.DataItem
import id.anantyan.foodapps.data.remote.model.MealsItem
import id.anantyan.foodapps.databinding.FragmentRecipeBinding

@AndroidEntryPoint
class RecipeFragment : Fragment(), Toolbar.OnMenuItemClickListener {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RecipeViewModel by viewModels()
    private val args: RecipeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback())
        bindObserver()
        bindView()
    }

    @SuppressLint("SetTextI18n")
    private fun bindObserver() {
        viewModel.bookmarked.observe(viewLifecycleOwner) { state: Boolean ->
            if (state) {
                binding.toolbar.menu.findItem(R.id.unbookmark).isVisible = false
                binding.toolbar.menu.findItem(R.id.bookmark).isVisible = true
            } else {
                binding.toolbar.menu.findItem(R.id.unbookmark).isVisible = true
                binding.toolbar.menu.findItem(R.id.bookmark).isVisible = false
            }
        }

        viewModel.recipeResult(args.recipeId).observe(viewLifecycleOwner) { state: UIState<MealsItem> ->
            when (state) {
                is UIState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.layoutContent.isVisible = false
                    binding.imgLost.isVisible = false
                }
                is UIState.Success -> {
                    binding.progressBar.isVisible = false
                    binding.layoutContent.isVisible = true
                    binding.imgLost.isVisible = false
                    binding.txtTitle.text = state.data?.strMeal
                    binding.txtArea.text = state.data?.strArea
                    binding.txtCategory.text = state.data?.strCategory
                    binding.txtInstruction.text = state.data?.strInstructions
                    binding.txtIngredient1.text = state.data?.strMeasure1+" "+state.data?.strIngredient1
                    binding.txtIngredient2.text = state.data?.strMeasure2+" "+state.data?.strIngredient2
                    binding.txtIngredient3.text = state.data?.strMeasure3+" "+state.data?.strIngredient3
                    binding.txtIngredient4.text = state.data?.strMeasure4+" "+state.data?.strIngredient4
                    binding.txtIngredient5.text = state.data?.strMeasure5+" "+state.data?.strIngredient5
                    binding.txtIngredient6.text = state.data?.strMeasure6+" "+state.data?.strIngredient6
                    binding.txtIngredient7.text = state.data?.strMeasure7+" "+state.data?.strIngredient7
                    binding.txtIngredient8.text = state.data?.strMeasure8+" "+state.data?.strIngredient8
                    binding.txtIngredient9.text = state.data?.strMeasure9+" "+state.data?.strIngredient9
                    binding.txtIngredient10.text =  state.data?.strMeasure10+" "+state.data?.strIngredient10
                    binding.txtIngredient11.text =  state.data?.strMeasure11+" "+state.data?.strIngredient11
                    binding.txtIngredient12.text =  state.data?.strMeasure12+" "+state.data?.strIngredient12
                    binding.txtIngredient13.text =  state.data?.strMeasure13+" "+state.data?.strIngredient13
                    binding.txtIngredient14.text =  state.data?.strMeasure14+" "+state.data?.strIngredient14
                    binding.txtIngredient15.text =  state.data?.strMeasure15+" "+state.data?.strIngredient15
                    binding.txtIngredient16.text =  state.data?.strMeasure16+" "+state.data?.strIngredient16
                    binding.txtIngredient17.text =  state.data?.strMeasure17+" "+state.data?.strIngredient17
                    binding.txtIngredient18.text =  state.data?.strMeasure18+" "+state.data?.strIngredient18
                    binding.txtIngredient19.text =  state.data?.strMeasure19+" "+state.data?.strIngredient19
                    binding.txtIngredient119.text =  state.data?.strMeasure20+" "+state.data?.strIngredient20
                    binding.imgRecipe.load(state.data?.strMealThumb) {
                        crossfade(true)
                        placeholder(R.drawable.img_loading)
                        error(R.drawable.img_not_found)
                        size(ViewSizeResolver(binding.imgRecipe))
                    }
                    binding.btnYoutube.setOnClickListener {
                        val destination = RecipeFragmentDirections.actionRecipeFragmentToTrailerFragment(state.data?.strYoutube)
                        findNavController().navigate(destination)
                    }
                }
                is UIState.Error -> {
                    binding.progressBar.isVisible = false
                    binding.layoutContent.isVisible = false
                    binding.imgLost.isVisible = true
                    Toast.makeText(requireContext(), getString(state.message!!), Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.recipeCheckResult(args.recipeId)
    }

    private fun bindView() {
        binding.toolbar.title = "Recipe"
        binding.toolbar.isTitleCentered = true
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_keyboard_backspace)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.toolbar.setOnMenuItemClickListener(this)
        binding.toolbar.menu.findItem(R.id.unbookmark).isVisible = true
        binding.toolbar.menu.findItem(R.id.bookmark).isVisible = false
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

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.unbookmark -> {
                viewModel.recipeBookmark(args.recipeId)
                true
            }
            R.id.bookmark -> {
                viewModel.recipeUnbookmark(args.recipeId)
                true
            }
            else -> false
        }
    }
}