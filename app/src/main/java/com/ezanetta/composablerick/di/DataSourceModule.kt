package com.ezanetta.composablerick.di

import com.ezanetta.composablerick.data.datasource.AllCharactersDataSource
import com.ezanetta.composablerick.data.network.RickAndMortyApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DataSourceModule {

    @Provides
    fun provideAllCharactersDataSource(
        rickAndMortyApiClient: RickAndMortyApiClient,
    ): AllCharactersDataSource {
        return AllCharactersDataSource(rickAndMortyApiClient)
    }
}