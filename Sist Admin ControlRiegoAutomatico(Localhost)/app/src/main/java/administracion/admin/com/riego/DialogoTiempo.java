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

public class DialogoTiempo {

    Button aceptaraccion, suma, resta, cancelaraccion;
    TextView contenido_suma;

    public interface FinalizoCuadroDialogo2{
        void ResultadoCuadroDialogo2(String ress);
    }

    private FinalizoCuadroDialogo2 interfaz;

    public DialogoTiempo(Context mensaje_flotante_tiempo, FinalizoCuadroDialogo2 actividad){

        interfaz = actividad;
        final Dialog dialogo = new Dialog(mensaje_flotante_tiempo);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialogo_tiempo);

        aceptaraccion = dialogo.findViewById(R.id.aceptaraccion);
        cancelaraccion = dialogo.findViewById(R.id.cancelaraccion);
        contenido_suma = dialogo.findViewById(R.id.contenido_suma);
        suma = dialogo.findViewById(R.id.suma);
        resta = dialogo.findViewById(R.id.resta);

        suma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valor = contenido_suma.getText().toString();
                int valor_nuevo = Integer.parseInt(valor) + 1;
                if (valor_nuevo > 100){
                    contenido_suma.setText("100");
                }else{
                    contenido_suma.setText(""+valor_nuevo);
                }
            }
        });



        resta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valor = contenido_suma.getText().toString();
                int valor_nuevo = Integer.parseInt(valor) - 1;
                if (valor_nuevo < 1){
                    contenido_suma.setText("1");
                }else{
                    contenido_suma.setText(""+valor_nuevo);
                }
            }
        });

        aceptaraccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaz.ResultadoCuadroDialogo2(contenido_suma.getText().toString());
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
