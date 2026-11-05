package com.example.myapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.HistoricoRepository
import com.example.myapplication.viewmodel.HistoricoViewModel

class HistoricoViewModelFactory(
    private val repository: HistoricoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoricoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoricoViewModel() as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}