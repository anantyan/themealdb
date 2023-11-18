package id.anantyan.foodapps.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import id.anantyan.foodapps.presentation.home.CategoriesAdapter
import id.anantyan.foodapps.presentation.home.MealsAdapter
import id.anantyan.foodapps.presentation.home.UsersAdapter

@Module
@InstallIn(ActivityComponent::class)
object AdapterModule {
    @Provides
    fun provideUsersAdapter(): UsersAdapter {
        return UsersAdapter()
    }

    @Provides
    fun provideCategoriesAdapter(): CategoriesAdapter {
        return CategoriesAdapter()
    }

    @Provides
    fun provideMealsAdapter(): MealsAdapter {
        return MealsAdapter()
    }
}