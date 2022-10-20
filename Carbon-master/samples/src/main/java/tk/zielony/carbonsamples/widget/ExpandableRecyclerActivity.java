package tk.zielony.carbonsamples.widget;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import carbon.Carbon;
import carbon.recycler.DividerItemDecoration;
import carbon.widget.ExpandableRecyclerView;
import tk.zielony.carbonsamples.R;
import tk.zielony.carbonsamples.Samples;
import tk.zielony.carbonsamples.SamplesActivity;

public class ExpandableRecyclerActivity extends SamplesActivity {
    private static List<String> fruits = new ArrayList<>(Arrays.asList("Strawberry", "Apple", "Orange", "Lemon", "Beer", "Lime", "Watermelon", "Blueberry", "Plum"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandablerecycler);

        Samples.initToolbar(this, getString(R.string.expandableRecyclerActivity_title));

        final ExpandableRecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ExpandableFruitAdapter fruitAdapter = new ExpandableFruitAdapter(fruits);
        recyclerView.setAdapter(fruitAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(new ColorDrawable(Carbon.getThemeColor(this, R.attr.carbon_dividerColor)), getResources().getDimensionPixelSize(R.dimen.carbon_dividerHeight));
        decoration.setDrawBefore(position -> position > 0 && fruits.contains(fruitAdapter.getItem(position)));
        recyclerView.addItemDecoration(decoration);
    }
}
