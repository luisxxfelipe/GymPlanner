package com.dispositivosmoveis.gymplanner.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface TreinoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(treino: Treino)

    @Update
    suspend fun update(treino: Treino)

    @Delete
    suspend fun delete(treino: Treino)

    @Query("SELECT * FROM treinos")
    fun getAllTreinos(): Flow<List<Treino>>

}