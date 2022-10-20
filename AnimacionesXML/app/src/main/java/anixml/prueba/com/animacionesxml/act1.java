package anixml.prueba.com.animacionesxml;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class act1 extends AppCompatActivity {
    Button ir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act1);
        ir = (Button)findViewById(R.id.ir);
        ir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act1.this, act2.class);
                startActivity(intent);
                overridePendingTransition(R.transition.transition_enter, R.transition.transition_exit);
                finish();
            }
        });
    }
}
