
package com.example.myapplication.ui.theme

import com.example.myapplication.BancoDadosProvider

fun carregarDadosSalvos() {
    val db = BancoDadosProvider.db

    listaRotinas.clear()
    listaRotinas.addAll(db.rotinaDAO().buscarTodas())

    listaAtividades.clear()
    listaAtividades.addAll(db.atividadeDAO().buscarTodas())

    listaHistorico.clear()
    listaHistorico.addAll(db.historicoDAO().buscarTodos())

    listaMetas.clear()
    listaMetas.addAll(db.metaDAO().buscarTodas())
}
