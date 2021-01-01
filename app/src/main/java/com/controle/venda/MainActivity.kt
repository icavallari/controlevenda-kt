package com.controle.venda

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import com.controle.venda.database.AppDatabase
import com.controle.venda.model.Produto
import com.controle.venda.model.Venda
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    lateinit var database: AppDatabase

    var adapter = VendaAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupDatabase()

        setupRecycler()

        inicializarDb(database)

        btn_add_venda_act.setOnClickListener {

            val intent = Intent(this, VendaActivity::class.java)
            startActivity(intent)

        }

    }

    override fun onResume() {
        super.onResume()

        doAsync {
            var vendas = database.vendaDao().findVendasPendentes()

            var qtdVendas = database.vendaDao().qtdVendas()

            var qtdProdutos = database.produtoDao().qtdProdutos()

            uiThread {
                adapter.vendas = vendas
                adapter.notifyDataSetChanged()

                txt_itens_vendidos_actmain.setText(qtdProdutos.toString())
                txt_qtd_vendas_actmain.setText(qtdVendas.toString())
            }

        }
    }

    fun setupRecycler() {
        recycler_view_act_main.apply {
            layoutManager = LinearLayoutManager(context)
        }
        recycler_view_act_main.adapter = adapter

        adapter.setItemClickListener { vendaId ->

            val intent = Intent(this, VendaActivity::class.java)
            intent.putExtra(VendaActivity.EXTRA_VENDA_ID, vendaId)
            startActivity(intent)

        }
    }

    private fun setupDatabase() {
        database = AppDatabase.getInstance(this)
    }

    private fun inicializarDb(database: AppDatabase) {

        doAsync {
            database.vendaDao()?.deleteAll()

            var vendaId: Long = 0
            var statusInicial = "Aguardando Pagamento"

            var sdf = SimpleDateFormat("dd/MM/yyyy")

            vendaId = database.vendaDao()?.create(Venda(0, "Gaby", sdf.parse("14/11/2020"), statusInicial))
            database.produtoDao()?.create(Produto(0, "Anel", "MJ6456", vendaId, 24.00))

            vendaId = database.vendaDao()?.create(Venda(0, "Vani", sdf.parse("15/11/2020"), statusInicial))
            database.produtoDao()?.create(Produto(0, "Brinco", "MJ20396", vendaId, 11.90))
            database.produtoDao()?.create(Produto(0, "Brinco", "MJ20380", vendaId, 11.90))
            database.produtoDao()?.create(Produto(0, "Brinco", "MJ20412", vendaId, 11.90))

            vendaId = database.vendaDao()?.create(Venda(0, "Ivone", sdf.parse("15/11/2020"), statusInicial))
            database.produtoDao()?.create(Produto(0, "Brinco", "MJ11712", vendaId, 11.90))
            database.produtoDao()?.create(Produto(0, "Pulseira", "MJ12807", vendaId, 15.90))
            database.produtoDao()?.create(Produto(0, "Brinco", "MJ21349", vendaId, 34.90))

            vendaId = database.vendaDao()?.create(Venda(0, "Ivone", sdf.parse("19/11/2020"), statusInicial))
            database.produtoDao()?.create(Produto(0, "Brinco", "MJ16132", vendaId, 19.90))

            vendaId = database.vendaDao()?.create(Venda(0, "Tia Geralda", sdf.parse("19/11/2020"), statusInicial))
            database.produtoDao()?.create(Produto(0, "Tornozeleira", "MJ12645", vendaId, 19.90))

            vendaId = database.vendaDao()?.create(Venda(0, "Amanda Agata", sdf.parse("15/11/2020"), "Pago"))
            database.produtoDao()?.create(Produto(0, "Pulseira", "MJ12023", vendaId, 24.90))

            vendaId = database.vendaDao()?.create(Venda(0, "Rafa Aprendiz", sdf.parse("15/11/2020"), statusInicial))
            database.produtoDao()?.create(Produto(0, "Brinco", "MJ14503", vendaId, 13.00))

            vendaId = database.vendaDao()?.create(Venda(0, "Adriana", sdf.parse("19/11/2020"), statusInicial))
            database.produtoDao()?.create(Produto(0, "Pulseira", "MJ10381", vendaId, 37.00))
            database.produtoDao()?.create(Produto(0, "Gargantilha", "MJ10097", vendaId, 44.90))
            database.produtoDao()?.create(Produto(0, "Gargantilha", "MJ18086", vendaId, 49.90))
        }

    }

}