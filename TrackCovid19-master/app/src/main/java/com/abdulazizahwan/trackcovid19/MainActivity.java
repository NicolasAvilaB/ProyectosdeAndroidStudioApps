package com.abdulazizahwan.trackcovid19;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.abdulazizahwan.trackcovid19.ui.country.CountryFragment;
import com.abdulazizahwan.trackcovid19.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    ChipNavigationBar menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
        menu = findViewById(R.id.nav_view);
        menu.setItemSelected(R.id.navigation_home, true);
        menu.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment seleccion = null;
                switch (i){
                    case R.id.navigation_home:
                        seleccion = new HomeFragment();
                        break;
                    case R.id.navigation_country:
                        seleccion = new CountryFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, seleccion).commit();
            }
        });
    }

}
