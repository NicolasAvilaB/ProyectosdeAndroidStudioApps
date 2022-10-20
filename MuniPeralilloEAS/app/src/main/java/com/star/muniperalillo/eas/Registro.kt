package com.star.muniperalillo.eas

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.Settings
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.florent37.viewtooltip.ViewTooltip
import es.dmoral.toasty.Toasty
import hari.bounceview.BounceView
import kotlinx.android.synthetic.main.activity_registro.*
import org.json.JSONArray
import org.json.JSONException

class Registro : AppCompatActivity(), DialogoConfirmacion.FinalizoCuadroDialogo {
        var listadia = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31")
        var ja: JSONArray? = null
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
            setContentView(R.layout.activity_registro)
            window.navigationBarColor = resources.getColor(R.color.Color_Medio_BlancoAzul)
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            delayedHide(300)
            mHideHandler.removeMessages(0)
            hideSystemUI()
            BounceView.addAnimTo(llamada1).setScaleForPushInAnim(1.0f, 1.0f).setScaleForPopOutAnim(1.2f, 1.2f).setPopOutAnimDuration(100)
            val adaptador_listadia = ArrayAdapter(this, android.R.layout.select_dialog_item, listadia)
            dia1.setThreshold(1)
            dia1.setAdapter(adaptador_listadia)
            mensaje_flotante_incorrecto = this
            mensaje_flotante_conf = this
            volver1.setOnClickListener {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                finish()
            }
            llamada1.setOnClickListener{
                when{
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED -> {
                        varseleccion = 1
                        val ms = DialogoConfirmacion(mensaje_flotante_conf, this@Registro)
                        ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                        ms.contenido_mensaje.setText(R.string.act_ptelefono) }
                    else -> {varseleccion = 2
                        val ms = DialogoConfirmacion(mensaje_flotante_conf, this@Registro)
                        ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                        ms.contenido_mensaje.setText(R.string.llamar_seguridad_ciudadana) }
                }
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
            val charactersForbiden2 = " !ΔΔ∆Δδ₲₡✓ÇüæęĘąǍǎČčĎďĚěǦǧȞȟǏǐǰǨǩĽľŇňǑǒŘřŠšṦṧŤťǓǔǙǚŽžǮǯĄłŁżŻĖČčėÆâôäöàòåûçùêÿëÖèÜï¢î£ì¥Ä₧Åƒ░▒▓│┤╡ª╢º╖¿╕⌐╣¬║½╗¼╝¡╜«╛»┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αßΓπΣσµτΦΘΩδ∞φε∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■ËÌÎÏÐÒÔÕÖ×ØÙÛÜÞßàâãäåæçèêëìîïðòôõö÷øùûüþÿ¯°±²³´µ¶·¸¹º»¼½¾¿ÀÂÃÄÅĒēĀāǢǣĪīŌōŪūǕǖȲȳÆÇÈÊ’“”•–—˜™š›œžŸ¡¢£¤¥¦§¨©ª«¬®{|}~Δ€‚ƒ„…†‡ˆ‰Š‹ŒŽ‘[\\]^`=>?@:;<\"#$%&'()*+,/"
            val filtro_nombre = InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    val type = Character.getType(source[i])
                    when {
                        source != null && charactersForbiden2.contains("" + source) || type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()-> return@InputFilter ""
                    }
                }
                null
            }
            val charactersForbiden = "!ćĆΔΔ∆Δδ₲₡✓ÇÉüæéęĘąǍǎČčĎďĚěǦǧȞȟǏǐ̌ǰǨǩĽľŇňǑǒŘřŠšṦṧŤťǓǔǙǚŽžǮǯĄłŁżŻĖńŃČčėÆâôäöàòåûçùêÿëÖèÜï¢î£ì¥Ä₧Åƒá░í▒ó▓ú│┤╡ª╢º╖¿╕⌐╣¬║½╗¼╝¡╜«╛»┐ └┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αßΓπΣσµτΦΘΩδ∞φε∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■ËÌÍÎÏÐÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðòóôõö÷øùúûüýþÿ¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅĒēĀāǢǣĪīŌōŪūǕǖȲȳÆÇÈÉÊ’“”•–—˜™š›œžŸ¡¢£¤¥¦§¨©ª«¬®{|}~Δ€‚ƒ„…†‡ˆ‰Š‹ŒŽ‘[\\]^_`=>?@:;<\"#$%&'()*+,-./"
            val filtro_clave = InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    when {
                        source != null && charactersForbiden.contains("" + source) || !Character.isLetterOrDigit(source[i]) -> return@InputFilter ""
                    }
                }
                null
            }
            val charactersForbiden55 = "0123456789!ΔΔ∆Δδ₲₡✓ÇüæęĘąǍǎČčĎďĚěǦǧȞȟǏǐ̌ǰǨǩĽľŇňǑǒŘřŠšṦṧŤťǓǔǙǚŽžǮǯĄłŁżŻĖČčėÆâôäöàòåûçùêÿëÖèÜï¢î£ì¥Ä₧Åƒ░▒▓│┤╡ª╢º╖¿╕⌐╣¬║½╗¼╝¡╜«╛»┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αßΓπΣσµτΦΘΩδ∞φε∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■ËÌÎÏÐÒÔÕÖ×ØÙÛÜÞßàâãäåæçèêëìîïðòôõö÷øùûüþÿ¯°±²³´µ¶·¸¹º»¼½¾¿ÀÂÃÄÅĒēĀāǢǣĪīŌōŪūǕǖȲȳÆÇÈÊ’“”•–—˜™š›œžŸ¡¢£¤¥¦§¨©ª«¬®{|}~Δ€‚ƒ„…†‡ˆ‰Š‹ŒŽ‘[\\]^`=>?@:;<\"#$%&'()*+,-._/"
            val filtro_nombre_completo = InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    val type = Character.getType(source[i])
                    when{
                        source != null && charactersForbiden55.contains("" + source) || type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt() -> return@InputFilter ""
                    }
                }
                null
            }
            val charactersForbiden3 = "!Δ∆ΔΔδ₲₡✓ÇüæęĘąǍǎČčĎďĚěǦǧȞȟǏǐ̌ǰǨǩĽľŇňǑǒŘřŠšṦṧŤťǓǔǙǚŽžǮǯĄłŁżŻĖČčėÆâôäöàòåûçùêÿëÖèÜï¢î£ì¥Ä₧Åƒ░▒▓│┤╡ª╢º╖¿╕⌐╣¬║½╗¼╝¡╜«╛»┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αßΓπΣσµτΦΘΩδ∞φε∩≡±≥≤⌠⌡÷≈∙·√ⁿ²■ËÌÎÏÐÒÔÕÖ×ØÙÛÜÞßàâãäåæçèêëìîïðòôõö÷øùûüþÿ¯±²³´µ¶·¸¹º»¼½¾¿ÀÂÃÄÅĒēĀāǢǣĪīŌōŪūǕǖȲȳÆÇÈÊ’“”•–—˜™š›œžŸ¡¢£¤¥¦§¨©ª«¬®{|}~Δ€‚ƒ„…†‡ˆ‰Š‹ŒŽ‘[\\]^_`=>?@:;<\"#$%&'()*+,/"
            val filtro_direccion = InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    val type = Character.getType(source[i])
                    when {
                        source != null && charactersForbiden3.contains("" + source) || type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt() -> return@InputFilter ""
                    }
                }
                null
            }
            val filtro_telefono = InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    when{
                        !Character.isDigit(source[i]) -> return@InputFilter ""
                    }
                }
                null
            }
            val charactersForbiden5 = "!ΔΔ∆Δδ₲₡✓ÇüæęĘąǍǎČčĎďĚěǦǧȞȟǏǐǰǨǩĽľŇňǑǒŘřŠšṦṧŤťǓǔǙǚŽžǮǯĄłŁżŻĖČčėÆâôäöàòåûçùêÿëÖèÜï¢î£ì¥Ä₧Åƒ░▒▓│┤╡ª╢º╖¿╕⌐╣¬║½╗¼╝¡╜«╛»┐ └┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αßΓπΣσµτΦΘΩδ∞φε∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■ËÌÎÏÐÒÔÕÖ×ØÙÛÜÞßàâãäåæçèêëìîïðòôõö÷øùûüþÿ¯°±²³´µ¶·¸¹º»¼½¾¿ÀÂÃÄÅĒēĀāǢǣĪīŌōŪūǕǖȲȳÆÇÈÊ’“”•–—˜™š›œžŸ¡¢£¤¥¦§¨©ª«¬®{|}~Δ€‚ƒ„…†‡ˆ‰Š‹ŒŽ‘[\\]^`=>?:;<\"#$%&'()*+,/"
            val filtro_correo = InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    val type = Character.getType(source[i])
                    when {
                        source != null && charactersForbiden5.contains("" + source) || type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt() -> return@InputFilter ""
                    }
                }
                null
            }
            val charactersForbiden6 = "ABCDEFGHIJKLMNOPKRSTUVWXYZabcdefghijklmnopkrstuvwxyz!ćĆΔΔ∆Δδ₲₡✓ÇÉüæéęĘąǍǎČčĎďĚěǦǧȞȟǏǐJ̌ǰǨǩĽľŇňǑǒŘřŠšṦṧŤťǓǔǙǚŽžǮǯĄłŁżŻĖńŃČčėÆâôäöàòåûçùêÿëÖèÜï¢î£ì¥Ä₧Åƒá░í▒ó▓ú│┤╡ª╢º╖¿╕⌐╣¬║½╗¼╝¡╜«╛»┐ └┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αßΓπΣσµτΦΘΩδ∞φε∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■ËÌÍÎÏÐÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðòóôõö÷øùúûüýþÿ¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅĒēĀāǢǣĪīŌōŪūǕǖȲȳÆÇÈÉÊ’“”•–—˜™š›œžŸ¡¢£¤¥¦§¨©ª«¬®{|}~Δ€‚ƒ„…†‡ˆ‰Š‹ŒŽ‘[\\]^_`=>?@:;<\"#$%&'()*+,-./"
            val filtro_mes = InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    val type = Character.getType(source[i])
                    when {
                        source != null && charactersForbiden6.contains("" + source) || type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt() -> return@InputFilter ""
                    }
                }
                null
            }
            rut1.setFilters(arrayOf(InputFilter.LengthFilter(12), InputFilter.AllCaps(), filtro_rut))
            nombre1.setFilters(arrayOf(InputFilter.LengthFilter(70), filtro_nombre))
            clave1.setFilters(arrayOf(InputFilter.LengthFilter(30), filtro_clave))
            nombre_completo.setFilters(arrayOf(InputFilter.LengthFilter(70), filtro_nombre_completo))
            direccion1.setFilters(arrayOf(InputFilter.LengthFilter(80), filtro_direccion))
            telefono1.setFilters(arrayOf(InputFilter.LengthFilter(8), filtro_telefono))
            correo1.setFilters(arrayOf(InputFilter.LengthFilter(60), filtro_correo))
            mes1.setFilters(arrayOf(InputFilter.LengthFilter(2), filtro_mes))
            rut1.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val length = rut1.getText().length
                    when (length){
                        0 -> rut1.setInputType(InputType.TYPE_CLASS_NUMBER)
                        2 -> {
                            rut1.setText(rut1.getText().toString() + ".")
                            rut1.setSelection(rut1.getText().length) }
                        6 -> {
                            rut1.setText(rut1.getText().toString() + ".")
                            rut1.setSelection(rut1.getText().length) }
                        10 -> {
                            rut1.setText(rut1.getText().toString() + "-")
                            rut1.setSelection(rut1.getText().length)
                            rut1.setInputType(InputType.TYPE_CLASS_TEXT) }
                    }
                }
                override fun afterTextChanged(s: Editable) {}
            })
            rut1.setOnClickListener { rut1.setSelection(0, rut1.getText().length) }
            ingresar1.setOnClickListener { campos_variables_proc() }
            pedir_telefono()
            dia1.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val dia12 = dia1.text.toString().toIntOrNull()
                    when {
                        dia12 != null -> {
                            when {
                                dia12 > 31 -> {
                                    dia1.setText("31")
                                    dia1.setSelection(dia1.getText().length)
                                }
                                dia12 == 0 -> {
                                    dia1.setText("1")
                                    dia1.setSelection(dia1.getText().length)
                                }
                            }
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
            mes1.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val mes12 = mes1.text.toString().toIntOrNull()
                    if (mes12 != null) {
                        if (mes12 > 12){
                            mes1.setText("12")
                            mes1.setSelection(mes1.getText().length)
                        }else if (mes12 == 0){
                            mes1.setText("1")
                            mes1.setSelection(mes1.getText().length)
                        }
                        when(mes12){
                            1 -> ViewTooltip.on(this@Registro, mes1).autoHide(true, 3000).corner(30).position(
                                ViewTooltip.Position.TOP).text("Enero").clickToHide(true).textColor(
                                Color.WHITE).color(Color.DKGRAY).arrowHeight(15).arrowWidth(15).distanceWithView(0).textSize(1, 20.0F).show()
                            2 -> ViewTooltip.on(this@Registro, mes1).autoHide(true, 3000).corner(30).position(
                                ViewTooltip.Position.TOP).text("Febrero").clickToHide(true).textColor(
                                Color.WHITE).color(Color.DKGRAY).arrowHeight(15).arrowWidth(15).distanceWithView(0).textSize(1, 20.0F).show()
                            3 -> ViewTooltip.on(this@Registro, mes1).autoHide(true, 3000).corner(30).position(
                                ViewTooltip.Position.TOP).text("Marzo").clickToHide(true).textColor(
                                Color.WHITE).color(Color.DKGRAY).arrowHeight(15).arrowWidth(15).distanceWithView(0).textSize(1, 20.0F).show()
                            4 -> ViewTooltip.on(this@Registro, mes1).autoHide(true, 3000).corner(30).position(
                                ViewTooltip.Position.TOP).text("Abril").clickToHide(true).textColor(
                                Color.WHITE).color(Color.DKGRAY).arrowHeight(15).arrowWidth(15).distanceWithView(0).textSize(1, 20.0F).show()
                            5 -> ViewTooltip.on(this@Registro, mes1).autoHide(true, 3000).corner(30).position(
                                ViewTooltip.Position.TOP).text("Mayo").clickToHide(true).textColor(Color.WHITE).color(
                                Color.DKGRAY).arrowHeight(15).arrowWidth(15).distanceWithView(0).textSize(1, 20.0F).show()
                            6 -> ViewTooltip.on(this@Registro, mes1).autoHide(true, 3000).corner(30).position(
                                ViewTooltip.Position.TOP).text("Junio").clickToHide(true).textColor(
                                Color.WHITE).color(Color.DKGRAY).arrowHeight(15).arrowWidth(15).distanceWithView(0).textSize(1, 20.0F).show()
                            7 -> ViewTooltip.on(this@Registro, mes1).autoHide(true, 3000).corner(30).position(
                                ViewTooltip.Position.TOP).text("Julio").clickToHide(true).textColor(
                                Color.WHITE).color(Color.DKGRAY).arrowHeight(15).arrowWidth(15).distanceWithView(0).textSize(1, 20.0F).show()
                            8 -> ViewTooltip.on(this@Registro, mes1).autoHide(true, 3000).corner(30).position(
                                ViewTooltip.Position.TOP).text("Agosto").clickToHide(true).textColor(
                                Color.WHITE).color(Color.DKGRAY).arrowHeight(15).arrowWidth(15).distanceWithView(0).textSize(1, 20.0F).show()
                            9 -> ViewTooltip.on(this@Registro, mes1).autoHide(true, 3000).corner(30).position(
                                ViewTooltip.Position.TOP).text("Septiembre").clickToHide(true).textColor(
                                Color.WHITE).color(Color.DKGRAY).arrowHeight(15).arrowWidth(15).distanceWithView(0).textSize(1, 20.0F).show()
                            10 -> ViewTooltip.on(this@Registro, mes1).autoHide(true, 3000).corner(30).position(
                                ViewTooltip.Position.TOP).text("Octubre").clickToHide(true).textColor(
                                Color.WHITE).color(Color.DKGRAY).arrowHeight(15).arrowWidth(15).distanceWithView(0).textSize(1, 20.0F).show()
                            11 -> ViewTooltip.on(this@Registro, mes1).autoHide(true, 3000).corner(30).position(
                                ViewTooltip.Position.TOP).text("Noviembre").clickToHide(true).textColor(
                                Color.WHITE).color(Color.DKGRAY).arrowHeight(15).arrowWidth(15).distanceWithView(0).textSize(1, 20.0F).show()
                            12 -> ViewTooltip.on(this@Registro, mes1).autoHide(true, 3000).corner(30).position(
                                ViewTooltip.Position.TOP).text("Diciembre").clickToHide(true).textColor(
                                Color.WHITE).color(Color.DKGRAY).arrowHeight(15).arrowWidth(15).distanceWithView(0).textSize(1, 20.0F).show()
                        }
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })
            ano1.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val ano12 = ano1.text.toString().toIntOrNull()
                    when{
                        ano12 != null -> {
                            when{
                                ano12 == 0 -> {
                                    ano1.setText("1920")
                                    ano1.setSelection(ano1.getText().length)
                                }
                            }
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
            rut1.setOnFocusChangeListener(object: View.OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    when{
                        !hasFocus -> {
                            rut1.setTextColor(resources.getColor(R.color.colorPrimary))
                            rut1.setHintTextColor(resources.getColor(R.color.plomo))
                        }
                        else -> {
                            rut1.setTextColor(resources.getColor(R.color.blancopuro))
                            rut1.setHintTextColor(resources.getColor(R.color.plomoblanco))
                        }
                    }
                    delayedHide(300)
                    mHideHandler.removeMessages(0)
                    hideSystemUI()
                }
            })
            nombre1.setOnFocusChangeListener(object: View.OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    when {
                        !hasFocus -> {
                            nombre1.setTextColor(resources.getColor(R.color.colorPrimary))
                            nombre1.setHintTextColor(resources.getColor(R.color.plomo))
                        }
                        else -> {
                            nombre1.setTextColor(resources.getColor(R.color.blancopuro))
                            nombre1.setHintTextColor(resources.getColor(R.color.plomoblanco))
                        }
                    }
                    delayedHide(300)
                    mHideHandler.removeMessages(0)
                    hideSystemUI()
                }
            })
            clave1.setOnFocusChangeListener(object: View.OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    when {
                        !hasFocus -> {
                            clave1.setTextColor(resources.getColor(R.color.colorPrimary))
                            clave1.setHintTextColor(resources.getColor(R.color.plomo))
                        }
                        else -> {
                            clave1.setTextColor(resources.getColor(R.color.blancopuro))
                            clave1.setHintTextColor(resources.getColor(R.color.plomoblanco))
                        }
                    }
                    delayedHide(300)
                    mHideHandler.removeMessages(0)
                    hideSystemUI()
                }
            })
            nombre_completo.setOnFocusChangeListener(object: View.OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    when {
                        !hasFocus -> {
                            nombre_completo.setTextColor(resources.getColor(R.color.colorPrimary))
                            nombre_completo.setHintTextColor(resources.getColor(R.color.plomo))
                        }
                        else -> {
                            nombre_completo.setTextColor(resources.getColor(R.color.blancopuro))
                            nombre_completo.setHintTextColor(resources.getColor(R.color.plomoblanco))

                        }
                    }
                    delayedHide(300)
                    mHideHandler.removeMessages(0)
                    hideSystemUI()
                }
            })
            direccion1.setOnFocusChangeListener(object: View.OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    when {
                        !hasFocus -> {
                            direccion1.setTextColor(resources.getColor(R.color.colorPrimary))
                            direccion1.setHintTextColor(resources.getColor(R.color.plomo))
                        }
                        else -> {
                            direccion1.setTextColor(resources.getColor(R.color.blancopuro))
                            direccion1.setHintTextColor(resources.getColor(R.color.plomoblanco))
                        }
                    }
                    delayedHide(300)
                    mHideHandler.removeMessages(0)
                    hideSystemUI()
                }
            })
            telefono1.setOnFocusChangeListener(object: View.OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    when {
                        !hasFocus -> {
                            telefono1.setTextColor(resources.getColor(R.color.colorPrimary))
                            telefono1.setHintTextColor(resources.getColor(R.color.plomo))
                        }
                        else -> {
                            telefono1.setTextColor(resources.getColor(R.color.blancopuro))
                            telefono1.setHintTextColor(resources.getColor(R.color.plomoblanco))
                        }
                    }
                    delayedHide(300)
                    mHideHandler.removeMessages(0)
                    hideSystemUI()
                }
            })
            correo1.setOnFocusChangeListener(object: View.OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    when {
                        !hasFocus -> {
                            correo1.setTextColor(resources.getColor(R.color.colorPrimary))
                            correo1.setHintTextColor(resources.getColor(R.color.plomo))
                        }
                        else -> {
                            correo1.setTextColor(resources.getColor(R.color.blancopuro))
                            correo1.setHintTextColor(resources.getColor(R.color.plomoblanco))
                        }
                    }
                    delayedHide(300)
                    mHideHandler.removeMessages(0)
                    hideSystemUI()
                }
            })
            dia1.setOnFocusChangeListener(object: View.OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    when {
                        !hasFocus -> {
                            dia1.setTextColor(resources.getColor(R.color.colorPrimary))
                            dia1.setHintTextColor(resources.getColor(R.color.plomo))
                        }
                        else -> {
                            dia1.setTextColor(resources.getColor(R.color.blancopuro))
                            dia1.setHintTextColor(resources.getColor(R.color.plomoblanco))
                        }
                    }
                    delayedHide(300)
                    mHideHandler.removeMessages(0)
                    hideSystemUI()
                }
            })
            mes1.setOnFocusChangeListener(object: View.OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    when {
                        !hasFocus -> {
                            mes1.setTextColor(resources.getColor(R.color.colorPrimary))
                            mes1.setHintTextColor(resources.getColor(R.color.plomo))
                        }
                        else -> {
                            mes1.setTextColor(resources.getColor(R.color.blancopuro))
                            mes1.setHintTextColor(resources.getColor(R.color.plomoblanco))
                        }
                    }
                    delayedHide(300)
                    mHideHandler.removeMessages(0)
                    hideSystemUI()
                }
            })
            ano1.setOnFocusChangeListener(object: View.OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    when {
                        !hasFocus -> {
                            ano1.setTextColor(resources.getColor(R.color.colorPrimary))
                            ano1.setHintTextColor(resources.getColor(R.color.plomo))
                        }
                        else -> {
                            ano1.setTextColor(resources.getColor(R.color.blancopuro))
                            ano1.setHintTextColor(resources.getColor(R.color.plomoblanco))
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

        fun buscar_nombre() {
            var registro = "https://systemchile.com/appmunicipal/login/comprobar_n_e.php?a=" + nombre1!!.text
            registro = registro.replace(" ", "%20")
            EnviarRecibirDatos_NOMBRE(registro)
        }

        fun buscar_correo() {
            var registro = "https://systemchile.com/appmunicipal/login/comprobar_n_e.php?a=" + correo1!!.text
            registro = registro.replace(" ", "%20")
            EnviarRecibirDatos_EMAIL(registro)
        }

        fun registro_perfil() {
            val valor_dia = dia1!!.text.toString().toInt()
            val valor_mes = mes1!!.text.toString().toInt()
            val valor_ano = ano1!!.text.toString().toInt()
            val concatenacion = "$valor_ano-$valor_mes-$valor_dia"
            var registro = "https://systemchile.com/appmunicipal/login/crear_perfil.php?a=" + rut1!!.text + "&b=" + nombre1!!.text + "&c=" + clave1!!.text + "&c2=" + nombre_completo!!.text + "&d=" + direccion1!!.text + "&e=9" + telefono1!!.text + "&f=" + correo1!!.text + "&g=" + concatenacion
            registro = registro.replace(" ", "%20")
            EnviarRecibirDatos(registro)
        }

        fun EnviarRecibirDatos(URL: String?) {
            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(Request.Method.GET, URL, Response.Listener { response -> var response = response
                response = response.replace("][", ",")
                when{
                    response.length > 0 -> {
                        try {
                            ja = JSONArray(response)
                            val valor_r = ja!!.getString(0)
                            when (valor_r){
                                "1" -> {
                                    Toasty.warning(applicationContext, "Su Rut ya está Registrado", Toasty.LENGTH_SHORT, true).show()
                                    progressDialog_carga!!.dismiss()
                                    rut1!!.requestFocus()
                                }
                                "0" -> {
                                    metodo_enviar_correo()
                                }
                            }
                        } catch (e: JSONException) {
                            Log.i("Error: ", e.toString() + "")
                        }
                    }
                }
            }, Response.ErrorListener { error -> Log.i("Mensaje Vollley Error: ", error.toString() + "")
                Handler().postDelayed({
                    Toasty.warning(applicationContext, "Consultando Datos, Espere un Momento...", Toasty.LENGTH_SHORT, true).show()
                    registro_perfil()
                }, 7000)
            })
            stringRequest.setShouldCache(false)
            queue.add(stringRequest)
        }

        fun EnviarRecibirDatos_NOMBRE(URL: String?) {
            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(Request.Method.GET, URL, Response.Listener { response -> var response = response
                response = response.replace("][", ",")
                when{
                    response.length > 0 -> {
                        Toasty.warning(applicationContext, "El Nombre de Usuario: " + nombre1!!.text + " ya se encuentra Registrado", Toasty.LENGTH_SHORT, true).show()
                        progressDialog_carga!!.dismiss()
                        nombre1!!.requestFocus()
                    }
                    else -> {
                        buscar_correo()
                    }
                }
            }, Response.ErrorListener { error -> Log.i("Mensaje Vollley Error: ", error.toString() + "")
                Handler().postDelayed({
                    Toasty.warning(applicationContext, "Consultando Datos, Espere un Momento...", Toasty.LENGTH_SHORT, true).show()
                    buscar_nombre()
                }, 7000)
            })
            stringRequest.setShouldCache(false)
            queue.add(stringRequest)
        }

        fun EnviarRecibirDatos_EMAIL(URL: String?) {
            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(Request.Method.GET, URL, Response.Listener { response -> var response = response
                response = response.replace("][", ",")
                when{
                    response.length > 0 -> {
                        Toasty.warning(applicationContext, "El Correo: " + correo1!!.text + " ya se encuentra Registrado", Toasty.LENGTH_SHORT, true).show()
                        progressDialog_carga!!.dismiss()
                        correo1!!.requestFocus()
                    }
                    else -> {
                        registro_perfil()
                    }
                }
            }, Response.ErrorListener { error ->
                Log.i("Mensaje Vollley Error: ", error.toString() + "")
                Handler().postDelayed({
                    Toasty.warning(applicationContext, "Consultando Datos, Espere un Momento...", Toasty.LENGTH_SHORT, true).show()
                    buscar_correo()
                }, 7000)
            })
            stringRequest.setShouldCache(false)
            queue.add(stringRequest)
        }

        fun metodo_enviar_correo() {
            var registro = "https://systemchile.com/appmunicipal/login/mail.php?a=" + correo1!!.text + "&b=" + nombre1!!.text + "&c=" + clave1!!.text
            registro = registro.replace(" ", "%20")
            Enviar_Correo(registro)
        }

        fun Enviar_Correo(URL: String?) {
            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(Request.Method.GET, URL, Response.Listener {
                Toasty.success(applicationContext, "Perfil Creado Exitosamente", Toasty.LENGTH_SHORT, true).show()
                progressDialog_carga!!.dismiss()
                val i = Intent(applicationContext, Login::class.java)
                startActivity(i)
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                finish()
            }, Response.ErrorListener { error -> Log.i("Mensaje Vollley Error: ", error.toString() + "")
                Handler().postDelayed({
                    Toasty.warning(applicationContext, "Consultando Datos, Espere un Momento...", Toasty.LENGTH_SHORT, true).show()
                    metodo_enviar_correo()
                }, 7000)
            })
            stringRequest.setShouldCache(false)
            queue.add(stringRequest)
        }

        fun campos_variables_proc() {
            val valor_rut = rut1!!.text.toString()
            val valor_nombre = nombre1!!.text.toString()
            val valor_clave = clave1!!.text.toString()
            val valor_nombre_completo = nombre_completo!!.text.toString()
            val valor_direccion = direccion1!!.text.toString()
            val valor_telefono = telefono1!!.text.toString()
            val valor_correo = correo1!!.text.toString()
            val valor_dia = dia1!!.text.toString()
            val valor_mes = mes1!!.text.toString()
            val valor_ano = ano1!!.text.toString()
            val length_rut = rut1!!.text.length
            val length_telefono = telefono1!!.text.length
            when{
                valor_rut == "" || length_rut < 12 ->{
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.rut_error)
                    rut1!!.requestFocus()
                }
                !validarRut(rut1!!.text.toString()) ->{
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.rut_error_valido)
                    rut1!!.requestFocus()
                }
                valor_nombre == "" ->{
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.nombre_c_error)
                    nombre1!!.requestFocus()
                }
                valor_clave == "" ->{
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.clave_c_error)
                    clave1!!.requestFocus()
                }
                valor_nombre_completo == "" ->{
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.nombre_compl_error)
                    nombre_completo!!.requestFocus()
                }
                valor_direccion == "" ->{
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.direccion_error)
                    direccion1!!.requestFocus()
                }
                valor_telefono == "" || length_telefono < 8 ->{
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.telefono_error)
                    telefono1!!.requestFocus()
                }
                valor_correo == "" ->{
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.email_error)
                    correo1!!.requestFocus()
                }
                !validarEmail(correo1!!.text.toString()) ->{
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.correo_no_válido_error)
                    correo1!!.requestFocus()
                }
                valor_dia == "" ->{
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.dia_error)
                    dia1!!.requestFocus()
                }
                valor_mes == "" ->{
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.mes_error)
                    mes1!!.requestFocus()
                }
                valor_ano == "" ->{
                    val ms = DialogoIncorrecto(mensaje_flotante_incorrecto)
                    ms.contenido_mensaje.findViewById<View>(R.id.contenido_mensaje)
                    ms.contenido_mensaje.setText(R.string.ano_error)
                    ano1!!.requestFocus()
                }
                else ->{
                    progressDialog_carga = ProgressDialog(this@Registro)
                    progressDialog_carga!!.dismiss()
                    progressDialog_carga!!.setTitle("Registrando Perfil")
                    progressDialog_carga!!.setMessage("Espere un Momento...")
                    progressDialog_carga!!.setCancelable(false)
                    progressDialog_carga!!.show()
                    buscar_nombre()
                }
            }
        }

        private fun validarEmail(email: String): Boolean {
            val pattern = Patterns.EMAIL_ADDRESS
            return pattern.matcher(email).matches()
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

        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            when (requestCode == REQUEST_ACCESS_FINE) {
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
                        2 -> {
                            Toasty.info(applicationContext, "Seguridad Ciudadana: \u000A Llamando...", Toasty.LENGTH_SHORT, true).show()
                            val callIntent = Intent(Intent.ACTION_CALL)
                            callIntent.data = Uri.parse("tel:+56 9 7623 2889")
                            startActivity(callIntent)
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                        }
                    }
                }
                false -> {
                    System.out.println("Vacio")
                    varseleccion = 0 }
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
