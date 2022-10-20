package com.star.muniperalillo.eas

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import es.dmoral.toasty.Toasty
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.util.*

class AyudaFragment : Fragment(){
    var lista_sectores = arrayOf("Calleuque - San Miguel De Calleuque", "Calleuque - Santa Blanca", "Calleuque - La Viroca", "Calleuque - Santa Victoria", "Los Cardos - Patria Nueva", "Los Cardos - El Barco", "Los Cardos - 21 de Mayo", "Rinconada de Molineros - Los Mayos", "Rinconada de Molineros - Rincón los Caceres", "Rinconada de Molineros - Rinconada de Molineros", "Mata Redonda - Molineros", "Puquillay - Puquillay", "Población - San Isidro La Bomba", "Población - Población", "Santa Ana - Santa Ana", "El Cortijo - El Cortijo", "Rinconada De Peralillo - Rinconada De Peralillo", "El Olivar - El Olivar", "Troya Sur - Troya Sur", "Parrones - Parrones", "Peralillo - Troya Centro", "Peralillo - Troya Norte", "Peralillo - Peralillo Centro", "Población - Calle Arturo Prat", "Peralillo - Peralillo", "Calleuque - Calleuque", "Los Cardos - Los Cardos")
    var root: View? = null
    var rut0: EditText? = null
    var descr: EditText? = null
    var sector: AutoCompleteTextView? = null
    var enviar_informacion: Button? = null
    var preferences: SharedPreferences? = null
    var buscador_alert: Spinner? = null
    var mensaje_flotante_incorrecto: Context? = null
    var progressDialog_carga: ProgressDialog? = null
    var latitud: String? = null
    var longitud = "----------"
    var direccion = "----------"
    var varseleccion = 0
    var alert: TextView? = null
    var ja: JSONArray? = null
    var mensaje_flotante_conf: Context? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_alertas, container, false)
        mensaje_flotante_conf = context
        buscador_alert = root!!.findViewById(R.id.buscador_alert)
        rut0 = root!!.findViewById(R.id.rut0)
        descr = root!!.findViewById(R.id.descr)
        alert = root!!.findViewById(R.id.alert)
        sector = root!!.findViewById(R.id.sector)
        enviar_informacion = root!!.findViewById(R.id.enviar_informacion)
        val adaptador_listasectores = ArrayAdapter(context!!, android.R.layout.simple_dropdown_item_1line, lista_sectores)
        sector!!.setThreshold(1)
        sector!!.setAdapter(adaptador_listasectores)
        mensaje_flotante_incorrecto = context
        spinner_alerta()
        enviar_informacion!!.setOnClickListener {
            val valor_rut = rut0!!.text.toString()
            val length_rut = rut0!!.text.length
            val valor_descripcion = descr!!.getText().toString()
            val valor_sector = sector!!.getText().toString()
            val valor_alerta = buscador_alert!!.getItemAtPosition(buscador_alert!!.getSelectedItemPosition()).toString()
            when{
                valor_rut == "" || length_rut < 12 ->{
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.rut_error)
                    rut0!!.requestFocus()
                }
                !validarRut(rut0!!.text.toString()) ->{
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.rut_error_valido)
                    rut0!!.requestFocus()
                }
                valor_descripcion == "" -> {
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.descri_error)
                    descr!!.requestFocus()
                }
                valor_sector == "" -> {
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.sect_error)
                    sector!!.requestFocus()
                }
                valor_alerta == "Seleccione una Ayuda..." -> {
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.selecione_error)
                    buscador_alert!!.requestFocus()
                }
                else -> {
                    progressDialog_carga = ProgressDialog(context)
                    progressDialog_carga!!.dismiss()
                    progressDialog_carga!!.setTitle("Ingresando Ayuda")
                    progressDialog_carga!!.setMessage("Espere un Momento...")
                    progressDialog_carga!!.setCancelable(false)
                    progressDialog_carga!!.show()
                    varseleccion = 1
                    locationStart()
                }
            }
        }
        val charactersForbiden0 = "!._0123456789@ΔΔ∆Δδ₲₡✓ÇüæęĘąǍǎČčĎďĚěǦǧȞȟǏǐ̌ǰǨǩĽľŇňǑǒŘřŠšṦṧŤťǓǔǙǚŽžǮǯĄłŁżŻĖČčėÆâôäöàòåûçùêÿëÖèÜï¢î£ì¥Ä₧Åƒ░▒▓│┤╡ª╢º╖¿╕⌐╣¬║½╗¼╝¡╜«╛»┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αßΓπΣσµτΦΘΩδ∞φε∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■ËÌÎÏÐÒÔÕÖ×ØÙÛÜÞßàâãäåæçèêëìîïðòôõö÷øùûüþÿ¯°±²³´µ¶·¸¹º»¼½¾¿ÀÂÃÄÅĒēĀāǢǣĪīŌōŪūǕǖȲȳÆÇÈÊ’“”•–—˜™š›œžŸ¡¢£¤¥¦§¨©ª«¬®{|}~Δ€‚ƒ„…†‡ˆ‰Š‹ŒŽ‘[\\]^`=>?:;<\"#$%&'()*+,/"
        val filtro_descripcion = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                val type = Character.getType(source[i])
                when{
                    charactersForbiden0.contains("" + source) || type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt() -> return@InputFilter ""
                }
            }
            null
        }
        val charactersForbiden1 = "!ΔΔ∆Δδ₲₡✓ÇüæęĘąǍǎČčĎďĚěǦǧȞȟǏǐ̌ǰǨǩĽľŇňǑǒŘřŠšṦṧŤťǓǔǙǚŽžǮǯĄłŁżŻĖČčėÆâôäöàòåûçùêÿëÖèÜï¢î£ì¥Ä₧Åƒ░▒▓│┤╡ª╢º╖¿╕⌐╣¬║½╗¼╝¡╜«╛»┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αßΓπΣσµτΦΘΩδ∞φε∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■ËÌÎÏÐÒÔÕÖ×ØÙÛÜÞßàâãäåæçèêëìîïðòôõö÷øùûüþÿ¯°±²³´µ¶·¸¹º»¼½¾¿ÀÂÃÄÅĒēĀāǢǣĪīŌōŪūǕǖȲȳÆÇÈÊ’“”•–—˜™š›œžŸ¡¢£¤¥¦§¨©ª«¬®{|}~Δ€‚ƒ„…†‡ˆ‰Š‹ŒŽ‘[\\]^_`=>?@:;<\"#$%&'()*+,./"
        val filtro_sector = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                when {
                    source != null && charactersForbiden1.contains("" + source) && !Character.isSpaceChar(source[i]) -> return@InputFilter ""
                }
            }
            null
        }
        val charactersForbiden4 = "a∆bcdefghijklmnopqrstuvwxyzñÑABCDEFGHIJLMNOPQRSTUVWXYZ!ΔΔΔδ₲₡✓ÇüæęĘąǍǎČčĎďĚěǦǧȞȟǏǐJ̌ǰǨǩĽľŇňǑǒŘřŠšṦṧŤťǓǔǙǚŽžǮǯĄłŁżŻĖČčėÆâôäöàòåûçùêÿëÖèÜï¢î£ì¥Ä₧Åƒ░▒▓│┤╡ª╢º╖¿╕⌐╣¬║½╗¼╝¡╜«╛»┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αßΓπΣσµτΦΘΩδ∞φε∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■ËÌÎÏÐÒÔÕÖ×ØÙÛÜÞßàâãäåæçèêëìîïðòôõö÷øùûüþÿ¯°±²³´µ¶·¸¹º»¼½¾¿ÀÂÃÄÅĒēĀāǢǣĪīŌōŪūǕǖȲȳÆÇÈÊ’“”•–—˜™š›œžŸ¡¢£¤¥¦§¨©ª«¬®{|}~Δ€‚ƒ„…†‡ˆ‰Š‹ŒŽ‘[\\]^_`=>?@:;<\"#$%&'()*+,/"
        val filtro_rut = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                val type = Character.getType(source[i])
                when {
                    source != null && charactersForbiden4.contains("" + source) || type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt() -> return@InputFilter ""
                }
            }
            null
        }
        rut0!!.setFilters(arrayOf(InputFilter.LengthFilter(12), InputFilter.AllCaps(), filtro_rut))
        descr!!.setFilters(arrayOf(InputFilter.LengthFilter(70), filtro_descripcion))
        sector!!.setFilters(arrayOf(InputFilter.LengthFilter(70), filtro_sector))
        descr!!.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                when {
                    !hasFocus -> {
                        descr!!.setTextColor(resources.getColor(R.color.blancopuro))
                        descr!!.setHintTextColor(resources.getColor(R.color.blancopuro))
                    }
                    else -> {
                        descr!!.setTextColor(resources.getColor(R.color.blancopuro))
                        descr!!.setHintTextColor(resources.getColor(R.color.blancopuro))
                    }
                }
            }
        })
        sector!!.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                when{
                    !hasFocus -> {
                        sector!!.setTextColor(resources.getColor(R.color.blancopuro))
                        sector!!.setHintTextColor(resources.getColor(R.color.blancopuro))
                    }
                    else -> {
                        sector!!.setTextColor(resources.getColor(R.color.blancopuro))
                        sector!!.setHintTextColor(resources.getColor(R.color.blancopuro))
                    }
                }
            }
        })
        rut0!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val length = rut0!!.getText().length
                when (length){
                    0 -> rut0!!.setInputType(InputType.TYPE_CLASS_NUMBER)
                    2 -> {
                        rut0!!.setText(rut0!!.getText().toString() + ".")
                        rut0!!.setSelection(rut0!!.getText().length) }
                    6 -> {
                        rut0!!.setText(rut0!!.getText().toString() + ".")
                        rut0!!.setSelection(rut0!!.getText().length) }
                    10 -> {
                        rut0!!.setText(rut0!!.getText().toString() + "-")
                        rut0!!.setSelection(rut0!!.getText().length)
                        rut0!!.setInputType(InputType.TYPE_CLASS_TEXT) }
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
        rut0!!.setOnClickListener { rut0!!.setSelection(0, rut0!!.getText().length) }
        /*buscador_alert!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                try{
                    val valor_alerta = buscador_alert!!.getItemAtPosition(buscador_alert!!.getSelectedItemPosition()).toString()
                    when (valor_alerta) {
                        "Seleccione una Alerta..." -> alert!!.setText(R.string.seleccionar_alerta)
                        "Basura / Escombros" -> alert!!.setText(R.string.b_sel)
                        "Calle / Vereda en Mal Estado" -> alert!!.setText(R.string.c_sel)
                        "Infraestructura Urbana en Mal Estado" -> alert!!.setText(R.string.d_sel)
                        "Luminaria en Mal Estado" -> alert!!.setText(R.string.e_sel)
                        "Poda por Obstrucción" -> alert!!.setText(R.string.f_sel)
                        "Juegos Infantiles y Maquinas de Ejercicios" -> alert!!.setText(R.string.g_sel)
                    }
                }catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                alert!!.setText(R.string.seleccionar_alerta)
            }
        })*/
        return root
    }

    fun validarRut(rut: String): Boolean {
        var rut = rut
        var validacion = false
        try {
            rut = rut.toUpperCase()
            rut = rut.replace(".", "")
            rut = rut.replace("-", "")
            var rutAux = rut.substring(0, rut.length - 1).toInt()
            val dv = rut[rut.length - 1]
            var m = 0
            var s = 1
            while (rutAux != 0) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11
                rutAux /= 10
            }
            if (dv == (if (s != 0) s + 47 else 75).toChar()) {
                validacion = true
            }
        } catch (e: NumberFormatException) {
        } catch (e: Exception) {
        }
        return validacion
    }
    fun spinner_alerta() {
        val datostipodocumento = arrayOf("Seleccione una Ayuda...", "Caja de Alimentación Municipal")
        val adapter = ArrayAdapter(activity!!, R.layout.spinner_alerta_item, datostipodocumento)
        buscador_alert!!.adapter = adapter
    }

    fun limpiar_residuo() {
        buscador_alert!!.setSelection(0)
        rut0!!.setText("")
        descr!!.setText("")
        sector!!.setText("")
        rut0!!.requestFocus()
    }

    private fun locationStart() {
        val Local = Localizacion()
        Local.mainActivity = this
        val mlocManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        when{!gpsEnabled -> { val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(settingsIntent) }
            ActivityCompat.checkSelfPermission(context!! as Activity, Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context!! as Activity, Manifest.permission.ACCESS_COARSE_LOCATION) !== PackageManager.PERMISSION_GRANTED ->{
                ActivityCompat.requestPermissions(context!! as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1000)
                return
            }
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, Local as LocationListener)
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, Local as LocationListener)
    }

    fun setLocation(loc: Location) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        when{ loc.latitude != 0.0 && loc.longitude != 0.0 -> {
            try { val geocoder = Geocoder(activity!!, Locale.getDefault())
                val list = geocoder.getFromLocation(loc.latitude, loc.longitude, 1)
                when { !list.isEmpty() -> {
                    val DirCalle = list[0]
                    direccion = (DirCalle.getAddressLine(0))
                }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        }
    }

    /* Aqui empieza la Clase Localizacion */
    inner class Localizacion : LocationListener {
        var mainActivity: AyudaFragment? = null

        override fun onLocationChanged(loc: Location) {
            when (varseleccion) {
                1 -> {
                    // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
                    // debido a la deteccion de un cambio de ubicacion
                    loc.latitude
                    loc.longitude
                    val sLatitud = loc.latitude.toString()
                    val sLongitud = loc.longitude.toString()
                    latitud = sLatitud
                    longitud = sLongitud
                    when{ latitud != null -> latitud = sLatitud }
                    try {
                        mainActivity?.setLocation(loc)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    preferences = activity!!.getSharedPreferences("credenciales", Context.MODE_PRIVATE)
                    val valor_rut = preferences!!.getString("valor_rut", "")
                    val login_perfil = preferences!!.getString("login_perfil", "")
                    var registro = "https://systemchile.com/appmunicipal/eas/crearayuda.php?a=$valor_rut&b=$login_perfil&c="+rut0!!.text+"&d="+descr!!.text+"&e="+sector!!.text+"&f="+ buscador_alert!!.getItemAtPosition(buscador_alert!!.selectedItemPosition).toString() +"&g="+ latitud +"&h="+ longitud
                    registro = registro.replace(" ", "%20")
                    varseleccion = 0
                    EnviarDatos(registro)
                }
            }
        }

        override fun onProviderDisabled(provider: String) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
            ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
            ms.contenido_mensaje.setText(R.string.activargps)
            //latitud.setText("GPS Desactivado")
        }

        override fun onProviderEnabled(provider: String) {
            // Este metodo se ejecuta cuando el GPS es activado
            //latitud.setText("GPS Activado")
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            when (status) {
                LocationProvider.AVAILABLE -> Log.d("debug", "LocationProvider.AVAILABLE")
                LocationProvider.OUT_OF_SERVICE -> Log.d("debug", "LocationProvider.OUT_OF_SERVICE")
                LocationProvider.TEMPORARILY_UNAVAILABLE -> Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE")
            }
        }
    }

    fun EnviarDatos(URL: String?) {
        val queue = Volley.newRequestQueue(activity)
        val stringRequest = StringRequest(Request.Method.GET, URL, Response.Listener { response ->
            var response = response
            response = response.replace("][", ",")
            when{
                response.length > 0 ->{
                    try {
                        ja = JSONArray(response)
                        val valor_r = ja!!.getString(0)
                        when (valor_r){
                            "1" -> {
                                Toasty.warning(activity!!, "El Rut del Beneficiario ya está Registrado", Toasty.LENGTH_SHORT, true).show()
                                progressDialog_carga!!.dismiss()
                                varseleccion = 0
                                rut0!!.requestFocus()
                            }
                            "0" -> {
                                Toasty.success(activity!!, "Datos Enviados Exitosamente", Toasty.LENGTH_SHORT, true).show()
                                progressDialog_carga!!.dismiss()
                                varseleccion = 0
                                limpiar_residuo()
                            }
                        }
                    } catch (e: JSONException) {
                        Log.i("Error: ", e.toString() + "")
                    }
                }
            }
        }, Response.ErrorListener { error -> Log.i("Mensaje Vollley Error: ", error.toString() + "")
            Handler().postDelayed({
                locationStart()
            }, 5000)
        })
        stringRequest.setShouldCache(false)
        queue.add(stringRequest)
    }
}