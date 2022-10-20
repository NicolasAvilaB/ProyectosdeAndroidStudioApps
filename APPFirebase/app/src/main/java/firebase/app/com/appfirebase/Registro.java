package firebase.app.com.appfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registro extends AppCompatActivity {
    EditText correo,password;
    Button registro;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        auth = FirebaseAuth.getInstance();

        correo = (EditText)findViewById(R.id.correo);
        password = (EditText)findViewById(R.id.password);
        registro = (Button)findViewById(R.id.registro);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = correo.getText().toString();
                String contra = password.getText().toString();

                if(TextUtils.isEmpty(user)){
                    Toast.makeText(getApplicationContext(),"Coloca tu Correo", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(contra)){
                    Toast.makeText(getApplicationContext(),"Coloca tu Contraseña", Toast.LENGTH_SHORT).show();
                }

                auth.createUserWithEmailAndPassword(user,contra)
                        .addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getApplicationContext(),"Se ha Creado el Usuario", Toast.LENGTH_SHORT).show();
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Preparate para los Problemas...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Registro.this, Login.class);
                            startActivity(intent);
                        }

                    }
                });
            }
        });

    }
}
