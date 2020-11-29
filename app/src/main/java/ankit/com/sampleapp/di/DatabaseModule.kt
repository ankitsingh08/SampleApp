package ankit.com.sampleapp.di

import android.content.Context
import androidx.room.Room
import ankit.com.sampleapp.data.database.RestaurantsDao
import ankit.com.sampleapp.data.database.RestaurantsDataBase
import ankit.com.sampleapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Created by AnkitSingh on 11/28/20.
 */
@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideRestaurantDao(restaurantsDataBase: RestaurantsDataBase): RestaurantsDao {
        return restaurantsDataBase.restaurantsDao()
    }

    @Provides
    @Singleton
    fun provideRestaurantDatabase(@ApplicationContext appContext: Context): RestaurantsDataBase {
        return Room.databaseBuilder(
            appContext,
            RestaurantsDataBase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }
}
