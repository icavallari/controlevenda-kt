package com.controle.venda.database

import androidx.room.*
import com.controle.venda.model.Venda
import com.controle.venda.model.VendaQuery

@Dao
interface VendaDao {

    @Query("Select count(*) From venda")
    fun qtdVendas() : Long

    @Transaction
    @Query("Select * From venda Where status != 'pago'")
    fun findVendasPendentes(): List<VendaQuery>

    @Query("Select * From venda Where id == :id")
    fun findById(id: Long): Venda

    @Insert
    fun create(venda: Venda): Long

    @Update
    fun update(venda: Venda)

    @Delete
    fun delete(venda: Venda)

    @Query("Delete From venda")
    fun deleteAll()

}