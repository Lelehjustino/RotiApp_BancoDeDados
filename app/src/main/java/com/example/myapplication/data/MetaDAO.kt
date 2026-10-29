package com.example.myapplication.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.example.myapplication.model.Meta

@Dao
interface MetaDAO {
    @Query("SELECT * FROM metas")
    fun buscarTodas(): List<Meta>

    @Insert
    fun inserir(meta: Meta)

    @Update
    fun atualizar(meta: Meta)

    @Delete
    fun excluir(meta: Meta)
}