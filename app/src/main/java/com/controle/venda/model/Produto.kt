package com.controle.venda.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Venda::class,
        parentColumns = ["id"], childColumns = ["vendaId"]
    )]
)
data class Produto(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var tipo: String,
    var codigo: String,
    @ColumnInfo(index = true)
    var vendaId: Long,
    var valorRevenda: Double
) {

    constructor() : this(0, "", "", 0, 0.0)

}