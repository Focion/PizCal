package cn.focion.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cn.focion.cal.CalModel;
import cn.focion.cal.CalView;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalView calView = (CalView) findViewById(R.id.recycler);
        calView.setOnCalSelectListener(new CalView.OnCalSelectListener() {
            @Override
            public void onCalSelect(String date) {
                Toast.makeText(MainActivity.this, date, Toast.LENGTH_SHORT)
                     .show();
            }
        });
        CalModel calModel = calView.getCalModel();
        calModel.setYear(2017, 2017).build();
        calView.notifyDataSetChanged();
    }
}
