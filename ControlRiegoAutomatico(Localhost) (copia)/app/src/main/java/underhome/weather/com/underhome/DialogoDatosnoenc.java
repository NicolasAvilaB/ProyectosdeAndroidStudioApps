package underhome.weather.com.underhome;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class DialogoDatosnoenc {
    Button aceptaraccion;

    public DialogoDatosnoenc (Context mensaje_datos){

        final Dialog dialogo = new Dialog(mensaje_datos);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialogo_datosnoenc);

        aceptaraccion = dialogo.findViewById(R.id.aceptaraccion);

        aceptaraccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();
            }
        });
        dialogo.show();
    }


}
