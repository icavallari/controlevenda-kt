package com.controle.venda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.controle.venda.model.VendaQuery
import com.controle.venda.util.Util

class VendaAdapter(var vendas: List<VendaQuery>) : RecyclerView.Adapter<VendaAdapter.ViewHolder>() {

    lateinit var clickListener: (vendaId: Long) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    fun setItemClickListener(listener: (vendaId: Long) -> Unit) {
        this.clickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vendaQ: VendaQuery = vendas[position]
        holder.bind(vendaQ)

        holder.getView().setOnClickListener {
            clickListener.invoke(vendaQ.venda?.id!!)
        }
    }

    override fun getItemCount(): Int = vendas.size

    class ViewHolder(var inflater: LayoutInflater, var parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.venda_list_item, parent, false)) {

        private var txtDataVenda: AppCompatTextView? = null
        private var txtComprador: AppCompatTextView? = null
        private var txtStatus: AppCompatTextView? = null

        init {

            txtDataVenda = itemView.findViewById(R.id.item_venda_datavenda)
            txtComprador = itemView.findViewById(R.id.item_venda_comprador)
            txtStatus = itemView.findViewById(R.id.item_venda_status)

        }

        fun bind(vendaQ: VendaQuery) {

            var produtos = vendaQ.produtos
            var container = itemView.findViewById<LinearLayout>(R.id.item_venda_container_produtos)

            for (produto in produtos as List) {

                var valorRevenda: String = Util.formatarMoeda(produto.valorRevenda)
                var codigo: String = Util.formatarString(produto.codigo) + " - " + produto.tipo

                var vProduto: View = inflater.inflate(R.layout.main_produto_list_item, parent, false)
                vProduto.findViewById<AppCompatTextView>(R.id.main_produto_valorrevenda).text =
                    valorRevenda
                vProduto.findViewById<AppCompatTextView>(R.id.main_produto_tipo).text = codigo
                container.addView(vProduto)

            }

            txtDataVenda?.text = Util.formatarData(vendaQ.venda?.dataVenda)
            txtComprador?.text = vendaQ.venda?.comprador
            txtStatus?.text = vendaQ.venda?.status

        }

        fun getView(): View {
            return itemView
        }

    }

}