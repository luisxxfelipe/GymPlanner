package com.dispositivosmoveis.gymplanner.ui.treinos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dispositivosmoveis.gymplanner.R
import com.dispositivosmoveis.gymplanner.entities.Treino

class TreinoAdapter(
    private val treinos: List<Treino>,
    private val onItemClick: (Treino) -> Unit
) : RecyclerView.Adapter<TreinoAdapter.TreinoViewHolder>() {

    inner class TreinoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNome: TextView = itemView.findViewById(R.id.tvNomeTreino)
        val tvObjetivo: TextView = itemView.findViewById(R.id.tvObjetivoTreino)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreinoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_treino, parent, false)
        return TreinoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TreinoViewHolder, position: Int) {
        val treino = treinos[position]
        holder.tvNome.text = treino.nome
        holder.tvObjetivo.text = treino.objetivo

        holder.itemView.setOnClickListener {
            onItemClick(treino)
        }
    }

    override fun getItemCount(): Int = treinos.size
}
