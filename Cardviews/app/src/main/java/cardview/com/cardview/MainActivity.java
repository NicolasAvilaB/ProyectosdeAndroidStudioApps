package cardview.com.cardview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Data> listadata = new ArrayList<Data>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(listadata);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        listadata.add(new Data("Prueba de Lista","Pehdfuiehfiuh"));
        listadata.add(new Data("Pewfefefwa","ewffewew"));

        listadata.add(new Data("aaaaaaaa Lista","vvvccccccccch"));
        listadata.add(new Data("Prueefeeffvbbbbe Lista","iiiiiiiiiiii"));

    }
}
