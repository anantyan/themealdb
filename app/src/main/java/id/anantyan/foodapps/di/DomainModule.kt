package id.anantyan.foodapps.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.anantyan.foodapps.data.local.datasource.MealsLocalDataSource
import id.anantyan.foodapps.data.local.datasource.PreferencesDataSource
import id.anantyan.foodapps.data.remote.datasource.AuthsRemoteDataSource
import id.anantyan.foodapps.data.remote.datasource.MealsRemoteDataSource
import id.anantyan.foodapps.data.repository.MainRepositoryImpl
import id.anantyan.foodapps.domain.repository.MainRepository
import id.anantyan.foodapps.domain.repository.MainUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Singleton
    @Provides
    fun provideMainRepository(
        mealsRemoteDataSource: MealsRemoteDataSource,
        authsRemoteDataSource: AuthsRemoteDataSource,
        mealsLocalDataSource: MealsLocalDataSource,
        preferencesDataSource: PreferencesDataSource
    ): MainRepository {
        return MainRepositoryImpl(
            mealsRemoteDataSource,
            authsRemoteDataSource,
            mealsLocalDataSource,
            preferencesDataSource
        )
    }

    @Singleton
    @Provides
    fun provideMainUseCase(
        mainRepository: MainRepository
    ): MainUseCase {
        return MainUseCase(mainRepository)
    }
}