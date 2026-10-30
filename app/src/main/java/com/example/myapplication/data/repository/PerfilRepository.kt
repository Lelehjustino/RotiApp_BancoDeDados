package com.example.myapplication.data.repository

import com.example.myapplication.BancoDadosProvider
import com.example.myapplication.model.Perfil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PerfilRepository {
    private val perfilDao = BancoDadosProvider.db.perfilDAO()

    suspend fun salvarPerfil(perfil: Perfil) = withContext(Dispatchers.IO) {
        perfilDao.salvarPerfil(perfil)
    }

    suspend fun buscarPerfil(): Perfil? = withContext(Dispatchers.IO) {
        perfilDao.buscarPerfil()
    }
}