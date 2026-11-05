package com.example.myapplication.viewmodel.uistate

data class ProgressoUiState(
    val nivel: Int = 1,
    val xpAcumulado: Int = 0,
    val progressoIndicator: Float = 0f,
    val xpNoNivel: Int = 0
)