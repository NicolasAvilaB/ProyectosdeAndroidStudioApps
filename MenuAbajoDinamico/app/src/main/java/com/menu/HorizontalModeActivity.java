package com.menu;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.ismaeldivita.chipnavigation.sample.util.colorAnimation;

public class HorizontalModeActivity extends Activity {
    private container findViewById<View>(R.id.container);
    private title by lazy { findViewById<TextView>(R.id.title);
    private menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu);

    private var lastColor: Int = 0

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);

        lastColor = (container.background as ColorDrawable).color

        menu.setOnItemSelectedListener { id ->
                val option = when (id) {
            R.id.home -> R.color.home to "Home"
            R.id.activity -> R.color.activity to "Activity"
            R.id.favorites -> R.color.favorites to "Favorites"
            R.id.settings -> R.color.settings to "Settings"
                else -> R.color.white to ""
        }
            val color = ContextCompat.getColor(this@HorizontalModeActivity, option.first)
            container.colorAnimation(lastColor, color)
            lastColor = color

            title.text = option.second
        }


        if (savedInstanceState == null) {
            menu.showBadge(R.id.home);
            menu.showBadge(R.id.settings, 32);
        }
    }
}
