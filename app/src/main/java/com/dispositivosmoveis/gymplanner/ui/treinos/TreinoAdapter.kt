package com.dispositivosmoveis.gymplanner.ui.treinos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dispositivosmoveis.gymplanner.R
import com.dispositivosmoveis.gymplanner.entities.Treino

class TreinoAdapter(
    private val treinos: List<Treino>,
    private val exerciciosPorTreino: Map<Long, Int>,
    private val onItemClick: (Treino) -> Unit,
    private val onEdit: (Treino) -> Unit,
    private val onDelete: (Treino) -> Unit
) : RecyclerView.Adapter<TreinoAdapter.TreinoViewHolder>() {

    inner class TreinoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNome: TextView = itemView.findViewById(R.id.tvNomeTreino)
        val tvObjetivo: TextView = itemView.findViewById(R.id.tvObjetivoTreino)
        val tvExercicios: TextView = itemView.findViewById(R.id.tvExerciciosCount)
        val btnMore: View = itemView.findViewById(R.id.btnMore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreinoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_treino, parent, false)
        return TreinoViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TreinoViewHolder, position: Int) {
        val treino = treinos[position]
        holder.tvNome.text = treino.nome
        holder.tvObjetivo.text = treino.objetivo

        // Exibir número de exercícios
        val totalEx = exerciciosPorTreino[treino.id.toLong()] ?: 0
        holder.tvExercicios.text = "$totalEx exercícios"

        holder.itemView.setOnClickListener {
            onItemClick(treino)
        }

        holder.btnMore.setOnClickListener { v ->
            val popup = PopupMenu(v.context, holder.btnMore)
            popup.inflate(R.menu.menu_exercicio_item)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_editar -> {
                        onEdit(treino)
                        true
                    }

                    R.id.action_excluir -> {
                        onDelete(treino)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }


    override fun getItemCount(): Int = treinos.size
}
