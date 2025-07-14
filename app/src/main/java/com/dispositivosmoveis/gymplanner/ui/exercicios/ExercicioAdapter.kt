package com.dispositivosmoveis.gymplanner.ui.exercicios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dispositivosmoveis.gymplanner.R
import com.dispositivosmoveis.gymplanner.entities.Exercicio

class ExercicioAdapter(
    private val lista: List<Exercicio>
) : RecyclerView.Adapter<ExercicioAdapter.ExercicioViewHolder>() {

    inner class ExercicioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.tvNomeExercicio)
        val descricao: TextView = itemView.findViewById(R.id.tvDescricaoExercicio)
        val series: TextView = itemView.findViewById(R.id.tvSeries)
        val tempo: TextView = itemView.findViewById(R.id.tvTempo)
        val repeticoes: TextView = itemView.findViewById(R.id.tvRepeticoes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercicioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercicio, parent, false)
        return ExercicioViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExercicioViewHolder, position: Int) {
        val exercicio = lista[position]
        holder.nome.text = exercicio.nome
        holder.descricao.text = exercicio.descricao
        holder.tempo.text = "${exercicio.tempo}"
        holder.series.text = "${exercicio.series}"
        holder.repeticoes.text = "${exercicio.repeticoes}"
    }

    override fun getItemCount(): Int = lista.size
}
