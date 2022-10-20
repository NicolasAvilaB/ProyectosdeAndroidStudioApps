package tk.zielony.carbonsamples.widget;

import android.os.Bundle;

import carbon.widget.rx.EditText;
import tk.zielony.carbonsamples.R;
import tk.zielony.carbonsamples.Samples;
import tk.zielony.carbonsamples.SamplesActivity;

public class TextFieldsActivity extends SamplesActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textfields);

        Samples.initToolbar(this, getString(R.string.textFieldsActivity_title));

        EditText editText = findViewById(R.id.editText);
        editText.focusChanges().subscribe((hasFocus) -> editText.setText(""));
    }
}
