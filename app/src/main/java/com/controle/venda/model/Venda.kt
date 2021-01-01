package com.controle.venda.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Venda(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var comprador: String?,
    var previsaoPagamento: Date?,
    var dataVenda: Date?,
    var status: String?
) {

    constructor()

    constructor() : this(0, "",null, Date(), "Aguardando Pagamento")
}