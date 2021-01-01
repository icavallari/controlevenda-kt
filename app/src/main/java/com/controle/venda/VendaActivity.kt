package com.controle.venda

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.controle.venda.database.AppDatabase
import com.controle.venda.model.Produto
import com.controle.venda.model.Venda
import com.controle.venda.util.CurrencyWatcher
import com.controle.venda.util.Util
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_cadastro_venda.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*
import kotlin.collections.ArrayList

class VendaActivity : AppCompatActivity() {

    companion object {
        val EXTRA_VENDA_ID: String = "VENDA_ACT_KEY_VENDA_ID"
    }

    var vendaId: Long = 0
    lateinit var venda: Venda
    lateinit var database: AppDatabase
    var produtos: ArrayList<Produto> = ArrayList()

    var adapter = ProdutoAdapter(ArrayList())

    var datePickerDialog: DatePickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_venda)

        setupDatabase()

        vendaId = intent.getLongExtra(EXTRA_VENDA_ID, 0)

        setupRecycler()

        edt_previsaopagamento_cadvenda_act.setOnClickListener {
            openDatePicker()
        }

        edt_previsaopagamento_cadvenda_act.setOnFocusChangeListener { view: View, hasFocus: Boolean ->
            if (hasFocus) openDatePicker()
        }

        val situacoes = resources.getStringArray(R.array.situacoes)
        val situacoesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, situacoes)
        spn_status_cadvenda_act.setAdapter(situacoesAdapter)

        btn_adicionar_produto.setOnClickListener {

            abrirProduto(Produto())

        }

    }

    override fun onResume() {
        super.onResume()

        if (vendaId == null) {
            venda = Venda()
            return
        }

        doAsync {
            var vendaDb = database.vendaDao().findById(vendaId)
            uiThread {
                venda = vendaDb

                spn_status_cadvenda_act.setText(venda.status)
                edt_comprador_cadvenda_act.setText(venda.comprador)
                edt_previsaopagamento_cadvenda_act.setText(Util.formatarData(venda.previsaoPagamento))
            }
        }

        doAsync {
            var produtosDb = database.produtoDao().findByVendaId(vendaId)

            uiThread {

                produtos = produtosDb as ArrayList<Produto>
                recarregarProdutos(produtos)

            }

        }
    }

    fun abrirProduto(produto: Produto) {

        val tipos = resources.getStringArray(R.array.tipos)
        val tiposAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipos)

        val view: View = layoutInflater.inflate(R.layout.produto, null);
        val txtTipos = view.findViewById<AutoCompleteTextView>(R.id.spn_tipo_ald_produto)
        val txtCodigo = view.findViewById<TextInputEditText>(R.id.edt_codigo_ald_produto)
        val txtValorRevenda = view.findViewById<TextInputEditText>(R.id.edt_valorrevenda_ald_produto)

        txtValorRevenda.addTextChangedListener(CurrencyWatcher(txtValorRevenda))

        txtTipos.setAdapter(tiposAdapter)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Novo Produto")
        builder.setView(view)
        builder.setPositiveButton("Cadastrar") { dialog, which ->

            var valorRevenda: String = txtValorRevenda.text.toString()
            valorRevenda = valorRevenda.replace(".", "").replace(",", ".")
            produtos.add(Produto(0, txtTipos.text.toString(), txtCodigo.text.toString(), vendaId, valorRevenda.toDouble()))
            recarregarProdutos(produtos)

        }

        builder.setNegativeButton("Cancelar") { dialog, which -> }

        val dialog: AlertDialog = builder.create()
        dialog.show()

        txtTipos.setText(produto.tipo)
        txtCodigo.setText(produto.codigo)
        txtValorRevenda.setText(String.format("%.2f", produto.valorRevenda))

    }

    fun recarregarProdutos(produtos: List<Produto>) {

        adapter.produtos = produtos
        adapter.notifyDataSetChanged()

    }

    fun setupRecycler() {
        recycler_view_act_venda.apply {
            layoutManager = LinearLayoutManager(context)
        }
        recycler_view_act_venda.adapter = adapter

        adapter.setItemClickListener { produto ->
            abrirProduto(produto)
        }
    }

    private fun setupDatabase() {
        database = AppDatabase.getInstance(this)
    }

    private fun openDatePicker() {
        val arr = getDate()
        if (datePickerDialog == null) {
            datePickerDialog = DatePickerDialog(
                this, dateSetListener, arr[2], arr[1], arr[0]
            )

        } else {
            datePickerDialog?.updateDate(arr[2], arr[1], arr[0])
        }

        if (datePickerDialog?.isShowing() == true) return

        datePickerDialog?.show()
    }

    private val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(
            view: DatePicker, year: Int, month: Int, day: Int
        ) {
            var data: String = String.format("%02d/%02d/%d", day, month + 1, year)
            edt_previsaopagamento_cadvenda_act.setText(data)
        }
    }

    private fun getDate(): Array<Int> {
        val data: String = edt_previsaopagamento_cadvenda_act?.text.toString()
        if (data == null || data.isEmpty()) {
            val calendar = Calendar.getInstance()
            return arrayOf(
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR)
            )
        }

        val arr = data.split("/")
        return arrayOf(
            Integer.valueOf(arr[0]), Integer.valueOf(arr[1]) - 1, Integer.valueOf(arr[2])
        )
    }

}