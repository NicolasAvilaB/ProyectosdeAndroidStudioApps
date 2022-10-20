package tk.zielony.carbonsamples.widget;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import carbon.behavior.Behavior;
import carbon.widget.CheckBox;
import carbon.widget.FloatingActionButton;
import carbon.widget.RelativeLayout;
import carbon.widget.Snackbar;
import tk.zielony.carbonsamples.R;
import tk.zielony.carbonsamples.Samples;
import tk.zielony.carbonsamples.SamplesActivity;

public class SnackbarActivity extends SamplesActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);

        Samples.initToolbar(this, getString(R.string.snackbarActivity_title));

        final CheckBox tapCheckBox = findViewById(R.id.tap);
        final CheckBox swipeCheckBox = findViewById(R.id.swipe);
        final CheckBox floatingCheckBox = findViewById(R.id.floating);
        final CheckBox infiniteCheckBox = findViewById(R.id.infinite);
        final CheckBox pushCheckBox = findViewById(R.id.push);
        final CheckBox fromTopCheckBox = findViewById(R.id.fromTop);
        final FloatingActionButton fab = findViewById(R.id.fab);
        RelativeLayout root = findViewById(R.id.root);

        findViewById(R.id.button).setOnClickListener(view -> {
            final Snackbar snackbar = new Snackbar(SnackbarActivity.this, "Hello world!", infiniteCheckBox.isChecked() ? Snackbar.INFINITE : getResources().getInteger(R.integer.carbon_snackbarDuration));
            View snackbarView = snackbar.getView();
            snackbar.setAction("dismiss", snackbar::dismiss);
            snackbar.setStyle(floatingCheckBox.isChecked() ? Snackbar.Style.Floating : Snackbar.Style.Docked);
            snackbar.setTapOutsideToDismissEnabled(tapCheckBox.isChecked());
            snackbar.setSwipeToDismissEnabled(swipeCheckBox.isChecked());
            if (fromTopCheckBox.isChecked()) {
                snackbar.setGravity(Gravity.START | Gravity.TOP);
            } else {
                snackbar.setGravity(Gravity.START | Gravity.BOTTOM);
                if (pushCheckBox.isChecked()) {
                    Behavior<FloatingActionButton> behavior = new Behavior<FloatingActionButton>(fab) {
                        @Override
                        public void onDependencyChanged(View view) {
                            if (snackbarView.getAlpha() < 1) {
                                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) snackbarView.getLayoutParams();
                                fab.setTranslationY(-root.getHeight() + snackbarView.getTop() + (1 - snackbarView.getAlpha()) * (snackbarView.getHeight() + layoutParams.bottomMargin));
                            } else {
                                fab.setTranslationY(-root.getHeight() + snackbarView.getTop() + snackbarView.getTranslationY());
                            }
                        }
                    };
                    behavior.setDependency(snackbarView);
                    root.addBehavior(behavior);
                }
            }
            snackbar.show(root);
        });

        Snackbar.clearQueue();
    }
}
