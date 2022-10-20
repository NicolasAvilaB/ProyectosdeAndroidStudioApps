package com.ismaeldivita.chipnavigation.sample

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class HorizontalModeActivity : AppCompatActivity(), OnFragmentActionsListener {

    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerViewAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private val listadata: MutableList<Data> = ArrayList<Data>()
    var tarjeta: CardView? = null
    private val container by lazy { findViewById<View>(R.id.container) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu) }
    var selectedFragmenst: Fragment = Fragment()

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN ; View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        actionBar?.hide()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizontal)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN ; View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        actionBar?.hide()
        recyclerView = findViewById(R.id.recycler)
        tarjeta = findViewById(R.id.tarjeta)


        menu.setOnItemSelectedListener { id ->
            val option = when (id) {
                R.id.home -> selectedFragmenst = HomeFragment()
                R.id.activity -> selectedFragmenst =  ActividadesFragment()
                R.id.favorites -> selectedFragmenst = FavoritesFragment()
                R.id.settings -> selectedFragmenst = SettingsFragment()
                else -> R.color.white to ""
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragmenst).commit()

        }


        if (savedInstanceState == null) {
            menu.showBadge(R.id.home)
            menu.showBadge(R.id.settings, 32)
        }
        menu.setIt
        menu.setItemSelected(R.id.home)
    }

    override fun MensajeBoton() {
        Toast.makeText(this,"El boton esta pulsado", Toast.LENGTH_SHORT).show()
        var registro = "https://systemchile.com/MuniSocial/mostrarAdmin.php"
        registro = registro.replace(" ", "%20")
        EnviarRecibirDatos(registro)
    }

    fun EnviarRecibirDatos(URL: String?) {
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, URL, Response.Listener { response ->
            var response = response
            response = response.replace("][", ",")
            if (response.length > 0) {
                try {
                    val ja = JSONArray(response)
                    Log.i("sizejson", "" + ja.length())
                    CargarDatos(ja)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }, Response.ErrorListener { })
        stringRequest.setShouldCache(false)
        queue.add(stringRequest)
    }

    fun CargarDatos(ja: JSONArray) {
        listadata.clear()
        ejecutar_recyclerview()
        var i = 0
        while (i < ja.length()) {
            try {
                listadata.add(Data("" + ja.getString(i), "" + ja.getString(i + 1), "" + ja.getString(i + 2), "" + ja.getString(i + 3)))
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            i += 4
        }
    }

    fun ejecutar_recyclerview() {
        recyclerView?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this@HorizontalModeActivity)
        recyclerView?.setLayoutManager(layoutManager)
        adapter = RecyclerViewAdapter(listadata)
        recyclerView?.setAdapter(adapter)
    }

}
