package com.dispositivosmoveis.gymplanner.ui.treinos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dispositivosmoveis.gymplanner.R
import com.dispositivosmoveis.gymplanner.data.AppDatabase
import com.dispositivosmoveis.gymplanner.data.Treino
import com.dispositivosmoveis.gymplanner.ui.exercicios.ExerciciosActivity
import kotlin.concurrent.thread

class TreinoFormActivity: AppCompatActivity() {
    private lateinit var etNomeTreino: EditText
    private lateinit var etObjetivoTreino: EditText
    private lateinit var btnSalvarTreino: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treino_form)

        etNomeTreino = findViewById(R.id.etNomeTreino)
        etObjetivoTreino = findViewById(R.id.etObjetivoTreino)
        btnSalvarTreino = findViewById(R.id.btnSalvarTreino)

        btnSalvarTreino.setOnClickListener {
            salvarTreino()
        }
    }

    private fun salvarTreino() {
        val nome = etNomeTreino.text.toString()
        val objetivo = etObjetivoTreino.text.toString().trim()

        if (nome.isEmpty() || objetivo.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()

            if (nome.length < 3) {
                Toast.makeText(
                    this, "O nome do treino deve ter no mínimo 3 caracteres", Toast.LENGTH_SHORT
                ).show()
                return
            }
        }


        val treino = Treino(nome = nome, objetivos = objetivo)

        thread {
            val context = applicationContext
            val treinoId = AppDatabase.getDatabase(context).treinoDao().insert(treino)
            runOnUiThread {
                Toast.makeText(this, "Treino salvo com sucesso", Toast.LENGTH_SHORT).show()
                Log.d("TreinoFormActivity", "ID do treino inserido: $treinoId")
                val intent = Intent(this, ExerciciosActivity::class.java)
                intent.putExtra("treinoId", treinoId)
                startActivity(intent)
                finish()
            }
        }
    }
}