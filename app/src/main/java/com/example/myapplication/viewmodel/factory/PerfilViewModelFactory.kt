package com.example.myapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.PerfilRepository
import com.example.myapplication.viewmodel.PerfilViewModel

class PerfilViewModelFactory(
    private val repository: PerfilRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerfilViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PerfilViewModel() as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}