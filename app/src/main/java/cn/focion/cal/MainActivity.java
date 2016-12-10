package cn.focion.cal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalUtil calUtil = new CalUtil();
        calUtil.initializeYear(2017);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        CalAdapter adapter = new CalAdapter(this, calUtil);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 7);
        layoutManager.setSpanSizeLookup(new CalSpanSize(adapter));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}
