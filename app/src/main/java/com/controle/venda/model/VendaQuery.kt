package com.controle.venda.model

import androidx.room.Embedded
import androidx.room.Relation

data class VendaQuery(

    @Embedded
    var venda: Venda? = null,

    @Relation(
        parentColumn = "id",
        entityColumn = "vendaId"
    )
    var produtos: List<Produto>? = null

)