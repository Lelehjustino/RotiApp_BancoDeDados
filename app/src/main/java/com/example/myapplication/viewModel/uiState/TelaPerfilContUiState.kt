package com.example.myapplication.viewmodel.uistate

data class TelaPerfilContUiState(
    val nome: String = "",
    val email: String = "",
    val salvando: Boolean = false,
    val erro: String? = null
)