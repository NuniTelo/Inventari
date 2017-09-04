package com.inventar.nuni.inventari;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {

    Button vendos;
    EditText numri;
    private String ko_ha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        numri =(EditText) findViewById(R.id.editText);
        numri.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);



    }

    public void get_item(View view){
        String ko_ha = numri.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("koha", ko_ha);
        setResult(RESULT_OK, intent);
        Toast.makeText(Settings.this,"Koha u vendos me sukses!",Toast.LENGTH_LONG).show();
        finish();

    }
}
