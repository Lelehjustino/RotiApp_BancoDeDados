package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Historico
import com.example.myapplication.model.listaMetas
import com.example.myapplication.model.listaRotinas
import com.example.myapplication.viewmodel.uistate.TelaHistoricoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HistoricoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        TelaHistoricoUiState(
            totalRotinas = listaRotinas.size,
            totalMetas = listaMetas.size
        )
    )
    val uiState: StateFlow<TelaHistoricoUiState> = _uiState.asStateFlow()

    fun carregarDados() {
        _uiState.update {
            it.copy(
                totalRotinas = listaRotinas.size,
                totalMetas = listaMetas.size
            )
        }
    }
}