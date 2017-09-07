package com.inventar.nuni.inventari.info;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.inventar.nuni.inventari.DatabazeCon;
import com.inventar.nuni.inventari.DownloadTask;
import com.inventar.nuni.inventari.MainActivity;
import com.inventar.nuni.inventari.MyAdapter;
import com.inventar.nuni.inventari.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by n.telo on 9/6/2017.
 */

public class ShitjeTab extends android.support.v4.app.Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<String>id,njesi,sasi,data;
    private String id_rreshtit;
    ArrayList<String>input_id,input_njesi,input_sasi,input_data;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.tab2_shitje, container, false);
            //per te marre te dhenat
            Bundle bn = getArguments();
            //merr id e produktit
            id_rreshtit= bn.getString("params");

            shkarko_artikuj();
            //recycleview
            recyclerView = (RecyclerView)rootView.findViewById(R.id.my_recycler_view_shitje);

            recyclerView.setHasFixedSize(true);
            //linear-layout

            mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);

            //merr info nga databaza
            shkarko_artikuj();
            merr_shitje();

            //adapteri i recycleview
            mAdapter = new ShitjeAdapter(id,njesi,getActivity(),sasi,data);
            recyclerView.setAdapter(mAdapter);
            return rootView;
        }


        public void shkarko_artikuj(){
             String url_kerkuar ="https://dl.dropboxusercontent.com/s/q8j6mdrsnx51gcm/artikulli.txt?dl=0";
            ArtikujDatabaze mydb = new ArtikujDatabaze(getActivity());
            id= new ArrayList<>();
            njesi = new ArrayList<>();
            sasi= new ArrayList<>();
            data= new ArrayList<>();

                DownloadShitje task = new DownloadShitje();
                String res = null;
                try {
                    res = task.execute(url_kerkuar).get();
                    String[] records = res.split(";");

                    for (String record : records) {
                        String[] recordData = record.split(",");
                        id.add(recordData[0]);
                        njesi.add(recordData[1]);
                        sasi.add(recordData[2]);
                        data.add(recordData[3]);
                    }
                    //mydb.fshi();
                    for (int i = 0; i < id.size(); i++) {
                        mydb.shto_artikuj_shitje(id.get(i), njesi.get(i), sasi.get(i), data.get(i));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            public void merr_shitje(){

               ArtikujDatabaze mydb = new ArtikujDatabaze(getActivity());
                Cursor rezultat = mydb.merr_artikuj_shitje(id_rreshtit);
                input_id = new ArrayList<>();
                input_njesi= new ArrayList<>();
                input_sasi= new ArrayList<>();
                input_data= new ArrayList<>();

                while (rezultat.moveToNext()) {

                    input_id.add("Id Artikullit: " + rezultat.getString(0));
                    input_njesi.add("Njesia: " + rezultat.getString(1));
                    input_sasi.add("Kategoria: " + rezultat.getString(2));
                    input_data.add("Data: " + rezultat.getString(3));
                }

                rezultat.close();
                // adapteri
                mAdapter = new ShitjeAdapter(input_id, input_njesi, getActivity(), input_sasi, input_data);
                recyclerView.setAdapter(mAdapter);
                rezultat.close();

            }




}
