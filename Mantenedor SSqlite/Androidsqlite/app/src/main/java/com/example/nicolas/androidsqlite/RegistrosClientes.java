package com.example.nicolas.androidsqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistrosClientes extends AppCompatActivity {
    EditText rut, nombre, direccion, telefono, buscador;
    Button nuevo, modificar, eliminar, cancelar, buscar, guardar;
    //Spinner sprut;
    SQLiteDatabase bd;
    int varseleccion = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros_clientes);

        clasecrearbd crearbd = new clasecrearbd(getApplicationContext(),"android",null,1);
        bd = crearbd.getWritableDatabase();

        rut = (EditText)findViewById(R.id.rut);
        nombre=(EditText)findViewById(R.id.nombre);
        direccion=(EditText)findViewById(R.id.direccion);
        telefono=(EditText)findViewById(R.id.telefono);
        buscador=(EditText)findViewById(R.id.buscador);
        nuevo=(Button)findViewById(R.id.nuevo);
        modificar=(Button)findViewById(R.id.modificar);
        eliminar=(Button)findViewById(R.id.eliminar);
        cancelar=(Button)findViewById(R.id.cancelar);
        guardar=(Button)findViewById(R.id.guardar);
        buscar=(Button)findViewById(R.id.buscar);
        //sprut=(Spinner)findViewById(R.id.sprut);

        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controles(true, false);
                limpiar();
                varseleccion = 1;
            }
        });

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controles(true, false);
                varseleccion = 2;
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String run = rut.getText().toString().trim();
                bd.execSQL("delete from clientes where rut ='" + run + "'");
                Toast.makeText(getApplicationContext(), "Datos Eliminados Correctamente", Toast.LENGTH_LONG).show();
                limpiar();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controles(false, true);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String run = rut.getText().toString().trim();
                //run.replaceAll(" ", "%20");
                String nom = nombre.getText().toString().trim();
                //nom.replaceAll(" ", "%20");
                String direc = direccion.getText().toString().trim();
                //direc.replaceAll(" ", "%20");
                String telef = telefono.getText().toString().trim();
                //telef.replaceAll(" ", "%20");
                if (varseleccion == 1){
                    if(rut.getText().length() == 0){
                        Toast.makeText(getApplicationContext(), "Error: Campo Rut Vacio", Toast.LENGTH_LONG).show();
                    }
                    else if (nombre.getText().length() == 0){
                        Toast.makeText(getApplicationContext(), "Error: Campo Nombre Vacio", Toast.LENGTH_LONG).show();
                    }
                    else if (direccion.getText().length() == 0){
                        Toast.makeText(getApplicationContext(), "Error: Campo Direccion Vacio", Toast.LENGTH_LONG).show();
                    }
                    else if (telefono.getText().length() == 0){
                        Toast.makeText(getApplicationContext(), "Error: Campo Telefono Vacio", Toast.LENGTH_LONG).show();
                    }
                    else {
                        bd.execSQL("Insert into clientes values('" + run + "','" + nom + "','" + direc + "'," + telef + ")");
                        Toast.makeText(getApplicationContext(), "Datos Ingresados Correctamente", Toast.LENGTH_LONG).show();
                        controles(false, true);
                    }
                }
                else if (varseleccion == 2){
                        bd.execSQL("update clientes set nombre='"+nom+"',direccion='"+direc+"',telefono="+telef+" where rut='"+run+"'");
                        Toast.makeText(getApplicationContext(), "Datos Modificados Correctamente", Toast.LENGTH_LONG).show();
                        controles(false, true);
                }
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String rutbuscar = buscador.getText().toString().trim();
                    Cursor c;
                    c = bd.rawQuery("select rut, nombre, direccion, telefono from clientes where rut ='" + rutbuscar + "'", null);
                    c.moveToFirst();
                    rut.setText(c.getString(0));
                    nombre.setText(c.getString(1));
                    direccion.setText(c.getString(2));
                    telefono.setText(c.getString(3));
                    Toast.makeText(getApplicationContext(), "Datos Encontrados Exitosamente", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Datos No Encontrados", Toast.LENGTH_LONG).show();
                    limpiar();
                }
            }
        });
    }

    public void limpiar(){
        rut.setText("");
        nombre.setText("");
        direccion.setText("");
        telefono.setText("");
    }

    public void controles(boolean a, boolean b){
        rut.setEnabled(a);
        nombre.setEnabled(a);
        direccion.setEnabled(a);
        telefono.setEnabled(a);
        buscador.setEnabled(b);
        nuevo.setEnabled(b);
        modificar.setEnabled(b);
        eliminar.setEnabled(b);
        guardar.setEnabled(a);
        cancelar.setEnabled(a);
        buscar.setEnabled(b);
    }
}
