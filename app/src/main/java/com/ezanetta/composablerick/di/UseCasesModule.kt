package com.ezanetta.composablerick.di

import com.ezanetta.composablerick.domain.repository.AllCharactersRepository
import com.ezanetta.composablerick.domain.repository.CharacterRepository
import com.ezanetta.composablerick.domain.usecase.GetAllCharactersUseCase
import com.ezanetta.composablerick.domain.usecase.GetAllCharactersUseCaseImpl
import com.ezanetta.composablerick.domain.usecase.GetCharacterUseCase
import com.ezanetta.composablerick.domain.usecase.GetCharacterUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @Provides
    fun provideGetCharacterUseCase(
        characterRepository: CharacterRepository,
    ): GetCharacterUseCase {
        return GetCharacterUseCaseImpl(characterRepository)
    }

    @Provides
    fun provideGetAllCharactersUseCase(
        allCharactersRepository: AllCharactersRepository,
    ): GetAllCharactersUseCase {
        return GetAllCharactersUseCaseImpl(allCharactersRepository)
    }
}