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

class TreinoFormActivity : AppCompatActivity() {

    private lateinit var etNomeTreino: EditText
    private lateinit var etObjetivoTreino: EditText
    private lateinit var btnSalvarTreino: Button

    private var usuarioId: Long = -1
    private var treinoId: Long = -1
    private var modoEdicao = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treino_form)

        etNomeTreino = findViewById(R.id.etNomeTreino)
        etObjetivoTreino = findViewById(R.id.etObjetivoTreino)
        btnSalvarTreino = findViewById(R.id.btnSalvarTreino)

        usuarioId = intent.getLongExtra("usuarioId", -1)
        treinoId = intent.getLongExtra("treinoId", -1)
        modoEdicao = intent.getBooleanExtra("modoEdicao", false)

        if (usuarioId == -1L) {
            finish()
            return
        }

        if (modoEdicao && treinoId.toInt() != -1) {
            carregarTreino()
        }

        btnSalvarTreino.setOnClickListener {
            if (modoEdicao) {
                editarTreino()
            } else {
                salvarTreino()
            }
        }
    }

    private fun carregarTreino() {
        val dao = AppDatabase.getDatabase(this).treinoDao()
        val repository = TreinoRepository(dao)

        lifecycleScope.launch {
            val treino = repository.buscarTreinoPorId(treinoId)
            treino?.let {
                etNomeTreino.setText(it.nome)
                etObjetivoTreino.setText(it.objetivo)
            }
        }
    }

    private fun salvarTreino() {
        val nome = etNomeTreino.text.toString()
        val objetivo = etObjetivoTreino.text.toString()

        if (nome.isBlank() || objetivo.isBlank()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        val treino = Treino(
            nome = nome,
            objetivo = objetivo,
            usuarioId = usuarioId
        )

        val repository = TreinoRepository(AppDatabase.getDatabase(this).treinoDao())

        lifecycleScope.launch(Dispatchers.IO) {
            repository.inserirTreino(treino)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@TreinoFormActivity, "Treino salvo!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun editarTreino() {
        val nome = etNomeTreino.text.toString()
        val objetivo = etObjetivoTreino.text.toString()

        if (nome.isBlank() || objetivo.isBlank()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        val treino = Treino(
            id = treinoId,
            nome = nome,
            objetivo = objetivo,
            usuarioId = usuarioId
        )

        val repository = TreinoRepository(AppDatabase.getDatabase(this).treinoDao())

        lifecycleScope.launch(Dispatchers.IO) {
            repository.atualizarTreino(treino)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@TreinoFormActivity, "Treino atualizado!", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }
}
