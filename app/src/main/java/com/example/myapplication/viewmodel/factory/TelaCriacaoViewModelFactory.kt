package com.example.myapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.RotinaRepository
import com.example.myapplication.data.repository.AtividadeRepository
import com.example.myapplication.viewmodel.TelaCriacaoViewModel

class TelaCriacaoViewModelFactory(
    private val rotinaRepository: RotinaRepository,
    private val atividadeRepository: AtividadeRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TelaCriacaoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TelaCriacaoViewModel() as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}