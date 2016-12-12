package cn.focion.cal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    
    private CalAdapter mAdapter;
    
    private CalModel mCalModel;
    
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mAdapter.notifyDataSetChanged();
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCalModel = new CalModel(2017, 2018, 3, 5);
        mCalModel.build();
        
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        mAdapter = new CalAdapter(this, mCalModel);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 7);
        layoutManager.setSpanSizeLookup(new CalSpanSize(mAdapter));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        // handler.sendEmptyMessageDelayed(0, 10 * 1000);
    }
}
