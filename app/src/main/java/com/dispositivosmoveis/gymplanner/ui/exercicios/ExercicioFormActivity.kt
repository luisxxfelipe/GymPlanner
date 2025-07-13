package com.dispositivosmoveis.gymplanner.ui.exercicios

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dispositivosmoveis.gymplanner.R
import com.dispositivosmoveis.gymplanner.data.AppDatabase
import com.dispositivosmoveis.gymplanner.data.Exercicio
import kotlin.concurrent.thread

class ExercicioFormActivity : AppCompatActivity() {

    private lateinit var etNomeExercicio: EditText
    private lateinit var etRepeticoes: EditText
    private lateinit var etSeries: EditText
    private lateinit var etDescricaoExercicio: EditText
    private lateinit var btnSalvarExercicio: Button
    private var treinoId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercicio_form)

        etNomeExercicio = findViewById(R.id.etNomeExercicio)
        etRepeticoes = findViewById(R.id.etRepeticoes)
        etSeries = findViewById(R.id.etSeries)
        etDescricaoExercicio = findViewById(R.id.etDescricaoExercicio)
        btnSalvarExercicio = findViewById(R.id.btnSalvarExercicio)


        treinoId = intent.getLongExtra("treinoId", -1)
        Log.d("ExercicioFormActivity", "TreinoId recebido: $treinoId")


        btnSalvarExercicio.setOnClickListener {
            salvarExercicio()
        }
    }

    private fun salvarExercicio() {
        val nome = etNomeExercicio.text.toString()
        val repeticoes = etRepeticoes.text.toString().toIntOrNull()
        val descricao = etDescricaoExercicio.text.toString()
        val series = etSeries.text.toString().toIntOrNull()

        Log.d("ExercicioFormActivity", "Nome: $nome" + " Repetições: $repeticoes" + " Descrição: $descricao" + " Series: $series" + " TreinoId: $treinoId")

        if (nome.isBlank() || descricao.isBlank() || repeticoes == null || series == null || treinoId.toInt() == -1) {
            // mostrar log dos dados recebidos

            Toast.makeText(
                this,
                "Por favor, preencha todos os campos corretamente",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val exercicio = Exercicio(
            nome = nome,
            repeticoes = repeticoes,
            descricao = descricao,
            series = series,
            treinoId = treinoId
        )

        thread {
            AppDatabase.getDatabase(this).exercicioDao().insert(exercicio)
            runOnUiThread {
                Toast.makeText(this, "Exercício salvo com sucesso", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

}
