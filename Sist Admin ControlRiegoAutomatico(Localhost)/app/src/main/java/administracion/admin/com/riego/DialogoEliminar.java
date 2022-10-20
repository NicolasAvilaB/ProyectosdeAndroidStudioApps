package administracion.admin.com.riego;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import underhome.weather.com.underhome.R;

public class DialogoEliminar {

    Button aceptaraccion;
    Button cancelaraccion;

    public interface FinalizoCuadroDialogo{
        void ResultadoCuadroDialogo(boolean res);
    }

    private FinalizoCuadroDialogo interfaz;

    public DialogoEliminar (Context mensaje_flotante_eliminando, FinalizoCuadroDialogo actividad){

        interfaz = actividad;
        final Dialog dialogo = new Dialog(mensaje_flotante_eliminando);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialogo_eliminar);

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
