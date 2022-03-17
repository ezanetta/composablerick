package com.ezanetta.composablerick.di

import com.ezanetta.composablerick.data.network.RickAndMortyApiClient
import com.ezanetta.composablerick.data.repository.NetworkAllCharactersRepository
import com.ezanetta.composablerick.data.repository.NetworkCharacterRepository
import com.ezanetta.composablerick.domain.repository.AllCharactersRepository
import com.ezanetta.composablerick.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideCharacterRepository(
        rickAndMortyApiClient: RickAndMortyApiClient,
    ): CharacterRepository {
        return NetworkCharacterRepository(rickAndMortyApiClient)
    }

    @Provides
    fun provideAllCharactersRepository(
        rickAndMortyApiClient: RickAndMortyApiClient,
    ): AllCharactersRepository {
        return NetworkAllCharactersRepository(rickAndMortyApiClient)
    }
}