
package com.example.myapplication

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.example.myapplication.ui.theme.*

@Database(
    entities = [
        Rotina::class,
        Atividade::class,
        Perfil::class,
        Historico::class,
        Meta::class
    ],
    version = 2,
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
