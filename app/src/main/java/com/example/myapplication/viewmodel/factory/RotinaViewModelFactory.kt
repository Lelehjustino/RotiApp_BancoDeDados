package com.example.myapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.RotinaRepository
import com.example.myapplication.viewmodel.RotinaViewModel

class RotinaViewModelFactory(
    private val repository: RotinaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RotinaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RotinaViewModel(repository) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}