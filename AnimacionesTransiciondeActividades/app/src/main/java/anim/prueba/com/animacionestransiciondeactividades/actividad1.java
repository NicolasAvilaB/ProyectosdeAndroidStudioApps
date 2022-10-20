package anim.prueba.com.animacionestransiciondeactividades;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

public class actividad1 extends AppCompatActivity {
    Transition transition;
    public static final long DURATION_TRANSITION = 1000;
    Button explotar,fade,deslizar, iniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad1);
        if (Build.VERSION.SDK_INT >= 21) {
            Slide slidebottom = new Slide();
            slidebottom.setDuration(DURATION_TRANSITION);
            slidebottom.setInterpolator(new DecelerateInterpolator());
            getWindow().setReenterTransition(slidebottom);
            getWindow().setAllowReturnTransitionOverlap(false);

        }

        explotar = (Button)findViewById(R.id.explotar);
        fade = (Button)findViewById(R.id.fade);
        deslizar = (Button)findViewById(R.id.deslizar);
        iniciar = (Button)findViewById(R.id.iniciar);

        explotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onExplodeClicked();
            }
        });

        fade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFadeClicked();
            }
        });

        deslizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSlideClicked();
            }
        });

    }

    public void onExplodeClicked() {

        if (Build.VERSION.SDK_INT >= 21) {
            transition = new Explode();
            IniciarActivity();
        }

    }

    public void onSlideClicked() {
        if (Build.VERSION.SDK_INT >= 21) {
            transition = new Slide(Gravity.START);
            IniciarActivity();
        }
    }

    public void onFadeClicked() {
        if (Build.VERSION.SDK_INT >= 21) {
            transition = new Fade(Fade.OUT);
            IniciarActivity();
        }
    }

    public void onActionClicked() {

    }

    @SuppressWarnings("unchecked")
    private void IniciarActivity() {
       transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setExitTransition(transition);
            Intent intent = new Intent(this,actividad2.class);
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
        }
    }

}