
package com.example.myapplication

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.example.myapplication.model.Atividade
import com.example.myapplication.model.AtividadeDAO
import com.example.myapplication.model.Historico
import com.example.myapplication.model.HistoricoDAO
import com.example.myapplication.model.Meta
import com.example.myapplication.model.MetaDAO
import com.example.myapplication.model.Perfil
import com.example.myapplication.model.PerfilDAO
import com.example.myapplication.model.Rotina

@Database(
    entities = [
        Rotina::class,
        Atividade::class,
        Perfil::class,
        Historico::class,
        Meta::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun rotinaDAO(): RotinaDAO
    abstract fun atividadeDAO(): AtividadeDAO
    abstract fun perfilDAO(): PerfilDAO
    abstract fun historicoDAO(): HistoricoDAO
    abstract fun metaDAO(): MetaDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "rotiapp_database"
                )
                    .allowMainThreadQueries()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}

object BancoDadosProvider {
    lateinit var db: AppDataBase
        private set

    fun inicializar(context: Context) {
        db = AppDataBase.getDatabase(context)
    }
}
