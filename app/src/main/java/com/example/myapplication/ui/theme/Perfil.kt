
package com.example.myapplication.ui.theme

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "perfil")
data class Perfil(
    val nome: String = "",
    val email: String = "",
    @PrimaryKey val id: Int = 1
)
