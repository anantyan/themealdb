package id.anantyan.foodapps.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.anantyan.foodapps.data.local.dao.RecipesDao
import id.anantyan.foodapps.data.local.database.RoomDB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RoomDB {
        return Room.databaseBuilder(
            context.applicationContext,
            RoomDB::class.java,
            "db_app"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideMealsDao(roomDB: RoomDB): RecipesDao {
        return roomDB.recipesDao()
    }
}