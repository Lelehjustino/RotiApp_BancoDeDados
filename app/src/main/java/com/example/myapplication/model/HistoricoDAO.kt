package com.example.myapplication.model

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query

@Dao
interface HistoricoDAO {
    @Query("SELECT * FROM historico ORDER BY idHistorico DESC")
    fun buscarTodos(): List<Historico>

    @Insert
    fun inserir(historico: Historico)

    @Delete
    fun excluir(historico: Historico)
}