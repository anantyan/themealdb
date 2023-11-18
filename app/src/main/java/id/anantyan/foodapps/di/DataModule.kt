package id.anantyan.foodapps.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.anantyan.foodapps.data.local.datasource.PreferencesDataSource
import id.anantyan.foodapps.data.local.datasource.PreferencesDataSourceImpl
import id.anantyan.foodapps.data.remote.datasource.AuthsRemoteDataSource
import id.anantyan.foodapps.data.remote.datasource.AuthsRemoteDataSourceImpl
import id.anantyan.foodapps.data.remote.datasource.MealsRemoteDataSource
import id.anantyan.foodapps.data.remote.datasource.MealsRemoteDataSourceImpl
import id.anantyan.foodapps.data.remote.service.AuthsApi
import id.anantyan.foodapps.data.remote.service.RecipesApi
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideAuthsRemoteDataSource(@Named("AUTHS") authsApi: AuthsApi): AuthsRemoteDataSource {
        return AuthsRemoteDataSourceImpl(authsApi)
    }

    @Singleton
    @Provides
    fun provideMealsRemoteDataSource(@Named("MEALS") recipesApi: RecipesApi): MealsRemoteDataSource {
        return MealsRemoteDataSourceImpl(recipesApi)
    }

    @Singleton
    @Provides
    fun providePreferencesLocalDataSource(@ApplicationContext context: Context): PreferencesDataSource {
        return PreferencesDataSourceImpl(context)
    }
}