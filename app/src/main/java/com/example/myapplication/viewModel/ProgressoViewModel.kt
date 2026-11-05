package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import com.example.myapplication.viewmodel.uistate.ProgressoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProgressoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProgressoUiState(nivel = 1, xpAcumulado = 50, progressoIndicator = 0.5f, xpNoNivel = 50))
    val uiState: StateFlow<ProgressoUiState> = _uiState.asStateFlow()
}