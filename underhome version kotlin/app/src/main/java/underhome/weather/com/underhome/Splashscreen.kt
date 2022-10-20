package underhome.weather.com.underhome

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.*
import kotlinx.android.synthetic.main.activity_splashscreen.*
import org.json.JSONArray
import org.json.JSONException

class Splashscreen : AppCompatActivity() {

    internal var a: String = ""
    internal var b: String = ""

    override fun onPause() {
        super.onPause()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        supportActionBar!!.hide()
        if (Build.VERSION.SDK_INT >= 19) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
        val m = AnimationUtils.loadAnimation(this, R.anim.fadesplash)
        imageView2.startAnimation(m)

        val decorview = window.decorView
        val ui = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //| View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorview.systemUiVisibility = ui

        EnviarRecibirDatos("http://systemchile.com/sensorDHT11/mostrar.php")

        Handler().postDelayed({
            val m = AnimationUtils.loadAnimation(this@Splashscreen, R.anim.fadesplash)
            val m2 = AnimationUtils.loadAnimation(this@Splashscreen, R.anim.fadesplash)
            proceso.startAnimation(m)
            compr.startAnimation(m2)

            val conexion = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val wifi = conexion.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            val datos = conexion.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

            if (wifi.state.toString() == "CONNECTED") {
                proceso.progress = 40
                compr.text = "Conexion desde Wifi"
                Handler().postDelayed({
                    proceso.progress = 75
                    compr.text = "Verificando Conexion a Internet Espere..."
                    val queue = Volley.newRequestQueue(this@Splashscreen)
                    val ping = "https://www.google.cl"
                    val stringRequest = StringRequest(Request.Method.GET, ping, Response.Listener {
                        //Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                        //progressBar2.dismiss();
                        proceso.progress = 100
                        compr.text = "Conectado al Internet"
                        Handler().postDelayed({
                            val i2 = Intent(this@Splashscreen, DHT11::class.java)
                            i2.putExtra("temperatura", a)
                            i2.putExtra("humedad", b)
                            startActivity(i2)
                            overridePendingTransition(R.anim.left_in, R.anim.left_out)
                            finish()
                        }, 2500)
                    }, Response.ErrorListener {
                        /*Toast.makeText(getApplicationContext(), "No hay Internet ", Toast.LENGTH_SHORT).show();
            progressBar2.dismiss();*/
                        compr.text = "Error: Tiempo de  Respuesta Agotado, Reintentando"
                        Toast.makeText(applicationContext, "Error: Tiempo de  Respuesta Agotado, Reintentando", Toast.LENGTH_LONG).show()
                        proceso.progress = 0
                        startActivity(Intent(this@Splashscreen, Splashscreen::class.java))
                        finish()
                    })
                    stringRequest.setShouldCache(false)
                    queue.add(stringRequest)
                }, 3000)
                /*progressBar2= new ProgressDialog(Inicio.this);
                    progressBar2.setMessage();
                    progressBar2.show();
                    progressBar2.setCancelable(false);*/
            } else {
                if (datos.state.toString() == "CONNECTED") {
                    proceso.progress = 40
                    compr.text = "Conexion desde Internet Movil"
                    Handler().postDelayed({
                        proceso.progress = 75
                        compr.text = "Verificando Conexion a Internet Espere..."
                        val queue = Volley.newRequestQueue(this@Splashscreen)
                        val ping = "https://www.google.cl"
                        val stringRequest = StringRequest(Request.Method.GET, ping, Response.Listener {
                            proceso.progress = 100
                            compr.text = "Conectado al Internet"
                            Handler().postDelayed({
                                val i2 = Intent(this@Splashscreen, DHT11::class.java)
                                i2.putExtra("temperatura", a)
                                i2.putExtra("humedad", b)
                                startActivity(i2)
                                overridePendingTransition(R.anim.left_in, R.anim.left_out)
                                finish()
                            }, 2500)
                        }, Response.ErrorListener {
                            compr.text = "Error: Tiempo de  Respuesta Agotado, Reintentando"
                            Toast.makeText(applicationContext, "Error: Tiempo de  Respuesta Agotado, Reintentando", Toast.LENGTH_LONG).show()
                            proceso.progress = 0
                            startActivity(Intent(this@Splashscreen, Splashscreen::class.java))
                            finish()
                        })
                        stringRequest.setShouldCache(false)
                        queue.add(stringRequest)
                    }, 3000)
                } else {
                    Toast.makeText(applicationContext, "Error: No tienes Activado Ningun Modo de Conexion a Internet (Wifi, Internet Movil)", Toast.LENGTH_LONG).show()
                    finish()
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            }
        }, 3000)
    }


    fun EnviarRecibirDatos(URL: String) {

        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, URL, Response.Listener { response ->
            var response = response
            response = response.replace("][", ",")
            try {
                val ja = JSONArray(response)
                Log.i("sizejson", "" + ja.length())
                Cargar(ja)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener {
            a = "Actualizando..."
            b = "Actualizando..."
        })
        queue.add(stringRequest)
    }

    fun Cargar(ja: JSONArray) {

        var i = 0
        while (i < ja.length()) {

            try {
                a = ja.getString(i + 0) + "° C."
                b = ja.getString(i + 1) + " %"

            } catch (e: JSONException) {
                e.printStackTrace()
            }

            i += 2
        }
    }
}



