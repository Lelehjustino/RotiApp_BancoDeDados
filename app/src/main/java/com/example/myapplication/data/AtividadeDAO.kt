package com.example.myapplication.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.example.myapplication.model.Atividade

@Dao
interface AtividadeDAO {
    @Query("SELECT * FROM atividades")
    fun buscarTodas(): List<Atividade>

    @Query("SELECT * FROM atividades WHERE idRotina = :idRotina")
    fun buscarPorRotina(idRotina: Int): List<Atividade>

    @Insert
    fun inserir(atividade: Atividade)

    @Update
    fun atualizar(atividade: Atividade)

    @Delete
    fun excluir(atividade: Atividade)

    @Query("DELETE FROM atividades WHERE idRotina = :idRotina")
    fun excluirPorRotina(idRotina: Int)
}