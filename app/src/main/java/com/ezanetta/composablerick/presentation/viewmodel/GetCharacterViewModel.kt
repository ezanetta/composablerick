package com.ezanetta.composablerick.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezanetta.composablerick.domain.usecase.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetCharacterViewModel @Inject constructor(
    private val characterUseCase: GetCharacterUseCase
) : ViewModel() {

}