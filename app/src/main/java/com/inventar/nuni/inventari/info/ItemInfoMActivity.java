package com.inventar.nuni.inventari.info;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.inventar.nuni.inventari.R;

public class ItemInfoMActivity extends AppCompatActivity {
TextView tx1,tx2,tx3,t4,t5,t6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_shtese);
        tx1= (TextView)findViewById(R.id.textView);
        tx2=(TextView)findViewById(R.id.textView2);
        tx3=(TextView)findViewById(R.id.textView8);
        t4=(TextView)findViewById(R.id.textView9);
        t5=(TextView)findViewById(R.id.textView10);
        t6=(TextView)findViewById(R.id.textView11);

        Intent i = getIntent();
        //per id
        String id = i.getStringExtra("id");
        tx1.setText(id);

        //per pershkrim
        String pershkrim = i.getStringExtra("emer");
        tx2.setText(pershkrim);

        //per njesi
        String njesi = i.getStringExtra("njesi");
        tx3.setText(njesi);

        //per kategori
        String kategori = i.getStringExtra("kategori");
        t4.setText(kategori);

        //per cmim
        String cmim = i.getStringExtra("cmim");
        t5.setText(cmim);

        //per date
        String data = i.getStringExtra("data");
        t6.setText(data);


    }
}
