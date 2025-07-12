package com.dispositivosmoveis.gymplanner.ui.treinos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dispositivosmoveis.gymplanner.R
import com.dispositivosmoveis.gymplanner.data.AppDatabase
import com.dispositivosmoveis.gymplanner.repository.TreinoRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class TreinosActivity: AppCompatActivity() {
    private lateinit var recyclerViewTreinos: RecyclerView
    private lateinit var fabAddTreino: FloatingActionButton
    private lateinit var treinoAdapter: TreinoAdapter

    private lateinit var treinoRepository: TreinoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treinos)

        recyclerViewTreinos = findViewById(R.id.recyclerViewTreinos)
        fabAddTreino = findViewById(R.id.fabAddTreino)

        val treinoDao = AppDatabase.getDatabase(this).treinoDao()
        treinoRepository = TreinoRepository(treinoDao)

        recyclerViewTreinos.layoutManager = LinearLayoutManager(this)

        // obs em tempo real o treino

        lifecycleScope.launch {
            treinoRepository.listarTreinos.collect { treinos ->
                treinoAdapter = TreinoAdapter(treinos)
                recyclerViewTreinos.adapter = treinoAdapter
            }

        }

        val fabAddTreino: FloatingActionButton = findViewById(R.id.fabAddTreino)
        fabAddTreino.setOnClickListener {
            val intent = Intent(this, TreinoFormActivity::class.java)
            startActivity(intent)
        }
    }
}