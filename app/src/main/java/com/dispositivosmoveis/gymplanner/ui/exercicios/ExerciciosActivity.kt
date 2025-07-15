package com.dispositivosmoveis.gymplanner.ui.exercicios

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dispositivosmoveis.gymplanner.R
import com.dispositivosmoveis.gymplanner.database.AppDatabase
import com.dispositivosmoveis.gymplanner.repository.ExercicioRepository
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciciosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExercicioAdapter
    private lateinit var tvExerciciosCount: TextView
    private var treinoId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercicios)

        recyclerView = findViewById(R.id.recyclerViewExercicios)
        recyclerView.layoutManager = LinearLayoutManager(this)
        tvExerciciosCount = findViewById(R.id.tvExerciciosCount)

        // Pega o ID do treino que foi passado via intent
        treinoId = intent.getLongExtra("treinoId", -1)

        if (treinoId.toInt() != -1) {
            carregarExercicios(treinoId)
        }

        val fabAddExercicio: ExtendedFloatingActionButton = findViewById(R.id.fabAddExercicio)
        fabAddExercicio.setOnClickListener {
            val intent = Intent(this, ExercicioFormActivity::class.java)
            intent.putExtra("treinoId", treinoId)
            startActivity(intent)
        }
    }

    @SuppressLint("DefaultLocale")
    private fun carregarExercicios(treinoId: Long) {
        val exercicioRepository = ExercicioRepository(AppDatabase.getDatabase(this).exercicioDao())

        lifecycleScope.launch {
            exercicioRepository.listarExerciciosPorTreino(treinoId).collect { exercicios ->
                tvExerciciosCount.text = String.format("%d", exercicios.size)
                adapter = ExercicioAdapter(
                    exercicios,
                    onEditar = { exercicio ->
                        val intent =
                            Intent(this@ExerciciosActivity, ExercicioFormActivity::class.java)
                        intent.putExtra("modoEdicao", true)
                        intent.putExtra("exercicioId", exercicio.id)
                        intent.putExtra("treinoId", treinoId)
                        startActivity(intent)
                    },
                    onExcluir = { exercicio ->
                        AlertDialog.Builder(this@ExerciciosActivity)
                            .setTitle("Excluir exercício")
                            .setMessage("Tem certeza que deseja excluir este exercício?")
                            .setPositiveButton("Sim") { _, _ ->
                                lifecycleScope.launch(Dispatchers.IO) {
                                    val dao = AppDatabase.getDatabase(this@ExerciciosActivity)
                                        .exercicioDao()
                                    dao.delete(exercicio)
                                }
                            }
                            .setNegativeButton("Cancelar", null)
                            .show()
                    }
                )

                recyclerView.adapter = adapter
            }
        }
    }



}
