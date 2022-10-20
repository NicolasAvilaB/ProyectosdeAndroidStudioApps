package tk.zielony.carbonsamples.component;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import java.io.Serializable;
import java.util.Arrays;

import carbon.component.DataBindingComponent;
import carbon.component.DefaultIconDropDownItem;
import carbon.component.DefaultIconEditTextItem;
import carbon.component.DefaultIconPasswordItem;
import carbon.component.DividerItem;
import carbon.component.DividerRow;
import carbon.component.IconDropDownRow;
import carbon.component.IconEditTextRow;
import carbon.component.IconPasswordRow;
import carbon.component.PaddingItem;
import carbon.component.PaddingRow;
import carbon.drawable.VectorDrawable;
import carbon.recycler.RowListAdapter;
import carbon.widget.RecyclerView;
import tk.zielony.carbonsamples.R;
import tk.zielony.carbonsamples.Samples;
import tk.zielony.carbonsamples.SamplesActivity;

public class RegisterActivity extends SamplesActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listcomponent);

        Samples.initToolbar(this, getString(R.string.registerActivity_title));

        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        RowListAdapter<Serializable> adapter = new RowListAdapter<>(DefaultIconEditTextItem.class, IconEditTextRow::new);
        adapter.addFactory(PaddingItem.class, PaddingRow::new);
        adapter.addFactory(String.class, parent -> new DataBindingComponent<>(parent, R.layout.row_description));
        adapter.addFactory(DividerItem.class, DividerRow::new);
        adapter.addFactory(DefaultIconPasswordItem.class, IconPasswordRow::new);
        adapter.addFactory(DefaultIconDropDownItem.class, IconDropDownRow::new);
        recycler.setAdapter(adapter);
        adapter.setItems(Arrays.asList(
                new PaddingItem(getResources().getDimensionPixelSize(R.dimen.carbon_paddingHalf)),
                "Forms such as registration screen can be easily created with reusable components and a recycler",
                new DefaultIconEditTextItem(new VectorDrawable(getResources(), R.raw.profile), "login", ""),
                new DefaultIconEditTextItem(new VectorDrawable(getResources(), R.raw.email), "email", ""),
                new DefaultIconPasswordItem(new VectorDrawable(getResources(), R.raw.lock), "password", ""),
                new DefaultIconPasswordItem(null, "retype password", ""),
                new DefaultIconDropDownItem<>(new VectorDrawable(getResources(), R.raw.gender), "sex", new String[]{"Male", "Female"}, "Male"),
                new PaddingItem(getResources().getDimensionPixelSize(R.dimen.carbon_paddingHalf))));
    }
}
