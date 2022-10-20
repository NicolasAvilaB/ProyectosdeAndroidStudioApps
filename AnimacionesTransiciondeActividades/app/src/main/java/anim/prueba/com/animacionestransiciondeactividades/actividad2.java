package anim.prueba.com.animacionestransiciondeactividades;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

public class actividad2 extends AppCompatActivity {
    Button volver,volver2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad2);
        volver = (Button) findViewById(R.id.volver);
        volver2 = (Button) findViewById(R.id.volver2);

       /* Fade fadeIn = new Fade(Fade.IN);
        fadeIn.setDuration(actividad1.DURATION_TRANSITION);
        fadeIn.setInterpolator(new DecelerateInterpolator());*/

        if (Build.VERSION.SDK_INT >= 21) {
            Slide slidetop = new Slide(Gravity.RIGHT);
            slidetop.setDuration(actividad1.DURATION_TRANSITION);
            slidetop.setInterpolator(new DecelerateInterpolator());
            getWindow().setEnterTransition(slidetop);
            getWindow().setAllowEnterTransitionOverlap(false);
            getWindow().setReturnTransition(slidetop);
        }
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackCLicked();
            }
        });


    }
        public void onBackCLicked(){
        if (Build.VERSION.SDK_INT >= 21) {
            finishAfterTransition();
        }
    }
}
