package com.dispositivosmoveis.gymplanner.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dispositivosmoveis.gymplanner.entities.Exercicio
import com.dispositivosmoveis.gymplanner.dao.ExercicioDao
import com.dispositivosmoveis.gymplanner.entities.Treino
import com.dispositivosmoveis.gymplanner.dao.TreinoDao
import com.dispositivosmoveis.gymplanner.dao.UserDao
import com.dispositivosmoveis.gymplanner.entities.User

@Database(entities = [Treino::class, Exercicio::class, User::class], version = 7, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun treinoDao(): TreinoDao
    abstract fun exercicioDao(): ExercicioDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gymplanner_database"
                )
                    .fallbackToDestructiveMigration(false).build()
                INSTANCE = instance
                instance
            }
        }
    }
}