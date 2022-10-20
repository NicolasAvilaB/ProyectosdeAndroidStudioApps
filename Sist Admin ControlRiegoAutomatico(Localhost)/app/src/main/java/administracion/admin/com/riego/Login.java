package administracion.admin.com.riego;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import underhome.weather.com.underhome.R;

public class Login extends AppCompatActivity {
    Session session;
    Button aceptar, salir, recuperar_contra;
    EditText nombre, clave;
    Snackbar mostrarsnack;
    TextInputLayout til_clave, til_nombre;
    JSONArray ja;
    ProgressDialog progressBarbuscar;
    //Snackbar mostrarmensaje;
    Context mensaje_flotante_incorrecto;
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        til_clave = findViewById(R.id.til_clave);
        til_nombre = findViewById(R.id.til_nombre);
        recuperar_contra = findViewById(R.id.recuperar_contra);

        mensaje_flotante_incorrecto = this;
        aceptar = findViewById(R.id.aceptar);
        salir = findViewById(R.id.salir);
        nombre = findViewById(R.id.nombre);
        nombre.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                nombre.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nombre.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void afterTextChanged(Editable s) {
                nombre.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }
        });
        clave = findViewById(R.id.clave);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombre.getText().length() == 0 && clave.getText().length() == 0){
                    til_nombre.setError("Advertencia: Campo Nombre Vacio");
                    til_clave.setError("Advertencia: Campo Clave Vacio");
                }
                else if (nombre.getText().length() == 0)
                {
                    til_nombre.setError("Advertencia: Campo Nombre Vacio");
                }
                else if (clave.getText().length() == 0){
                    til_nombre.setError(null);
                    til_clave.setError("Advertencia: Campo Clave Vacio");
                }
                else {
                    Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
                    if (!patron.matcher(nombre.getText()).matches() || nombre.getText().length() > 45) {
                        til_nombre.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
                    } else {
                        Pattern patron2 = Pattern.compile("^[a-zA-Z1-9]+$");
                        if(!patron2.matcher(clave.getText()).matches() || clave.getText().length() > 45){
                            til_nombre.setError(null);
                            til_clave.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
                        }
                        else{
                            til_nombre.setError(null);
                            til_clave.setError(null);
                            progressBarbuscar= new ProgressDialog(Login.this);
                            progressBarbuscar.dismiss();
                            progressBarbuscar.setTitle("Consultando Usuario");
                            progressBarbuscar.setMessage("Espere un Momento...");
                            progressBarbuscar.setCancelable(false);
                            progressBarbuscar.show();
                            consultalogin();
                        }
                    }
                }
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recuperar_contra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
                if (nombre.length() == 0){
                    til_clave.setError(null);
                    til_nombre.setError("Primero digite el usuario, luego envie el correo electronico");
                }
                else if (!patron.matcher(nombre.getText()).matches() || nombre.getText().length() > 45)
                {
                    til_clave.setError(null);
                    til_nombre.setError("No se permite caracteres especiales ni sobrexederse de 45 palabras");
                }
                else{
                    til_nombre.setError(null);
                    progressBarbuscar= new ProgressDialog(Login.this);
                    progressBarbuscar.dismiss();
                    progressBarbuscar.setTitle("Enviando Contraseña a Correo");
                    progressBarbuscar.setMessage("Espere un Momento...");
                    progressBarbuscar.setCancelable(false);
                    progressBarbuscar.show();
                    String registro = "http://192.168.43.250/sistemariego_app_admin/buscar_correo_admin.php?nx=" + nombre.getText();
                    registro = registro.replace(" ","%20");
                    EnviarRecibir_CorreoClave(registro);
                }
            }
        });
    }

    public void EnviarRecibirDatos(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ja = new JSONArray(response);
                    String nombres = ja.getString(0);
                    String claves = ja.getString(1);
                    if (nombres.equals(nombre.getText().toString()) && claves.equals(clave.getText().toString())){
                        progressBarbuscar.dismiss();
                        Intent i = new Intent(getApplicationContext(), DHT11.class);
                        startActivity(i);
                        nombre.setText("");
                        nombre.invalidate();
                        clave.setText("");
                        clave.invalidate();
                    } else{
                        progressBarbuscar.dismiss();
                        new DialogoIncorrecto(mensaje_flotante_incorrecto);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    progressBarbuscar.dismiss();
                    new DialogoIncorrecto(mensaje_flotante_incorrecto);
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                consultalogin();
            }
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    public void consultalogin(){
        String registro = "http://192.168.43.250/sistemariego_app_admin/login.php?nx=" + nombre.getText() + "&cx=" +clave.getText();
        registro = registro.replace(" ","%20");
        EnviarRecibirDatos(registro);
    }

    public void EnviarRecibir_CorreoClave(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                if (response.length()>0) {
                    try {
                        ja = new JSONArray(response);
                        String clave = ja.getString(0);
                        String correo = ja.getString(1);
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        Properties properties = new Properties();
                        properties.put("mail.smtp.host", "smtp.googlemail.com");
                        properties.put("mail.smtp.socketFactory.port", "465");
                        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                        properties.put("mail.smtp.auth", "true");
                        properties.put("mail.smtp.port", "465");

                        try {
                            session = Session.getDefaultInstance(properties, new Authenticator() {
                                @Override
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication("sistemacontrolriegoautomatico@gmail.com", "sistema1122*");
                                }
                            });
                            if (session != null) {
                                Message message = new MimeMessage(session);
                                message.setFrom(new InternetAddress("sistemacontrolriegoautomatico@gmail.com"));
                                message.setSubject("Recuperacion de Contraseña");
                                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo));
                                message.setContent("Has solicitado una recuperacion de contraseña, tu clave es " + clave, "text/html; charset=utf-8");
                                Transport.send(message);
                            }
                            progressBarbuscar.dismiss();
                            mostrarsnack = Snackbar.make(findViewById(R.id.recuperar_contra), "Se ha enviado la contraseña a tu correo", Snackbar.LENGTH_LONG).setAction("Action", null);
                            View sbView = mostrarsnack.getView();
                            sbView.setBackgroundColor(Color.rgb(13,112,110));
                            mostrarsnack.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    progressBarbuscar.dismiss();
                    mostrarsnack = Snackbar.make(findViewById(R.id.recuperar_contra), "No se encontro el usuario", Snackbar.LENGTH_LONG).setAction("Action", null);
                    View sbView = mostrarsnack.getView();
                    sbView.setBackgroundColor(Color.rgb(13,112,110));
                    mostrarsnack.show();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

}
