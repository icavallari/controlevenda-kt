package com.controle.venda

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class VendaAdapter(val vendas: List<Venda>) : RecyclerView.Adapter<VendaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return VendaViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: VendaViewHolder, position: Int) {
        val venda: Venda = vendas[position]
        holder.bind(venda)
    }

    override fun getItemCount(): Int = vendas.size

}