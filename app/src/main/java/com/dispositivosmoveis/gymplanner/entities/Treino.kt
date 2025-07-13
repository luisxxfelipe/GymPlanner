package com.dispositivosmoveis.gymplanner.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "treinos")
class Treino (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val objetivo: String
)
