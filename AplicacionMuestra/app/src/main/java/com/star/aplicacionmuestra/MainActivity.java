package com.star.aplicacionmuestra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import com.ismaeldivita.chipnavigation.*;
public class MainActivity extends AppCompatActivity {

    ChipNavigationBar menu;
    @Override
    protected void onResume() {
        super.onResume();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InicioFragment()).commit();
        menu = findViewById(R.id.menu_abajo);
        menu.setItemSelected(R.id.home, true);
        menu.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment seleccion = null;
                switch (i){
                    case R.id.home:
                        seleccion = new InicioFragment();
                        break;
                    case R.id.person:
                        seleccion = new CuentaFragment();
                        break;
                    case R.id.menu:
                        seleccion = new MenuFragment();
                        break;
                    case R.id.data:
                        seleccion = new DatosFragment();
                        break;
                    case R.id.search:
                        seleccion = new BuscadorFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, seleccion).commit();
            }
        });
    }
}
