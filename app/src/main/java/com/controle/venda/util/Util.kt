package com.controle.venda.util

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class Util {

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