package com.star.muniperalillo.eas

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class acerdade : AppCompatActivity() {
    private val volver by lazy {findViewById<Button>(R.id.volver)}
    private val nab by lazy {findViewById<TextView>(R.id.nab)}
    private val inform by lazy {findViewById<TextView>(R.id.inform)}
    var preferences: SharedPreferences? = null
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
        setContentView(R.layout.activity_acerdade)
        when {
            Build.VERSION.SDK_INT >= 21-> window.navigationBarColor = resources.getColor(R.color.white)}
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        delayedHide(300)
        mHideHandler.removeMessages(0)
        hideSystemUI()
        volver.setOnClickListener { finish() }
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
        verificar_usuarios()
    }

    fun verificar_usuarios() {
        val valor_rut = preferences!!.getString("valor_rut", "")
        var registro = "https://systemchile.com/appmunicipal/login/buscar_res.php?a=$valor_rut"
        registro = registro.replace(" ", "%20")
        ConsultaDatos(registro)
    }

    fun ConsultaDatos(URL: String?) {
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, URL, Response.Listener { response ->
            var response = response
            response = response.replace("][", ",")
            when{
                response.length > 0 -> inform!!.setTextColor(resources.getColor(R.color.textview))
                else -> nab!!.setTextColor(resources.getColor(R.color.textview))
            }
        }, Response.ErrorListener { error -> Log.i("Mensaje Vollley Error: ", error.toString() + "")
            Handler().postDelayed({
                verificar_usuarios()
            }, 5000)
        })
        stringRequest.setShouldCache(false)
        queue.add(stringRequest)
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
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
