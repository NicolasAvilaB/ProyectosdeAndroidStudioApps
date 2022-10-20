package mysql.com.mysqljava;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.*;

public class MainActivity extends AppCompatActivity {
    Connection conn = null;
    TextView b;
    Button r,x, y;
    EditText e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = findViewById(R.id.b);
        r = findViewById(R.id.r);
        e = findViewById(R.id.e);
        x = findViewById(R.id.x);
        y = findViewById(R.id.y);


        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loginn obj = new Loginn();
                obj.execute("");
            }
        });

        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarUsuario obj = new RegistrarUsuario();
                obj.execute("");
            }
        });

        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EliminarUsuario obj = new EliminarUsuario();
                obj.execute("");
            }
        });

    }

    //para Login


    private class Loginn extends AsyncTask<String,String,String>{
      String msg = "";
      @Override
      protected void onPreExecute(){

      }
      @Override
      protected String doInBackground(String... strings) {
              try {
                  Class.forName("com.mysql.jdbc.Driver");
                  conn = DriverManager.getConnection("jdbc:mysql://ip/bd","usuario_mysql","password_mysql");
                  if(conn == null)
                  {
                      msg="No se pudo Conectar";
                  }
                  else
                  {
                      Statement psd = conn.createStatement();
                      ResultSet rs = psd.executeQuery("select * from a where ass = '" +e.getText().toString()+ "'");
                      if (rs.next()) {
                          b.setText("Bienvenido Usuario!!");
                      }
                      else{
                          b.setText("Usuario no Existe!!");

                      }
                  }
              } catch (Exception e){
                  e.printStackTrace();
              }
              return msg;
          }
          @Override
          protected void onPostExecute(String msg){
          }
          }


          //para Registro de Usuario

    private class RegistrarUsuario extends AsyncTask<String,String,String>{
        String msg = "";
        @Override
        protected void onPreExecute(){

        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://ip/bd","usuario_mysql","password_mysql");
                if(conn == null)
                {
                    msg="No se pudo Conectar";
                }
                else
                {
                    Statement st = conn.createStatement();
                    st.executeUpdate("insert into a values('" +(e.getText().toString())+ "')");
                    b.setText("registro correcto!!");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return msg;
        }
        @Override
        protected void onPostExecute(String msg){
        }
    }

    //para Borrar Usuario

    private class EliminarUsuario extends AsyncTask<String,String,String>{
        String msg = "";
        @Override
        protected void onPreExecute(){

        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://ip/bd","usuario_mysql","password_mysql");
                if(conn == null)
                {
                    msg="No se pudo Conectar";
                }
                else
                {
                    Statement st = conn.createStatement();
                    st.executeUpdate("delete from a where ass = '" +(e.getText().toString())+ "'");
                    b.setText("Borrado correcto!!");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return msg;
        }
        @Override
        protected void onPostExecute(String msg){
        }
    }



}

