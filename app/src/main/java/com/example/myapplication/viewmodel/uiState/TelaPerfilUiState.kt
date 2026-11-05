package com.example.myapplication.viewmodel.uistate

import com.example.myapplication.model.Perfil

data class PerfilUiState(
    val perfil: Perfil = Perfil(),
    val carregando: Boolean = false,
    val salvando: Boolean = false,
    val erro: String? = null
)