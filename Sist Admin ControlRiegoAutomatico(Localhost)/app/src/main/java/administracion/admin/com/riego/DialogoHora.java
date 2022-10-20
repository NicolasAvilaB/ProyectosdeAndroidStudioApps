package administracion.admin.com.riego;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import underhome.weather.com.underhome.R;

public class DialogoHora {

    Button aceptaraccion, suma, resta, suma2, resta2, cancelaraccion;
    TextView contenido_suma, contenido_suma2;

    public interface FinalizoCuadroDialogo3{
        void ResultadoCuadroDialogo3(String ress);
    }

    private FinalizoCuadroDialogo3 interfaz;

    public DialogoHora(Context mensaje_flotante_hora, FinalizoCuadroDialogo3 actividad){

        interfaz = actividad;
        final Dialog dialogo = new Dialog(mensaje_flotante_hora);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialogo_hora);

        aceptaraccion = dialogo.findViewById(R.id.aceptaraccion);
        cancelaraccion = dialogo.findViewById(R.id.cancelaraccion);
        contenido_suma = dialogo.findViewById(R.id.contenido_suma);
        suma = dialogo.findViewById(R.id.suma);
        resta = dialogo.findViewById(R.id.resta);
        contenido_suma2 = dialogo.findViewById(R.id.contenido_suma2);
        suma2 = dialogo.findViewById(R.id.suma2);
        resta2 = dialogo.findViewById(R.id.resta2);

        suma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valor = contenido_suma.getText().toString();
                valor = valor.replace("Hora ", "");
                int valor_nuevo = Integer.parseInt(valor) + 1;
                if (valor_nuevo > 23){
                    contenido_suma.setText("Hora 23");
                }else{
                    if (valor_nuevo > 9){
                        contenido_suma.setText("Hora "+valor_nuevo);
                    }else{
                        contenido_suma.setText("Hora 0"+valor_nuevo);
                    }
                }
            }
        });



        resta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valor = contenido_suma.getText().toString();
                valor = valor.replace("Hora ", "");
                int valor_nuevo = Integer.parseInt(valor) - 1;
                if (valor_nuevo < 01) {
                    contenido_suma.setText("Hora 00");
                }
                else{
                    if (valor_nuevo > 9){
                        contenido_suma.setText("Hora "+valor_nuevo);
                    }else{
                        contenido_suma.setText("Hora 0"+valor_nuevo);
                    }
                }
            }
        });

        suma2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valor = contenido_suma2.getText().toString();
                valor = valor.replace("Minuto ", "");
                int valor_nuevo = Integer.parseInt(valor) + 1;
                if (valor_nuevo > 59){
                    contenido_suma2.setText("Minuto 59");
                }else{
                    if (valor_nuevo > 9){
                        contenido_suma2.setText("Minuto "+valor_nuevo);
                    }else{
                        contenido_suma2.setText("Minuto 0"+valor_nuevo);
                    }
                }
            }
        });



        resta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valor = contenido_suma2.getText().toString();
                valor = valor.replace("Minuto ", "");
                int valor_nuevo = Integer.parseInt(valor) - 1;
                if (valor_nuevo < 1){
                    contenido_suma2.setText("Minuto 00");
                }else{
                    if (valor_nuevo > 9){
                        contenido_suma2.setText("Minuto "+valor_nuevo);
                    }else{
                        contenido_suma2.setText("Minuto 0"+valor_nuevo);
                    }
                }
            }
        });

        aceptaraccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val = contenido_suma.getText().toString() + ":" + contenido_suma2.getText().toString();
                val = val.replace("Minuto ", "");
                val = val.replace("Hora ", "");
                interfaz.ResultadoCuadroDialogo3(val);
                dialogo.dismiss();
            }
        });

        cancelaraccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();
            }
        });
        dialogo.show();
    }
}
