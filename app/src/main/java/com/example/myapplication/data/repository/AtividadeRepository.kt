package com.example.myapplication.data.repository

import com.example.myapplication.BancoDadosProvider
import com.example.myapplication.model.Atividade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AtividadeRepository {
    private val atividadeDao = BancoDadosProvider.db.atividadeDAO()

    suspend fun buscarTodas(): List<Atividade> = withContext(Dispatchers.IO) {
        atividadeDao.buscarTodas()
    }

    suspend fun buscarPorRotina(idRotina: Int): List<Atividade> = withContext(Dispatchers.IO) {
        atividadeDao.buscarPorRotina(idRotina)
    }

    suspend fun inserir(atividade: Atividade) = withContext(Dispatchers.IO) {
        atividadeDao.inserir(atividade)
    }

    suspend fun atualizar(atividade: Atividade) = withContext(Dispatchers.IO) {
        atividadeDao.atualizar(atividade)
    }

    suspend fun excluir(atividade: Atividade) = withContext(Dispatchers.IO) {
        atividadeDao.excluir(atividade)
    }

    suspend fun excluirPorRotina(idRotina: Int) = withContext(Dispatchers.IO) {
        atividadeDao.excluirPorRotina(idRotina)
    }
}