package com.controle.venda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class VendaViewHolder(var inflater: LayoutInflater, var parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.venda_list_item, parent, false)) {

    private var txtPrevisaoPagamento: AppCompatTextView? = null
    private var txtComprador: AppCompatTextView? = null
    private var txtStatus: AppCompatTextView? = null

    init {

        txtPrevisaoPagamento = itemView.findViewById(R.id.item_venda_previsaopagamento)
        txtComprador = itemView.findViewById(R.id.item_venda_comprador)
        txtStatus = itemView.findViewById(R.id.item_venda_status)

    }

    fun bind(venda: Venda) {

        var produtos: Array<Produto> = venda.produtos

        var container = itemView.findViewById<LinearLayout>(R.id.item_venda_container_produtos)
        for (produto in produtos) {

            var valorRevenda: String = formatarMoeda(produto.valorRevenda)
            var codigo: String = formatarString(produto.codigo) + " - " + produto.tipo

            var vProduto: View = inflater.inflate(R.layout.produto_list_item, parent, false)
            vProduto.findViewById<AppCompatTextView>(R.id.item_produto_valorrevenda).text =
                valorRevenda
            vProduto.findViewById<AppCompatTextView>(R.id.item_produto_tipo).text = codigo
            container.addView(vProduto)

        }

        txtPrevisaoPagamento?.text = formatarData(venda.previsaoPagamento)
        txtComprador?.text = venda.comprador
        txtStatus?.text = venda.status

    }

    private fun formatarMoeda(valor: Double): String {
        return "R$ " + DecimalFormat("#,###.00").format(valor)
    }

    private fun formatarData(valor: Date?): String {
        if (valor == null) return "--/--/----"
        return SimpleDateFormat("dd/MM/yyyy").format(valor)
    }

    private fun formatarString(valor: String?): String {
        if (valor == null) return "00000000"
        return valor?.padStart(8, '0')
    }


}