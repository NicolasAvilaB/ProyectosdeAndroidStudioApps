package com.star.muniperalillo.eas

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import es.dmoral.toasty.Toasty
import hari.bounceview.BounceView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val frombottom by lazy { AnimationUtils.loadAnimation(this, R.anim.frombottom)}
    private val boton_fade by lazy { AnimationUtils.loadAnimation(this, R.anim.boton_fade)}
    private val boton_reg by lazy { AnimationUtils.loadAnimation(this, R.anim.boton_reg)}
    private val bganim by lazy { AnimationUtils.loadAnimation(this, R.anim.bganim)}
    private var preferences: SharedPreferences? = null
    private var progressDialog_carga: ProgressDialog? = null
    private var nombre_usuario: String? = null
    private var clave_usuario: String? = ""
    var REQUEST_ACCESS_FINE = 0
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
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        delayedHide(300)
        mHideHandler.removeMessages(0)
        hideSystemUI()
        BounceView.addAnimTo(registrarse).setScaleForPushInAnim(1.0f, 1.0f).setScaleForPopOutAnim(1.1f, 1.1f).setPopOutAnimDuration(100)
        cargar_preferencias()
        texto3.startAnimation(frombottom)
        texto4.startAnimation(frombottom)
        boton_login.startAnimation(boton_fade)
        texto_registrate.startAnimation(boton_reg)
        fondo.startAnimation(bganim)
        boton_login.setOnClickListener {
            startActivity(Intent(applicationContext, Login::class.java))
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            finish()
        }
        registrarse.setOnClickListener {
            startActivity(Intent(applicationContext, Registro::class.java))
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            finish()
        }
        pedir_gps()
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

    private fun cargar_preferencias() {
        preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE)
        nombre_usuario = preferences!!.getString("nombre_usuario", "")
        clave_usuario = preferences!!.getString("clave_usuario", "")
        if (nombre_usuario == "" || clave_usuario == "") {
        } else {
            progressDialog_carga = ProgressDialog(this@MainActivity)
            progressDialog_carga!!.dismiss()
            progressDialog_carga!!.setTitle("Consultando Perfil")
            progressDialog_carga!!.setMessage("Espere un Momento...")
            progressDialog_carga!!.setCancelable(false)
            progressDialog_carga!!.show()
            consulta_login(nombre_usuario, clave_usuario)
        }
    }

    fun consulta_login(nombre_usuario: String?, clave_usuario: String?) {
        var registro = "https://systemchile.com/appmunicipal/login/login.php?a=$nombre_usuario&b=$clave_usuario"
        registro = registro.replace(" ", "%20")
        EnviarRecibirDatos(registro)
    }

    fun EnviarRecibirDatos(URL: String?) {
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, URL, Response.Listener { response -> var response = response
            response = response.replace("][", ",")
            if (response.length > 0) {
                Toasty.info(applicationContext, "Bienvenido Usuario: $nombre_usuario", Toasty.LENGTH_SHORT, true).show()
                progressDialog_carga!!.dismiss()
                val i = Intent(applicationContext, Menu::class.java)
                startActivity(i)
                finish()
            } else {
                Toasty.warning(applicationContext, "Este Perfil no está Registrado", Toasty.LENGTH_SHORT, true).show()
                progressDialog_carga!!.dismiss()
                val i = Intent(applicationContext, Login::class.java)
                startActivity(i)
                finish()
            }
        }, Response.ErrorListener { error -> Log.i("Mensaje Vollley Error: ", error.toString() + "")
            Handler().postDelayed({
                Toasty.warning(applicationContext, "Consultando Datos, Espere un Momento...", Toasty.LENGTH_SHORT, true).show()
                consulta_login(nombre_usuario, clave_usuario)
            }, 7000)
        })
        stringRequest.setShouldCache(false)
        queue.add(stringRequest)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_ACCESS_FINE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                //pedir_gps()
            }
        }
    }

    fun pedir_gps() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // When the window loses focus (e.g. the action overflow is shown),
        // cancel any pending hide action. When the window gains focus,
        // hide the system UI.
        if (hasFocus) {
            delayedHide(300)
        } else {
            mHideHandler.removeMessages(0)
        }
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_IMMERSIVE
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
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
