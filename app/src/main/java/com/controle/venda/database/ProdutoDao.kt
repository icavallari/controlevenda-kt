package com.controle.venda.database

import androidx.room.*
import com.controle.venda.model.Produto

@Dao
interface ProdutoDao {

    @Query("Select count(*) From produto")
    fun qtdProdutos(): Long

    @Query("SELECT * FROM produto Where vendaId == :id")
    fun findByVendaId(id: Long): List<Produto>

    @Query("Select * From produto Where id == :id")
    fun findById(id: Long): Produto

    @Insert
    fun create(produto: Produto)

    @Update
    fun update(produto: Produto)

    @Delete
    fun delete(produto: Produto)

}