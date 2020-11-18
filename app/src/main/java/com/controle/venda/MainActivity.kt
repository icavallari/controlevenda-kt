package com.controle.venda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var vendas = arrayListOf(

        Venda(
            "Ivanilda", arrayOf(
                Produto("AF5641", "Brinco")
                , Produto("2AGH641", "Tornozeleira")
                , Produto("2AUAS41", "Pulseira")
                , Produto("2KASS41", "Gargantilha")
            )
        )

        , Venda("Gabriela", arrayOf(Produto("Q15641", "Colar")))
        , Venda("Elena", arrayOf(Produto("Q15641", "Anel")))

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var auth = Firebase.auth

        auth.signInWithEmailAndPassword(
            getString(R.string.emaiLogin),
            getString(R.string.senhaLogin)
        )
            .addOnCompleteListener(this) {
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                //connectDb()

                initRecycler()
            }
            .addOnFailureListener(this) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }

        btn_add_venda_act.setOnClickListener {

            val intent = Intent(this, CadastroVendaActivity::class.java)
            startActivity(intent)

        }

    }

    fun initRecycler() {

        recycler_view_act_main.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = VendaAdapter(vendas)
        }

    }

    fun connectDb() {

        val database = Firebase.database
        database.setPersistenceEnabled(true)

        val vendasRef = database.getReference("_vendas_")
        vendasRef.setValue(vendas)

        //produtos.push()
    }

}