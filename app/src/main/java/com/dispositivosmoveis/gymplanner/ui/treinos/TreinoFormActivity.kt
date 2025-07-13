package com.dispositivosmoveis.gymplanner.ui.treinos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dispositivosmoveis.gymplanner.R
import com.dispositivosmoveis.gymplanner.database.AppDatabase
import com.dispositivosmoveis.gymplanner.entities.Treino
import com.dispositivosmoveis.gymplanner.repository.TreinoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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


        val treino = Treino(nome = nome, objetivo = objetivo)
        val treinoRepository = TreinoRepository(AppDatabase.getDatabase(this).treinoDao())

        lifecycleScope.launch(Dispatchers.IO) {
            treinoRepository.inserirTreino(treino)

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@TreinoFormActivity,
                    "Treino salvo com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }

    }
}