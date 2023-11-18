package id.anantyan.foodapps.presentation.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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
import id.anantyan.foodapps.databinding.FragmentProfileBinding

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private val args: ProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback())
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.toolbar.title = "Profile"
        binding.toolbar.isTitleCentered = true
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_keyboard_backspace)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    @SuppressLint("SetTextI18n")
    private fun bindObserver() {
        viewModel.userResult(args.userId).observe(viewLifecycleOwner) { state: UIState<DataItem> ->
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
                    binding.txtFullname.text = state.data?.firstName+" "+state.data?.lastName
                    binding.txtEmail.text = state.data?.email
                    binding.imgProfile.load(state.data?.avatar) {
                        crossfade(true)
                        placeholder(R.drawable.img_loading)
                        error(R.drawable.img_not_found)
                        size(ViewSizeResolver(binding.imgProfile))
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