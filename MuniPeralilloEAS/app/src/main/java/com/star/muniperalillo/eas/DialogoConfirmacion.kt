package com.star.muniperalillo.eas

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.TextView

class DialogoConfirmacion (mensaje_flotante_conf: Context?, actividad: FinalizoCuadroDialogo?)  {
    @JvmField
    var aceptaraccion: Button
    var cancelaraccion: Button
    var contenido_mensaje: TextView
    private var interfaz: FinalizoCuadroDialogo? = null

    interface FinalizoCuadroDialogo {
        fun ResultadoCuadroDialogo(res: Boolean)
    }

    init {
        interfaz = actividad
        val dialogo = Dialog(mensaje_flotante_conf!!)
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogo.setCancelable(false)
        dialogo.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogo.setContentView(R.layout.dialogo_conf)
        aceptaraccion = dialogo.findViewById(R.id.aceptaraccion)
        cancelaraccion = dialogo.findViewById(R.id.cancelaraccion)
        contenido_mensaje = dialogo.findViewById(R.id.contenido_mensaje)
        aceptaraccion.setOnClickListener{
            interfaz!!.ResultadoCuadroDialogo(true)
            dialogo.dismiss()
        }
        cancelaraccion.setOnClickListener{
            interfaz!!.ResultadoCuadroDialogo(false)
            dialogo.dismiss()
        }
        dialogo.show()
    }
}