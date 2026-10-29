package com.example.myapplication.data

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.myapplication.model.Perfil

@Dao
interface PerfilDAO {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun salvarPerfil(perfil: Perfil)

    @Query("SELECT * FROM perfil WHERE id = 1 LIMIT 1")
    suspend fun buscarPerfil(): Perfil?
}