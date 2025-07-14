package com.dispositivosmoveis.gymplanner.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dispositivosmoveis.gymplanner.entities.Exercicio
import kotlinx.coroutines.flow.Flow

@Dao
interface ExercicioDao{
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert(exercicio: Exercicio)

    @Update
    suspend fun update(exercicio: Exercicio)

    @Delete
    suspend fun delete(exercicio: Exercicio)

    @Query ("SELECT * FROM exercicios WHERE treinoId = :treinoId")
    fun getExerciciosByTreinoId(treinoId: Long): Flow <List<Exercicio>>

    @Query("SELECT COUNT(*) FROM exercicios WHERE treinoId = :treinoId")
    fun contarExerciciosPorTreino(treinoId: Long): Int

}