package com.ezanetta.composablerick.di

import com.ezanetta.composablerick.data.network.RickAndMortyApiClient
import com.ezanetta.composablerick.data.network.RickAndMortyApiService
import com.ezanetta.composablerick.data.repository.NetworkCharacterRepository
import com.ezanetta.composablerick.domain.repository.CharacterRepository
import com.ezanetta.composablerick.domain.usecase.GetCharacterUseCase
import com.ezanetta.composablerick.domain.usecase.GetCharacterUseCaseImpl
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
}