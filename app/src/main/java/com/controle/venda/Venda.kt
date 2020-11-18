package com.controle.venda

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Venda(var comprador: String?, var produtos: Array<Produto>) {

    var dataVenda: Date = Date()
    var previsaoPagamento: Date? = null
    var status: String = "Aguardando Pagamento"

}