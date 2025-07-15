package com.dispositivosmoveis.gymplanner.ui.treinos

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dispositivosmoveis.gymplanner.R
import com.dispositivosmoveis.gymplanner.database.AppDatabase
import com.dispositivosmoveis.gymplanner.repository.TreinoRepository
import com.dispositivosmoveis.gymplanner.ui.exercicios.ExerciciosActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TreinosActivity: AppCompatActivity() {
    private lateinit var recyclerViewTreinos: RecyclerView
    private lateinit var fabAddTreino: ExtendedFloatingActionButton
    private lateinit var treinoAdapter: TreinoAdapter
    private lateinit var tvTotalTreinos: TextView
    private lateinit var tvTotalExercicios: TextView

    private lateinit var treinoRepository: TreinoRepository

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treinos)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerViewTreinos = findViewById(R.id.recyclerViewTreinos)
        fabAddTreino = findViewById(R.id.fabAddTreino)

        val treinoDao = AppDatabase.getDatabase(this).treinoDao()
        val usuarioId = intent.getLongExtra("usuarioId", -1)

        if (usuarioId == -1L) {
            finish()
            return
        }

        tvTotalTreinos = findViewById(R.id.tvTotalTreinos)
        tvTotalExercicios = findViewById(R.id.tvTotalExercicios)
        treinoRepository = TreinoRepository(treinoDao)

        recyclerViewTreinos.layoutManager = LinearLayoutManager(this)

        // obs em tempo real o treino

        lifecycleScope.launch {
            treinoRepository.buscarTreinosPorUsuario(usuarioId).collect { treinos ->

                tvTotalTreinos.text = treinos.size.toString()

                val exercicioDao = AppDatabase.getDatabase(this@TreinosActivity).exercicioDao()
                val mapaExercicios: MutableMap<Long, Int> = mutableMapOf()
                var totalExercicios = 0

                withContext(Dispatchers.IO) {
                    for (treino in treinos) {
                        val count = exercicioDao.contarExerciciosPorTreino(treino.id.toLong())
                        mapaExercicios[treino.id.toLong()] = count
                        totalExercicios += count
                    }
                }

                tvTotalExercicios.text = totalExercicios.toString()

                treinoAdapter = TreinoAdapter(
                    treinos = treinos,
                    exerciciosPorTreino = mapaExercicios,
                    onItemClick = { treino ->
                        val intent = Intent(this@TreinosActivity, ExerciciosActivity::class.java)
                        intent.putExtra("treinoId", treino.id)
                        startActivity(intent)
                    },
                    onEdit = { treino ->
                        val intent = Intent(this@TreinosActivity, TreinoFormActivity::class.java)
                        intent.putExtra("modoEdicao", true)
                        intent.putExtra("treinoId", treino.id)
                        intent.putExtra("usuarioId", usuarioId)
                        startActivity(intent)
                    },
                    onDelete = { treino ->
                        AlertDialog.Builder(this@TreinosActivity)
                            .setTitle("Excluir treino")
                            .setMessage("Tem certeza que deseja excluir o treino \"${treino.nome}\"?")
                            .setPositiveButton("Sim") { _, _ ->
                                lifecycleScope.launch(Dispatchers.IO) {
                                    treinoRepository.deletarTreino(treino)
                                }
                            }
                            .setNegativeButton("Cancelar", null)
                            .show()
                    }
                )



                recyclerViewTreinos.adapter = treinoAdapter
            }
        }


        fabAddTreino.setOnClickListener {
            val intent = Intent(this, TreinoFormActivity::class.java)
            intent.putExtra("usuarioId", usuarioId)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_treinos, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                mostrarDialogoLogout()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun mostrarDialogoLogout() {
        AlertDialog.Builder(this)
            .setTitle("Sair")
            .setMessage("Deseja realmente sair da sua conta?")
            .setPositiveButton("Sim") { _, _ ->
                val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
                prefs.edit().clear().apply()

                val intent = Intent(
                    this,
                    com.dispositivosmoveis.gymplanner.ui.login.LoginActivity::class.java
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

}