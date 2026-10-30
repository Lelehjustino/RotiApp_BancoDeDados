package com.example.myapplication.data.repository

import com.example.myapplication.BancoDadosProvider
import com.example.myapplication.model.Historico
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HistoricoRepository {
    private val historicoDao = BancoDadosProvider.db.historicoDAO()

    suspend fun buscarTodos(): List<Historico> = withContext(Dispatchers.IO) {
        historicoDao.buscarTodos()
    }

    suspend fun inserir(historico: Historico) = withContext(Dispatchers.IO) {
        historicoDao.inserir(historico)
    }

    suspend fun excluir(historico: Historico) = withContext(Dispatchers.IO) {
        historicoDao.excluir(historico)
    }
}