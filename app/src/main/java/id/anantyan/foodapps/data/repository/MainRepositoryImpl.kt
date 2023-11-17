package id.anantyan.foodapps.data.repository

import android.util.Log
import id.anantyan.foodapps.data.local.datasource.PreferencesDataSource
import id.anantyan.foodapps.data.remote.datasource.MealsRemoteDataSource
import id.anantyan.foodapps.data.remote.model.MealsItem
import id.anantyan.foodapps.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class MainRepositoryImpl(
    private val mealsRemoteDataSource: MealsRemoteDataSource,
    private val preferencesDataSource: PreferencesDataSource
) : MainRepository {
    override suspend fun results(query: String?): List<MealsItem> {
        return try {
            mealsRemoteDataSource.results(query)
        } catch (e: Exception) {
            Log.d("MEALS-DEBUG", e.message.toString())
            emptyList()
        }
    }

    override suspend fun result(id: Int): MealsItem? {
        return try {
            mealsRemoteDataSource.result(id)
        } catch (e: Exception) {
            Log.d("MEALS-DEBUG", e.message.toString())
            null
        }
    }

    override suspend fun categories(): List<MealsItem> {
        return try {
            mealsRemoteDataSource.categories()
        } catch (e: Exception) {
            Log.d("MEALS-DEBUG", e.message.toString())
            emptyList()
        }
    }

    override suspend fun areas(): List<MealsItem> {
        return try {
            mealsRemoteDataSource.areas()
        } catch (e: Exception) {
            Log.d("MEALS-DEBUG", e.message.toString())
            emptyList()
        }
    }

    override suspend fun setLogin(value: Boolean) {
        preferencesDataSource.setLogin(value)
    }

    override fun getLogin(): Flow<Boolean> {
        return preferencesDataSource.getLogin()
    }

    override suspend fun setUsrId(value: Int) {
        preferencesDataSource.setUsrId(value)
    }

    override fun getUsrId(): Flow<Int> {
        return preferencesDataSource.getUsrId()
    }
}