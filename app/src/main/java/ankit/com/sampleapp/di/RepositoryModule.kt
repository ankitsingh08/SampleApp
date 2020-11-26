package ankit.com.sampleapp.di

import ankit.com.sampleapp.data.repository.RestaurantsRepositoryImpl
import ankit.com.sampleapp.domain.repository.RestaurantsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * Created by AnkitSingh on 11/25/20.
 */
@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRestaurantsRepository(restaurantsRepositoryImpl: RestaurantsRepositoryImpl): RestaurantsRepository
}