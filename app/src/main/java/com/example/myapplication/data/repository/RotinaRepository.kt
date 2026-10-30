package com.example.myapplication.data.repository

import com.example.myapplication.model.Atividade
import com.example.myapplication.model.Rotina
import com.example.myapplication.BancoDadosProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RotinaRepository {
    private val db = BancoDadosProvider.db

    suspend fun buscarRotinaEAtividades(idRotina: Int): Pair<Rotina?, List<Atividade>> {
        return withContext(Dispatchers.IO) {
            val rotinaEncontrada = db.rotinaDAO()
                .buscarTodas()
                .find { it.idRotina == idRotina }

            val atividades = db.atividadeDAO()
                .buscarPorRotina(idRotina)

            Pair(rotinaEncontrada, atividades)
        }
    }

    suspend fun excluirAtividade(atividade: Atividade) {
        withContext(Dispatchers.IO) {
            db.atividadeDAO().excluir(atividade)
        }
    }
}

/*
package com.example.myapplication.repository

import com.example.myapplication.BancoDadosProvider
import com.example.myapplication.model.Rotina
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RotinaRepository {
    private val rotinaDao = BancoDadosProvider.db.rotinaDAO()

    suspend fun buscarTodas(): List<Rotina> = withContext(Dispatchers.IO) {
        rotinaDao.buscarTodas()
    }

    suspend fun inserir(rotina: Rotina): Long = withContext(Dispatchers.IO) {
        rotinaDao.inserir(rotina)
    }

    suspend fun atualizar(rotina: Rotina) = withContext(Dispatchers.IO) {
        rotinaDao.atualizar(rotina)
    }

    suspend fun excluir(rotina: Rotina) = withContext(Dispatchers.IO) {
        rotinaDao.excluir(rotina)
    }

    // Método combinado útil caso queira buscar a rotina e suas atividades juntas
    suspend fun buscarRotinaEAtividades(idRotina: Int): Pair<Rotina?, List<AtividadeDaRotina>> {
        // Exemplo se precisar juntar com o DAO de Atividades
        // ...
    }
}
* */