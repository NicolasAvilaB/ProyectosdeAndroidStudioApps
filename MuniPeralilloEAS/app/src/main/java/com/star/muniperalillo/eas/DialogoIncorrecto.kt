package com.star.muniperalillo.eas

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.TextView

class DialogoIncorrecto(mensaje_flotante_incorrecto: Context?) {
    var aceptaraccion: Button
    @JvmField
    var contenido_mensaje: TextView

    init {
        val dialogo = Dialog(mensaje_flotante_incorrecto!!)
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogo.setCancelable(false)
        dialogo.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogo.setContentView(R.layout.dialogo_incorrecto)
        aceptaraccion = dialogo.findViewById(R.id.aceptaraccion)
        contenido_mensaje = dialogo.findViewById(R.id.contenido_mensaje)
        aceptaraccion.setOnClickListener { dialogo.dismiss() }
        dialogo.show()
    }
}