package com.inventar.nuni.inventari.info;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.inventar.nuni.inventari.DatabazeCon;
import com.inventar.nuni.inventari.R;




/**
 * Created by n.telo on 9/6/2017.
 */

public class InformacionTab extends android.support.v4.app.Fragment {
    TextView tx1,tx2,tx3,t4,t5,t6;
    String id,emer,njesi,kategori,cmim,data;
    private Context con;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_informacion, container, false);
        DatabazeCon mydb = new DatabazeCon(getActivity());
        tx1= (TextView) rootView.findViewById(R.id.textView2);
        tx2= (TextView) rootView.findViewById(R.id.textView);
        tx3= (TextView) rootView.findViewById(R.id.textView9);
        t4= (TextView) rootView.findViewById(R.id.textView11);
        t5= (TextView) rootView.findViewById(R.id.textView10);
        t6= (TextView) rootView.findViewById(R.id.textView8);
        Bundle bn = getArguments();
        //merr id e produktit
        id = bn.getString("params");

        Cursor cursor = mydb.getInfoProdukt(id);
        if(cursor.getCount()==0){
            Toast.makeText(getActivity(),"Nuk ka te dhena!",Toast.LENGTH_SHORT);
        }

        while(cursor.moveToNext()) {

            emer = cursor.getString(1);
            njesi = cursor.getString(2);
         kategori = cursor.getString(3);
            cmim = cursor.getString(4);
            data = cursor.getString(5);
        }

        cursor.close();
        tx1.setText("ID e produktit: " +id);
        tx2.setText("Emri produktit: "+ emer);
        tx3.setText("Njesia: "+ njesi);
        t4.setText("Kategori: "+ kategori);
        t5.setText("Cmimi: "+ cmim);
        t6.setText("Data: "+ data);

        return rootView;

    }


}
