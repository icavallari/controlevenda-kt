package com.controle.venda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.controle.venda.model.Produto
import com.controle.venda.util.Util
import java.text.DecimalFormat

class ProdutoAdapter(var produtos: List<Produto>) : RecyclerView.Adapter<ProdutoAdapter.ViewHolder>() {

    lateinit var clickListener: (produto: Produto) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto: Produto = produtos[position]
        holder.bind(produto)

        holder.getView().setOnClickListener {
            clickListener.invoke(produto)
        }

        /*
        holder.getView().setOnLongClickListener {

        }
        */
    }

    fun setItemClickListener(clickListener: (produto: Produto) -> Unit) {
        this.clickListener = clickListener
    }

    override fun getItemCount(): Int = produtos.size

    class ViewHolder(var inflater: LayoutInflater, var parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.venda_produto_list_item, parent, false)) {

        private var txtValorRevenda: AppCompatTextView? = null
        private var txtTipo: AppCompatTextView? = null

        init {

            txtValorRevenda = itemView.findViewById(R.id.venda_produto_valorrevenda)
            txtTipo = itemView.findViewById(R.id.venda_produto_tipo)

        }

        fun bind(produto: Produto) {

            txtTipo?.text = Util.formatarString(produto.codigo) + " - " + produto.tipo
            txtValorRevenda?.text = Util.formatarMoeda(produto.valorRevenda)

        }

        fun getView(): View {
            return itemView
        }

    }

}