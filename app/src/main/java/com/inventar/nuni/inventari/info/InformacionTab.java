package com.inventar.nuni.inventari.info;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.inventar.nuni.inventari.R;


/**
 * Created by n.telo on 9/6/2017.
 */

public class InformacionTab extends android.support.v4.app.Fragment {
    TextView tx1,tx2,tx3,t4,t5,t6;
    String id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_informacion, container, false);
        tx1= (TextView) rootView.findViewById(R.id.textView2);
        Bundle bn = getArguments();
          id = bn.getString("params");

        tx1.setText(id);


        return rootView;

    }


}
