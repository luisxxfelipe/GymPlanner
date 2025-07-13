package com.dispositivosmoveis.gymplanner.ui.exercicios

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dispositivosmoveis.gymplanner.R
import com.dispositivosmoveis.gymplanner.data.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class ExerciciosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExercicioAdapter
    private var treinoId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercicios)

        recyclerView = findViewById(R.id.recyclerViewExercicios)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Pega o ID do treino que foi passado via intent
        treinoId = intent.getLongExtra("treinoId", -1)
        Log.d("ExercicioAcit", "TreinoId recebido: $treinoId")

        if (treinoId.toInt() != -1) {
            carregarExercicios(treinoId)
        }

        val fabAddExercicio: FloatingActionButton = findViewById(R.id.fabAddExercicio)
        fabAddExercicio.setOnClickListener {
            val intent = Intent(this, ExercicioFormActivity::class.java)
            intent.putExtra("treinoId", treinoId)
            startActivity(intent)
        }
    }

    private fun carregarExercicios(treinoId: Long) {
        val dao = AppDatabase.getDatabase(this).exercicioDao()

        lifecycleScope.launch {
            dao.getExerciciosByTreinoId(treinoId).collect { lista ->
                adapter = ExercicioAdapter(lista)
                recyclerView.adapter = adapter
            }
        }
    }



}
