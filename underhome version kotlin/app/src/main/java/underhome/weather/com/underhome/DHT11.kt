package underhome.weather.com.underhome

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_dht11.*
import kotlinx.android.synthetic.main.activity_splashscreen.*
import kotlinx.android.synthetic.main.app_bar_dht11.*
import kotlinx.android.synthetic.main.content_dht11.*
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class DHT11 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dht11)
        setSupportActionBar(toolbar)

        if (Build.VERSION.SDK_INT >= 19) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
        title = "Sensor DHT11"
        val intent = intent
        val extras = intent.extras
        val datotemperatura = extras!!.get("temperatura") as String
        val datohumedad = extras.get("humedad") as String
        temp.setText(datotemperatura)
        hum.setText(datohumedad)

        val consulta = "http://systemchile.com/sensorDHT11/mostrar.php"
        EnviarRecibirDatos(consulta)

        val handler = Handler()
        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                handler.post {
                    try {
                        val consulta = "http://systemchile.com/sensorDHT11/mostrar.php"
                        EnviarRecibirDatos(consulta)
                    } catch (e: Exception) {
                        Log.e("error", e.message)
                    }
                }
            }
        }

        timer.schedule(task, 0, 1000)

        actualizar.setOnClickListener(View.OnClickListener {
            val consulta = "http://systemchile.com/sensorDHT11/mostrar.php"
            EnviarRecibirDatos2(consulta)
        })


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            //super.onBackPressed()
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dht11, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*when (item.itemId) {
            R.id.actiom -> return true
            else -> return super.onOptionsItemSelected(item)
        }*/
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.sensordht11 -> {
                // Handle the camera action
            }
            R.id.sensords18b20 -> {

            }
            R.id.salir -> {

                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun EnviarRecibirDatos2(URL: String) {
        val progressBar2 = ProgressDialog(this@DHT11)
        progressBar2.setTitle("Actualizar Datos")
        progressBar2.setMessage("Espere un Momento...")
        progressBar2.show()
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, URL, Response.Listener { response ->
            var response = response

            response = response.replace("][", ",")
            if (response.length > 0) {
                try {
                    val ja = JSONArray(response)
                    Log.i("sizejson", "" + ja.length())
                    progressBar2.dismiss()
                    Cargar(ja)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            } else {
                temp.text = "Actualizando..."
                hum.text = "Actualizando..."
            }
        }, Response.ErrorListener { })
        queue.add(stringRequest)

    }


    fun EnviarRecibirDatos(URL: String) {

        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, URL, Response.Listener { response ->
            var response = response

            response = response.replace("][", ",")
            //if (response.length()>0){
            try {
                val ja = JSONArray(response)
                Log.i("sizejson", "" + ja.length())
                Cargar(ja)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            //}
            /*else {
            temp.setText("Esperando Datos...");
            hum.setText("Esperando Datos...");
        }*/
        }, Response.ErrorListener { })
        queue.add(stringRequest)

    }

    fun Cargar(ja: JSONArray) {

        var i = 0
        while (i < ja.length()) {

            try {
                temp.text = "" + ja.getString(i + 0) + "° C."
                hum.text = "" + ja.getString(i + 1) + " %"
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            i += 2

        }

    }

}
