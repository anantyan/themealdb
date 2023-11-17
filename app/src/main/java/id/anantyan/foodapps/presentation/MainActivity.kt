package id.anantyan.foodapps.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.foodapps.NavGraphMainDirections
import id.anantyan.foodapps.R
import id.anantyan.foodapps.databinding.ActivityMainBinding
import id.anantyan.foodapps.presentation.login.LoginFragmentDirections

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
    }

    private fun bindView() {
        val host = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = host.navController
        navController.addOnDestinationChangedListener(this)

        if (viewModel.getLogin) {
            val destination = LoginFragmentDirections.actionRootToHomeFragment()
            navController.navigate(destination)
        } else {
            val destination = NavGraphMainDirections.actionRootToLoginFragment()
            navController.navigate(destination)
        }

        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.homeFragment -> binding.bottomNav.isVisible = true
            R.id.usersFragment -> binding.bottomNav.isVisible = true
            R.id.bookmarkFragment -> binding.bottomNav.isVisible = true
            else -> binding.bottomNav.isVisible = false
        }
    }
}