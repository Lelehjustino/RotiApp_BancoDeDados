package com.example.myapplication.data.repository

import com.example.myapplication.BancoDadosProvider
import com.example.myapplication.model.Meta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MetaRepository {
    private val metaDao = BancoDadosProvider.db.metaDAO()

    suspend fun buscarTodas(): List<Meta> = withContext(Dispatchers.IO) {
        metaDao.buscarTodas()
    }

    suspend fun inserir(meta: Meta) = withContext(Dispatchers.IO) {
        metaDao.inserir(meta)
    }

    suspend fun atualizar(meta: Meta) = withContext(Dispatchers.IO) {
        metaDao.atualizar(meta)
    }

    suspend fun excluir(meta: Meta) = withContext(Dispatchers.IO) {
        metaDao.excluir(meta)
    }
}