package underhome.weather.com.underhome;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class DialogoCerrar {

    Button aceptaraccion;
    Button cancelaraccion;

    public interface FinalizoCuadroDialogo{
        void ResultadoCuadroDialogo(boolean res);
    }

    private FinalizoCuadroDialogo interfaz;

    public DialogoCerrar (Context mensaje_flotante_cerrando, FinalizoCuadroDialogo actividad){

        interfaz = actividad;
        final Dialog dialogo = new Dialog(mensaje_flotante_cerrando);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialogo_cerrarsesion);

        aceptaraccion = dialogo.findViewById(R.id.aceptaraccion);
        cancelaraccion = dialogo.findViewById(R.id.cancelaraccion);

        aceptaraccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              interfaz.ResultadoCuadroDialogo(true);
              dialogo.dismiss();
            }
        });

        cancelaraccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaz.ResultadoCuadroDialogo(false);
                dialogo.dismiss();
            }
        });
        dialogo.show();
    }


}
