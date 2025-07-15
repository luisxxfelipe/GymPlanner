package com.dispositivosmoveis.gymplanner.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dispositivosmoveis.gymplanner.entities.Treino
import kotlinx.coroutines.flow.Flow


@Dao
interface TreinoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(treino: Treino) : Long

    @Update
    suspend fun update(treino: Treino)

    @Delete
    suspend fun delete(treino: Treino)

    @Query("SELECT * FROM treinos WHERE usuarioId = :usuarioId")
    fun getAllTreinos(usuarioId: Long): Flow<List<Treino>>

    @Query("SELECT * FROM treinos WHERE id = :treinoId")
    suspend fun getTreinoById(treinoId: Long): Treino?

}