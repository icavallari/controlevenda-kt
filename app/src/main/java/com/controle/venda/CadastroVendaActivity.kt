package com.controle.venda

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_cadastro_venda.*
import java.util.*

class CadastroVendaActivity : AppCompatActivity() {

    var datePickerDialog: DatePickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_venda)

        var calendar = Calendar.getInstance()

        edt_previsaopagamento_cadvenda_act.setOnClickListener {
            openDatePicker()
        }

        edt_previsaopagamento_cadvenda_act.setOnFocusChangeListener { view: View, hasFocus: Boolean ->
            if (hasFocus) openDatePicker()
        }

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