package com.star.muniperalillo.eas

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.Settings
import android.text.InputFilter
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.security.ProviderInstaller
import es.dmoral.toasty.Toasty
import hari.bounceview.BounceView
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONException

class Login : AppCompatActivity(), DialogoConfirmacion.FinalizoCuadroDialogo {
    var ja: JSONArray? = null
    var preferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    var mensaje_flotante_incorrecto: Context? = null
    var progressDialog_carga: ProgressDialog? = null
    var REQUEST_ACCESS_FINE = 0
    var mensaje_flotante_conf: Context? = null
    var valor = false
    var varseleccion = 0

    override fun onResume() {
        super.onResume()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        delayedHide(300)
        mHideHandler.removeMessages(0)
        hideSystemUI()
    }

    override fun onRestart() {
        super.onRestart()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        delayedHide(300)
        mHideHandler.removeMessages(0)
        hideSystemUI()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.navigationBarColor = resources.getColor(R.color.Color_Medio_BlancoAzul)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        delayedHide(300)
        mHideHandler.removeMessages(0)
        hideSystemUI()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        BounceView.addAnimTo(recuperar).setScaleForPushInAnim(1.0f, 1.0f).setScaleForPopOutAnim(1.1f, 1.1f).setPopOutAnimDuration(100)
        BounceView.addAnimTo(registrarse).setScaleForPushInAnim(1.0f, 1.0f).setScaleForPopOutAnim(1.1f, 1.1f).setPopOutAnimDuration(100)
        BounceView.addAnimTo(llamada).setScaleForPushInAnim(1.0f, 1.0f).setScaleForPopOutAnim(1.1f, 1.1f).setPopOutAnimDuration(100)
        mensaje_flotante_incorrecto = this
        mensaje_flotante_conf = this
        volver.setOnClickListener{
            startActivity(Intent(applicationContext, MainActivity::class.java))
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            finish()
        }
        ingresar.setOnClickListener{
            val valor_nombre = nombre.getText().toString()
            val valor_clave = clave.getText().toString()
            when{
                valor_nombre == "" -> {
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.nombre_error)
                    nombre.requestFocus()
                }
                valor_clave == "" -> {
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.clave_error)
                    clave.requestFocus()
                }
                else -> {
                    progressDialog_carga = ProgressDialog(this@Login)
                    progressDialog_carga!!.dismiss()
                    progressDialog_carga!!.setTitle("Consultando Perfil")
                    progressDialog_carga!!.setMessage("Espere un Momento...")
                    progressDialog_carga!!.setCancelable(false)
                    progressDialog_carga!!.show()
                    preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE)
                    val usua = preferences!!.getString("login_perfil", "")
                    val ruts = preferences!!.getString("valor_rut", "")
                    val emails = preferences!!.getString("valor_email", "")
                    val fechas = preferences!!.getString("valor_fecha", "")
                    val telefonos = preferences!!.getString("valor_telefono", "")
                    val direccions = preferences!!.getString("valor_direccion", "")
                    when {
                        usua == "" && ruts == "" && emails == "" && fechas == "" && telefonos == "" && direccions == "" -> System.out.println("Vacio")
                        else -> {
                            preferences!!.edit().clear().apply()
                        }
                    }
                    consulta_login()
                }
            }
        }
        registrarse.setOnClickListener{
            startActivity(Intent(applicationContext, Registro::class.java))
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            finish()
        }
        llamada.setOnClickListener{
            when{
                ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED -> {
                    varseleccion = 1
                    val ms = DialogoConfirmacion(mensaje_flotante_conf, this@Login)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.act_ptelefono) }
                else -> {varseleccion = 2
                    val ms = DialogoConfirmacion(mensaje_flotante_conf, this@Login)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.llamar_seguridad_ciudadana) }
            }
        }
        recuperar.setOnClickListener{
            val valor_nombre = nombre.getText().toString()
            when {
                valor_nombre == "" -> {
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.correo_recuperacion_error)
                    nombre.requestFocus()
                }
                else -> {
                    when{
                        !validarEmail(nombre.getText().toString()) -> {
                            val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                            ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                            ms.contenido_mensaje.setText(R.string.correo_no_válido_error)
                            nombre.requestFocus()
                        }
                        else -> {
                            progressDialog_carga = ProgressDialog(this@Login)
                            progressDialog_carga!!.dismiss()
                            progressDialog_carga!!.setTitle("Enviando Correo")
                            progressDialog_carga!!.setMessage("Espere un Momento...")
                            progressDialog_carga!!.setCancelable(false)
                            progressDialog_carga!!.show()
                            recuperar_contra()
                        }
                    }
                }
            }
        }
        val charactersForbiden2 = "! ΔΔ∆Δδ₲₡✓ÇüæęĘąǍǎČčĎďĚěǦǧȞȟǏǐǰǨǩĽľŇňǑǒŘřŠšṦṧŤťǓǔǙǚŽžǮǯĄłŁżŻĖČčėÆâôäöàòåûçùêÿëÖèÜï¢î£ì¥Ä₧Åƒ░▒▓│┤╡ª╢º╖¿╕⌐╣¬║½╗¼╝¡╜«╛»┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αßΓπΣσµτΦΘΩδ∞φε∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■ËÌÎÏÐÒÔÕÖ×ØÙÛÜÞßàâãäåæçèêëìîïðòôõö÷øùûüþÿ¯°±²³´µ¶·¸¹º»¼½¾¿ÀÂÃÄÅĒēĀāǢǣĪīŌōŪūǕǖȲȳÆÇÈÊ’“”•–—˜™š›œžŸ¡¢£¤¥¦§¨©ª«¬®{|}~Δ€‚ƒ„…†‡ˆ‰Š‹ŒŽ‘[\\]^`=>?:;<\"#$%&'()*+,/"
        val filtro_nombre = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                val type = Character.getType(source[i])
                when{
                    charactersForbiden2.contains("" + source) || type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt() -> return@InputFilter ""
                }
            }
            null
        }
        val charactersForbiden = "!ćĆΔΔΔ∆δ₲₡✓ÇÉüæéęĘąǍǎČčĎďĚěǦǧȞȟǏǐǰǨǩĽľŇňǑǒŘřŠšṦṧŤťǓǔǙǚŽžǮǯĄłŁżŻĖńŃČčėÆâôäöàòåûçùêÿëÖèÜï¢î£ì¥Ä₧Åƒá░í▒ó▓ú│┤╡ª╢º╖¿╕⌐╣¬║½╗¼╝¡╜«╛»┐ └┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αßΓπΣσµτΦΘΩδ∞φε∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■ËÌÍÎÏÐÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðòóôõö÷øùúûüýþÿ¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅĒēĀāǢǣĪīŌōŪūǕǖȲȳÆÇÈÉÊ’“”•–—˜™š›œžŸ¡¢£¤¥¦§¨©ª«¬®{|}~Δ€‚ƒ„…†‡ˆ‰Š‹ŒŽ‘[\\]^_`=>?@:;<\"#$%&'()*+,-./"
        val filtro_clave = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                when {
                    source != null && charactersForbiden.contains("" + source) || !Character.isLetterOrDigit(source[i]) -> return@InputFilter ""
                }
            }
            null
        }
        nombre.setFilters(arrayOf(InputFilter.LengthFilter(70), filtro_nombre))
        clave.setFilters(arrayOf(InputFilter.LengthFilter(30), filtro_clave))
        pedir_telefono()
        nombre.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                when {
                    !hasFocus -> {nombre.setTextColor(resources.getColor(R.color.colorPrimary))
                        nombre.setHintTextColor(resources.getColor(R.color.plomo))
                    }
                    else -> {nombre.setTextColor(resources.getColor(R.color.blancopuro))
                        nombre.setHintTextColor(resources.getColor(R.color.plomoblanco))
                    }
                }
                delayedHide(300)
                mHideHandler.removeMessages(0)
                hideSystemUI()
            }
        })
        clave.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                when {
                    !hasFocus -> { clave.setTextColor(resources.getColor(R.color.colorPrimary))
                        clave.setHintTextColor(resources.getColor(R.color.plomo))
                    }
                    else -> { clave.setTextColor(resources.getColor(R.color.blancopuro))
                        clave.setHintTextColor(resources.getColor(R.color.plomoblanco))

                    }
                }
                delayedHide(300)
                mHideHandler.removeMessages(0)
                hideSystemUI()
            }
        })
        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                delayedHide(300)
                mHideHandler.removeMessages(0)
                hideSystemUI()
            } else {
                delayedHide(300)
                mHideHandler.removeMessages(0)
                hideSystemUI()
            }
        }
    }

    fun recuperar_contra() {
        var registro = "https://systemchile.com/appmunicipal/login/recuperar_contrasena.php?a=" + nombre!!.text
        registro = registro.replace(" ", "%20")
        Enviar_Correo(registro)
    }

    fun Enviar_Correo(URL: String?) {
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, URL, Response.Listener {
                progressDialog_carga!!.dismiss()
                Toasty.success(applicationContext, "Contraseña Enviada a tu Correo", Toasty.LENGTH_SHORT, true).show() },
            Response.ErrorListener { error ->
                Handler().postDelayed({
                    Log.i("Mensaje Vollley Error: ", error.toString() + "")
                    Toasty.warning(applicationContext, "Consultando Datos, Espere un Momento...", Toasty.LENGTH_SHORT, true).show()
                    recuperar_contra()
                }, 7000)
            })
        stringRequest.setShouldCache(false)
        queue.add(stringRequest)
    }

    fun consulta_login() {
        var registro = "https://systemchile.com/appmunicipal/login/login.php?a=" + nombre!!.text + "&b=" + clave!!.text
        registro = registro.replace(" ", "%20")
        EnviarRecibirDatos(registro)
    }

    fun EnviarRecibirDatos(URL: String?) {
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, URL, Response.Listener { response -> var response = response
            response = response.replace("][", ",")
            when{
                response.length > 0 -> {
                    guardar_preferencias()
                } else -> {
                Toasty.warning(applicationContext, "Este Perfil no está Registrado", Toasty.LENGTH_SHORT, true).show()
                progressDialog_carga!!.dismiss()
                nombre!!.setText("")
                clave!!.setText("")
                nombre!!.requestFocus()
            }
            }
        }, Response.ErrorListener { error -> Log.i("Mensaje Vollley Error: ", error.toString() + "")
            Handler().postDelayed({
                Toasty.warning(applicationContext, "Consultando Datos, Espere un Momento...", Toasty.LENGTH_SHORT, true).show()
                consulta_login()
            }, 7000)
        })
        stringRequest.setShouldCache(false)
        queue.add(stringRequest)
    }

    private fun guardar_preferencias() {
        preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE)
        val n_u = nombre!!.text.toString()
        val c_u = clave!!.text.toString()
        editor = preferences!!.edit()
        when{
            recordar!!.isChecked -> {
                editor!!.putString("nombre_usuario", n_u)
                editor!!.putString("clave_usuario", c_u)
            }
            else ->{
                borrar_preferencias()
            }
        }
        consulta_nombre()
    }

    private fun borrar_preferencias() {
        preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE)
        val usua = preferences!!.getString("nombre_usuario", "")
        val clave = preferences!!.getString("clave_usuario", "")
        when{
            usua == "" || clave == "" -> System.out.println("Vacio")
            else -> {
                editor!!.remove("nombre_usuario")
                editor!!.remove("clave_usuario")
                editor!!.apply()
            }
        }
    }

    fun consulta_nombre() {
        var registro = "https://systemchile.com/appmunicipal/login/buscar_rut.php?a=" + nombre!!.text
        registro = registro.replace(" ", "%20")
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, registro, Response.Listener { response -> var response = response
            response = response.replace("][", ",")
            when {
                response.length > 0 -> {
                    try {
                        ja = JSONArray(response)
                        val valor_r = ja!!.getString(0)
                        val valor_e = ja!!.getString(1)
                        val valor_f = ja!!.getString(2)
                        val valor_n = ja!!.getString(3)
                        val valor_te = ja!!.getString(4)
                        val valor_di = ja!!.getString(5)
                        editor!!.putString("valor_rut", valor_r)
                        editor!!.putString("valor_email", valor_e)
                        editor!!.putString("valor_fecha", valor_f)
                        editor!!.putString("login_perfil", valor_n)
                        editor!!.putString("valor_telefono", valor_te)
                        editor!!.putString("valor_direccion", valor_di)
                        editor!!.apply()
                        Toasty.info(applicationContext, "Bienvenido Usuario: " + nombre!!.text.toString(), Toasty.LENGTH_SHORT, true).show()
                        progressDialog_carga!!.dismiss()
                        nombre!!.setText("")
                        clave!!.setText("")
                        nombre!!.requestFocus()
                        val i = Intent(applicationContext, Menu::class.java)
                        startActivity(i)
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                        finish()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        }, Response.ErrorListener { error -> Log.i("Mensaje Vollley Error: ", error.toString() + "")
            Handler().postDelayed({
                Toasty.warning(applicationContext, "Consultando Datos, Espere un Momento...", Toasty.LENGTH_SHORT, true).show()
                consulta_nombre()
            }, 7000)
        })
        stringRequest.setShouldCache(false)
        queue.add(stringRequest)
    }

    private fun validarEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode == REQUEST_ACCESS_FINE){
            grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED -> System.out.println("Vacio")
            else -> {
                //pedir_telefono()
            }
        }
    }

    fun pedir_telefono() {
        when { ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED -> ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.CALL_PHONE), REQUEST_ACCESS_FINE)
        }
    }

    override fun ResultadoCuadroDialogo(res: Boolean) {
        valor = res
        when (valor) {
            true -> {
                when (varseleccion) {
                    1 -> {
                        Toasty.info(applicationContext, "* Pinche en Permisos \u000A* Luego Pinche en Teléfono", Toasty.LENGTH_LONG, true).show()
                        startActivity(
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(
                                Uri.fromParts("package", packageName, null))) }
                    2 -> { Toasty.info(applicationContext, "Seguridad Ciudadana: \u000A Llamando...", Toasty.LENGTH_SHORT, true).show()
                        val callIntent = Intent(Intent.ACTION_CALL)
                        callIntent.data = Uri.parse("tel:+56 9 7623 2889")
                        startActivity(callIntent)
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                    }
                }
            }
            false -> {
                System.out.println("Vacio")
                varseleccion = 0
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // When the window loses focus (e.g. the action overflow is shown),
        // cancel any pending hide action. When the window gains focus,
        // hide the system UI.
        when{
            hasFocus -> delayedHide(300)
            else -> mHideHandler.removeMessages(0)
        }
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LOW_PROFILE or View.SYSTEM_UI_FLAG_IMMERSIVE
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    private val mHideHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            hideSystemUI()
        }
    }

    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeMessages(0)
        mHideHandler.sendEmptyMessageDelayed(0, delayMillis.toLong())
    }
}
