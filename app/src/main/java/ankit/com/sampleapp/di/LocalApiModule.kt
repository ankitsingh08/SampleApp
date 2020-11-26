package ankit.com.sampleapp.di

import android.content.Context
import ankit.com.sampleapp.data.local.RestaurantsInformationAPI
import ankit.com.sampleapp.data.local.RestaurantsInformationAPIToEntityMapper
import ankit.com.sampleapp.data.repository.RestaurantsDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Created by AnkitSingh on 11/25/20.
 */
@Module
@InstallIn(ApplicationComponent::class)
class LocalApiModule {
    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    internal fun provideRestaurantsInformationAPIToEntityMapper(gson: Gson):
            RestaurantsInformationAPIToEntityMapper {
        return RestaurantsInformationAPIToEntityMapper(gson)
    }

    @Provides
    @Singleton
    internal fun provideRestaurantsInformationAPI(@ApplicationContext appContext: Context):
            RestaurantsInformationAPI {
        return RestaurantsInformationAPI(appContext)
    }

    @Provides
    @Singleton
    internal fun provideRestaurantsDataSource(
        restaurantsInformationAPI: RestaurantsInformationAPI,
        restaurantsInformationAPIToEntityMapper: RestaurantsInformationAPIToEntityMapper
    )
            : RestaurantsDataSource {
        return RestaurantsDataSource(
            restaurantsInformationAPI,
            restaurantsInformationAPIToEntityMapper
        )
    }
}