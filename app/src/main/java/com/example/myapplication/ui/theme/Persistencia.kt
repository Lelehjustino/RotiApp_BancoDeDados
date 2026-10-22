
package com.example.myapplication.ui.theme

import com.example.myapplication.BancoDadosProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun carregarDadosSalvos() {
    val dados = withContext(Dispatchers.IO) {
        val db = BancoDadosProvider.db

        Triple(
            db.rotinaDAO().buscarTodas(),
            db.atividadeDAO().buscarTodas(),
            Pair(
                db.historicoDAO().buscarTodos(),
                db.metaDAO().buscarTodas()
            )
        )
    }

    listaRotinas.clear()
    listaRotinas.addAll(dados.first)

    listaAtividades.clear()
    listaAtividades.addAll(dados.second)

    listaHistorico.clear()
    listaHistorico.addAll(dados.third.first)

    listaMetas.clear()
    listaMetas.addAll(dados.third.second)
}
