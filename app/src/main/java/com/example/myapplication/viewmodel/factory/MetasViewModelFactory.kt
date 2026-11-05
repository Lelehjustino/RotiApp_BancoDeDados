package com.example.myapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.MetaRepository
import com.example.myapplication.viewmodel.MetasViewModel

class MetasViewModelFactory(
    private val repository: MetaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MetasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MetasViewModel() as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}