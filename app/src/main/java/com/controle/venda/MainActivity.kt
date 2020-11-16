package com.controle.venda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var listaProdutos = arrayListOf(

        Produto(null, "Choker")
        , Produto("Q15641", "Colar")
        , Produto("Q15641", "Anel")
        , Produto("Q15641", "Brinco")
        , Produto("Q15641", "Tornozeleira")
        , Produto("Q15641", "Pulseira")
        , Produto("Q15641", "Gargantilha")
        , Produto("Q15641", "Anel")
        , Produto("Q15641", "Choker")

        , Produto("Q15641", "Colar")
        , Produto("Q15641", "Anel")
        , Produto("Q15641", "Brinco")
        , Produto("Q15641", "Tornozeleira")
        , Produto("Q15641", "Pulseira")
        , Produto("Q15641", "Gargantilha")
        , Produto("Q15641", "Anel")
        , Produto("Q15641", "Choker")

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var auth = Firebase.auth

        auth.signInWithEmailAndPassword("drigocavallari@gmail.com", "15$4684A0nAB")
            .addOnCompleteListener(this) {
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                connectDb()

                initRecycler()
            }
            .addOnFailureListener(this) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }

    }

    fun initRecycler() {

        recycler_view_act_main.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ListAdapter(listaProdutos)
        }

    }

    fun connectDb() {

        val database = Firebase.database
        database.setPersistenceEnabled(true)

        val produtos = database.getReference("_produtos_")
        produtos.setValue(listaProdutos)

        //produtos.push()
    }

    class ListAdapter(private val list: List<Produto>) :
        RecyclerView.Adapter<ListAdapter.ProdutoViewHolder>() {

        class ProdutoViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.produto_list_item, parent, false)) {

            private var txtTipo: AppCompatTextView? = null
            private var txtValorRevenda: AppCompatTextView? = null

            private var txtComprador: AppCompatTextView? = null
            private var txtDataPagmento: AppCompatTextView? = null

            private var txtStatus: AppCompatTextView? = null

            init {
                txtTipo = itemView.findViewById(R.id.item_produto_tipo)
                txtValorRevenda = itemView.findViewById(R.id.item_produto_valorrevenda)

                txtComprador = itemView.findViewById(R.id.item_produto_comprador)
                txtDataPagmento = itemView.findViewById(R.id.item_produto_datapagamento)

                txtStatus = itemView.findViewById(R.id.item_produto_status)
            }

            fun bind(produto: Produto) {
                txtTipo?.text = formatarString(produto.codigo) + " - " + produto.tipo
                txtValorRevenda?.text = formatarMoeda(produto.valorRevenda)

                txtStatus?.text = produto.status
                txtComprador?.text = produto.comprador
                txtDataPagmento?.text = formatarData(produto.dataPagamento)
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ProdutoViewHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
            val produto: Produto = list[position]
            holder.bind(produto)
        }

        override fun getItemCount(): Int = list.size

    }

    @IgnoreExtraProperties
    data class Produto(var codigo: String?, var tipo: String) {

        var valorCompra: Double = 0.0
        var valorRevenda: Double = 25.90

        var comprador: String? = "Ivanilda"
        var dataVenda: Date = Date()
        var dataPagamento: Date? = null

        var status: String = "Aguardando Pagamento"

    }

    companion object {
        fun formatarMoeda(valor: Double): String {
            return "R$ " + DecimalFormat("#,###.00").format(valor)
        }

        fun formatarData(valor: Date?): String {
            if (valor == null) return "--/--/----"
            return SimpleDateFormat("dd/MM/yyyy").format(valor)
        }

        fun formatarString(valor: String?): String {
            if (valor == null) return "00000000"
            return valor?.padStart(8, '0')
        }

    }

}