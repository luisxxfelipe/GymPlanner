package com.dispositivosmoveis.gymplanner.ui.exercicios

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dispositivosmoveis.gymplanner.R
import com.dispositivosmoveis.gymplanner.database.AppDatabase
import com.dispositivosmoveis.gymplanner.entities.Exercicio
import com.dispositivosmoveis.gymplanner.repository.ExercicioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExercicioFormActivity : AppCompatActivity() {

    private lateinit var etNomeExercicio: EditText
    private lateinit var etRepeticoes: EditText
    private lateinit var etSeries: EditText
    private lateinit var etDescricaoExercicio: EditText
    private lateinit var etTempo: EditText
    private lateinit var btnSalvarExercicio: Button

    private var treinoId: Long = -1
    private var exercicioId: Int = -1
    private var modoEdicao = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercicio_form)

        etNomeExercicio = findViewById(R.id.etNomeExercicio)
        etRepeticoes = findViewById(R.id.etRepeticoes)
        etSeries = findViewById(R.id.etSeries)
        etDescricaoExercicio = findViewById(R.id.etDescricaoExercicio)
        etTempo = findViewById(R.id.etTempo)
        btnSalvarExercicio = findViewById(R.id.btnSalvarExercicio)

        treinoId = intent.getLongExtra("treinoId", -1)
        exercicioId = intent.getIntExtra("exercicioId", -1)
        modoEdicao = intent.getBooleanExtra("modoEdicao", false)

        if (modoEdicao && exercicioId != -1) {
            carregarDadosDoExercicio()
        }

        btnSalvarExercicio.setOnClickListener {
            if (modoEdicao) {
                editarExercicio()
            } else {
                salvarExercicio()
            }
        }
    }

    private fun carregarDadosDoExercicio() {
        lifecycleScope.launch {
            val dao = AppDatabase.getDatabase(this@ExercicioFormActivity).exercicioDao()
            val exercicios = dao.getExerciciosByTreinoId(treinoId).first()
            val exercicio = exercicios.find { it.id == exercicioId }

            exercicio?.let {
                etNomeExercicio.setText(it.nome)
                etDescricaoExercicio.setText(it.descricao)
                etSeries.setText(it.series.toString())
                etRepeticoes.setText(it.repeticoes.toString())
                etTempo.setText(it.tempo)
            }
        }
    }

    private fun editarExercicio() {
        val nome = etNomeExercicio.text.toString()
        val repeticoes = etRepeticoes.text.toString().toIntOrNull()
        val descricao = etDescricaoExercicio.text.toString()
        val series = etSeries.text.toString().toIntOrNull()
        val tempo = etTempo.text.toString()

        if (nome.isBlank() || descricao.isBlank() || repeticoes == null || series == null) {
            Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
            return
        }

        val exercicio = Exercicio(
            id = exercicioId,
            nome = nome,
            repeticoes = repeticoes,
            descricao = descricao,
            series = series,
            tempo = tempo,
            treinoId = treinoId
        )

        val repository = ExercicioRepository(AppDatabase.getDatabase(this).exercicioDao())

        lifecycleScope.launch(Dispatchers.IO) {
            repository.atualizarExercicio(exercicio)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@ExercicioFormActivity, "Exercício editado!", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }

    private fun salvarExercicio() {
        val nome = etNomeExercicio.text.toString()
        val repeticoes = etRepeticoes.text.toString().toIntOrNull()
        val descricao = etDescricaoExercicio.text.toString()
        val series = etSeries.text.toString().toIntOrNull()
        val tempo = etTempo.text.toString()

        if (nome.isBlank() || descricao.isBlank() || repeticoes == null || series == null || treinoId == -1L) {
            Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
            return
        }

        val exercicio = Exercicio(
            nome = nome,
            repeticoes = repeticoes,
            descricao = descricao,
            series = series,
            tempo = tempo,
            treinoId = treinoId
        )

        val repository = ExercicioRepository(AppDatabase.getDatabase(this).exercicioDao())

        lifecycleScope.launch(Dispatchers.IO) {
            repository.inserirExercicio(exercicio)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@ExercicioFormActivity, "Exercício salvo!", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }
}
