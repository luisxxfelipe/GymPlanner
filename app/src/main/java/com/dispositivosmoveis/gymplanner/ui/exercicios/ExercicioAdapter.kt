package com.dispositivosmoveis.gymplanner.ui.exercicios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dispositivosmoveis.gymplanner.R
import com.dispositivosmoveis.gymplanner.entities.Exercicio

class ExercicioAdapter(
    private val lista: List<Exercicio>,
    private val onEditar : (Exercicio) -> Unit,
    private val onExcluir : (Exercicio) -> Unit
) : RecyclerView.Adapter<ExercicioAdapter.ExercicioViewHolder>() {

    inner class ExercicioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.tvNomeExercicio)
        val descricao: TextView = itemView.findViewById(R.id.tvDescricaoExercicio)
        val series: TextView = itemView.findViewById(R.id.tvSeries)
        val tempo: TextView = itemView.findViewById(R.id.tvTempo)
        val repeticoes: TextView = itemView.findViewById(R.id.tvRepeticoes)
        val btnMore: View = itemView.findViewById(R.id.btnMore)
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

        holder.btnMore.setOnClickListener { v ->
            val popup = PopupMenu(v.context, holder.btnMore)
            popup.inflate(R.menu.menu_exercicio_item)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_editar -> {
                        onEditar(exercicio)
                        true
                    }
                    R.id.action_excluir -> {
                        onExcluir(exercicio)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    override fun getItemCount(): Int = lista.size
}
