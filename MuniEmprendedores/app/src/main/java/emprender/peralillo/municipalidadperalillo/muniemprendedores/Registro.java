package emprender.peralillo.municipalidadperalillo.muniemprendedores;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Registro extends AppCompatActivity {
    TextView textView3, textView1, textView12, textView123, textView1258,textView125, textView12589, t, t2, t8, t55, t110, t550, t5500;
    EditText rut, nombre, ape1, ape2, comuna, direccion, telefono, email, actividad;
    Spinner sii, categoria, segunda_categoria, financiamiento, capacitacion;
    Button registrar;
    Animation animacion_desde_abajo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().setElevation(0);
        setTitle("Registro Emprendedores");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        rut = findViewById(R.id.rut);
        nombre = findViewById(R.id.nombre);
        ape1 = findViewById(R.id.ape1);
        ape2 = findViewById(R.id.ape2);
        comuna = findViewById(R.id.comuna);
        direccion = findViewById(R.id.direccion);
        telefono = findViewById(R.id.telefono);
        email = findViewById(R.id.email);
        actividad = findViewById(R.id.actividad);
        sii = findViewById(R.id.sii);
        categoria= findViewById(R.id.categoria);
        segunda_categoria = findViewById(R.id.segunda_categoria);
        financiamiento = findViewById(R.id.financiamiento);
        capacitacion = findViewById(R.id.capacitacion);
        textView1 = findViewById(R.id.textView1);
        textView12 = findViewById(R.id.textView12);
        textView123 = findViewById(R.id.textView123);
        textView1258 = findViewById(R.id.textView1258);
        textView125 = findViewById(R.id.textView125);
        textView12589 = findViewById(R.id.textView12589);
        t = findViewById(R.id.t);
        t2 = findViewById(R.id.t2);
        t8 = findViewById(R.id.t8);
        t55 = findViewById(R.id.t55);
        t5500 = findViewById(R.id.t5500);
        t110 = findViewById(R.id.t110);
        textView3 = findViewById(R.id.textView3);
        registrar = findViewById(R.id.registrar);
        //animaciones
        animacion_desde_abajo = AnimationUtils.loadAnimation(this, R.anim.animacion_desde_abajo);
        rut.setAnimation(animacion_desde_abajo);
        nombre.setAnimation(animacion_desde_abajo);
        ape1.setAnimation(animacion_desde_abajo);
        ape2.setAnimation(animacion_desde_abajo);
        comuna.setAnimation(animacion_desde_abajo);
        direccion.setAnimation(animacion_desde_abajo);
        telefono.setAnimation(animacion_desde_abajo);
        email.setAnimation(animacion_desde_abajo);
        actividad.setAnimation(animacion_desde_abajo);
        sii.setAnimation(animacion_desde_abajo);
        categoria.setAnimation(animacion_desde_abajo);
        segunda_categoria.setAnimation(animacion_desde_abajo);
        financiamiento.setAnimation(animacion_desde_abajo);
        capacitacion.setAnimation(animacion_desde_abajo);
        textView1.setAnimation(animacion_desde_abajo);
        textView12.setAnimation(animacion_desde_abajo);
        textView123.setAnimation(animacion_desde_abajo);
        textView1258.setAnimation(animacion_desde_abajo);
        textView125.setAnimation(animacion_desde_abajo);
        textView12589.setAnimation(animacion_desde_abajo);
        t.setAnimation(animacion_desde_abajo);
        t2.setAnimation(animacion_desde_abajo);
        t8.setAnimation(animacion_desde_abajo);
        t55.setAnimation(animacion_desde_abajo);
        t5500.setAnimation(animacion_desde_abajo);
        t110.setAnimation(animacion_desde_abajo);
        textView3.setAnimation(animacion_desde_abajo);
        registrar.setAnimation(animacion_desde_abajo);

    }
}
