package com.controle.venda.util

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import java.text.DecimalFormat

class CurrencyWatcher(val view: TextInputEditText) : TextWatcher {

    var current = ""

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s.toString() == current) return

        view.removeTextChangedListener(this)

        var value: String = s.toString()
            .replace("[\\,\\.]".toRegex(), "")
            .replace("^0+".toRegex(), "")

        if (value.length == 1) {
            value = "0.0" + value

        } else if (value.length == 2) {
            value = "0." + value

        } else {
            value = value.substring(0, value.length - 2) + "." + value.substring(value.length - 2)
        }

        value = DecimalFormat("#,##0.00").format(value.toDouble())

        current = value
        view.setText(current)
        view.setSelection(current.length)

        view.addTextChangedListener(this)
    }
}