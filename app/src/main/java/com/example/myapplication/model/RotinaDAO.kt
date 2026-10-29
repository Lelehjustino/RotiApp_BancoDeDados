
package com.example.myapplication

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Update
import androidx.room3.Delete
import androidx.room3.Query
import com.example.myapplication.model.Rotina

@Dao
interface RotinaDAO {
    @Query("SELECT * FROM rotinas")
    fun buscarTodas(): List<Rotina>

    @Insert
    fun inserir(rotina: Rotina): Long

    @Update
    fun atualizar(rotina: Rotina)

    @Delete
    fun excluir(rotina: Rotina)
}


