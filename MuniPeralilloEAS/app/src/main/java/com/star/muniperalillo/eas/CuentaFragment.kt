package com.star.muniperalillo.eas

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty
import hari.bounceview.BounceView

class CuentaFragment : Fragment(), DialogoConfirmacion.FinalizoCuadroDialogo {
    var cerrar: Button? = null
    var acerda_de: Button? = null
    var salir: Button? = null
    var nombre_perfil: TextView? = null
    var rut_perfil: TextView? = null
    var mensaje_flotante_conf: Context? = null
    var valor = false
    var email_perfil: TextView? = null
    var fecha_perfil: TextView? = null
    var root: View? = null
    var preferences: SharedPreferences? = null
    var llamada: Button? = null
    var varseleccion = 0;
    var REQUEST_ACCESS_FINE = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_cuenta, container, false)
        mensaje_flotante_conf = context
        preferences = activity!!.getSharedPreferences("credenciales", Context.MODE_PRIVATE)
        val login_perfil = preferences!!.getString("login_perfil", "")
        val valor_rut = preferences!!.getString("valor_rut", "")
        val valor_email = preferences!!.getString("valor_email", "")
        var valor_fecha = preferences!!.getString("valor_fecha", "")
        valor_fecha = valor_fecha!!.replace("-", "")
        nombre_perfil = root!!.findViewById(R.id.nombre_perfil)
        rut_perfil = root!!.findViewById(R.id.rut_perfil)
        email_perfil = root!!.findViewById(R.id.email_perfil)
        fecha_perfil = root!!.findViewById(R.id.fecha_perfil)
        acerda_de = root!!.findViewById(R.id.acerda_de)
        cerrar = root!!.findViewById(R.id.cerrar)
        salir = root!!.findViewById(R.id.salir)
        llamada = root!!.findViewById(R.id.llamada)
        BounceView.addAnimTo(llamada).setScaleForPushInAnim(1.0f, 1.0f).setScaleForPopOutAnim(1.2f, 1.2f).setPopOutAnimDuration(100)
        acerda_de!!.setOnClickListener{
            val i = Intent(activity, acerdade::class.java)
            startActivity(i)
        }
        cerrar!!.setOnClickListener {
            varseleccion = 1
            val ms = DialogoConfirmacion(mensaje_flotante_conf, this@CuentaFragment)
            ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
            ms.contenido_mensaje.setText(R.string.cerrarsesion_app)
        }
        salir!!.setOnClickListener {
            varseleccion = 2
            val ms = DialogoConfirmacion(mensaje_flotante_conf, this@CuentaFragment)
            ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
            ms.contenido_mensaje.setText(R.string.salir_app)
        }
        llamada!!.setOnClickListener{
            when{
                ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED -> {
                    varseleccion = 3
                    val ms = DialogoConfirmacion(mensaje_flotante_conf, this@CuentaFragment)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.act_ptelefono)
                }
                else -> { varseleccion = 4
                    val ms = DialogoConfirmacion(mensaje_flotante_conf, this@CuentaFragment)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.llamar_seguridad_ciudadana)
                }
            }
        }
        nombre_perfil!!.setText(login_perfil)
        rut_perfil!!.setText(valor_rut)
        email_perfil!!.setText(valor_email)
        val fecha_ano = valor_fecha.substring(0,4)
        var fecha_mes = valor_fecha.substring(4,6)
        val fecha_dia = valor_fecha.substring(6,8)
        when(fecha_mes){
            "01" -> fecha_mes = "Enero"
            "02" -> fecha_mes = "Febrero"
            "03" -> fecha_mes = "Marzo"
            "04" -> fecha_mes = "Abril"
            "05" -> fecha_mes = "Mayo"
            "06" -> fecha_mes = "Junio"
            "07" -> fecha_mes = "Julio"
            "08" -> fecha_mes = "Agosto"
            "09" -> fecha_mes = "Septiembre"
            "10" -> fecha_mes = "Octubre"
            "11" -> fecha_mes = "Noviembre"
            "12" -> fecha_mes = "Diciembre"
        }
        val fecha_convertida = fecha_dia + " de " + fecha_mes + " de " + fecha_ano
        fecha_perfil!!.setText(fecha_convertida)
        preferences = activity!!.getSharedPreferences("credenciales", Context.MODE_PRIVATE)
        val usua = preferences!!.getString("nombre_usuario", "")
        when(usua){
            "" -> cerrar!!.setVisibility(View.GONE)
            else -> cerrar!!.setVisibility(View.VISIBLE)
        }

        return root
    }

    private fun borrar_preferencias() {
        preferences = activity!!.getSharedPreferences("credenciales", Context.MODE_PRIVATE)
        val usua = preferences!!.getString("nombre_usuario", "")
        when(usua) {
            ""-> { System.out.println("Vacio") }
            else ->{ preferences!!.edit().clear().apply() }
        }
    }

    override fun ResultadoCuadroDialogo(res: Boolean) {
        valor = res
        when (valor){
            true -> {
                when (varseleccion) {
                    1 -> {borrar_preferencias()
                        activity!!.finish()
                        val intent = Intent(Intent.ACTION_MAIN)
                        intent.addCategory(Intent.CATEGORY_HOME)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                    2 -> {activity!!.finish()
                        val intent = Intent(Intent.ACTION_MAIN)
                        intent.addCategory(Intent.CATEGORY_HOME)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                    3 -> {
                        Toasty.info(activity!!, "* Pinche en Permisos \u000A* Luego Pinche en Teléfono", Toasty.LENGTH_LONG, true).show()
                        startActivity(
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(
                                Uri.fromParts("package", activity!!.packageName, null)));
                    }
                    4 -> {
                        Toasty.info(activity!!, "Seguridad Ciudadana: \u000A Llamando...", Toasty.LENGTH_SHORT, true).show()
                        val callIntent = Intent(Intent.ACTION_CALL)
                        callIntent.data = Uri.parse("tel:+56 9 7623 2889")
                        startActivity(callIntent)
                        activity!!.overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                    }
                }
            }
            false -> { System.out.println("Vacio")
                varseleccion = 0}
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode == REQUEST_ACCESS_FINE) {
            grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED -> System.out.println("Vacio")
            else -> {
                pedir_telefono()
            }
        }
    }

    fun pedir_telefono() {
        when {
            ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED -> ActivityCompat.requestPermissions(activity!!, arrayOf(
                Manifest.permission.CALL_PHONE), REQUEST_ACCESS_FINE)
        }
    }
}