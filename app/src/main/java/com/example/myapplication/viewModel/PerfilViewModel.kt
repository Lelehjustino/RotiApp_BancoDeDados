package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Perfil
import com.example.myapplication.viewmodel.uistate.PerfilUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PerfilViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    fun salvarPerfil(nome: String, email: String, onSuccess: () -> Unit) {
        if (nome.isBlank() || email.isBlank()) {
            _uiState.update { it.copy(erro = "Preencha todos os campos.") }
            return
        }
        _uiState.update {
            it.copy(
                perfil = Perfil(nome = nome, email = email),
                erro = null
            )
        }
        onSuccess()
    }
}