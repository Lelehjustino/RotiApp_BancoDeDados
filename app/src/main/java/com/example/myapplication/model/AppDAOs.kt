
package com.example.myapplication

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Update
import androidx.room3.Delete
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.myapplication.model.Atividade
import com.example.myapplication.model.Historico
import com.example.myapplication.model.Meta
import com.example.myapplication.model.Perfil
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

@Dao
interface PerfilDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salvarPerfil(perfil: Perfil)

    @Query("SELECT * FROM perfil WHERE id = 1 LIMIT 1")
    suspend fun buscarPerfil(): Perfil?
}

@Dao
interface HistoricoDAO {
    @Query("SELECT * FROM historico ORDER BY idHistorico DESC")
    fun buscarTodos(): List<Historico>

    @Insert
    fun inserir(historico: Historico)

    @Delete
    fun excluir(historico: Historico)
}

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


