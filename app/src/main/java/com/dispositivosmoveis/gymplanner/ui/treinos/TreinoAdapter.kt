package com.dispositivosmoveis.gymplanner.ui.treinos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dispositivosmoveis.gymplanner.R
import com.dispositivosmoveis.gymplanner.data.Treino

class TreinoAdapter (
    private val treinos: List<Treino>
) : RecyclerView.Adapter<TreinoAdapter.TreinoViewHolder>() {
    inner class TreinoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNomeTreino: TextView = itemView.findViewById(R.id.tvNomeTreino)
        val tvObjetivoTreino: TextView = itemView.findViewById(R.id.tvObjetivoTreino)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreinoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_treino, parent, false)
        return TreinoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TreinoViewHolder, position: Int) {
        val treino = treinos[position]
        holder.tvNomeTreino.text = treino.nome
        holder.tvObjetivoTreino.text = treino.objetivos
    }

    override fun getItemCount(): Int = treinos.size
}

