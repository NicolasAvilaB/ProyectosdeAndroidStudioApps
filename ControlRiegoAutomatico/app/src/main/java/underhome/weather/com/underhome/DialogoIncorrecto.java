package underhome.weather.com.underhome;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class DialogoIncorrecto {

    Button aceptaraccion;

    public DialogoIncorrecto (Context mensaje_flotante_incorrecto){

        final Dialog dialogo = new Dialog(mensaje_flotante_incorrecto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialogo_incorrecto);

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
