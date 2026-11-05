package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.viewmodel.uistate.TelaCarregamentoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TelaCarregamentoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TelaCarregamentoUiState())
    val uiState: StateFlow<TelaCarregamentoUiState> = _uiState.asStateFlow()
}