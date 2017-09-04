package com.inventar.nuni.inventari;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import static com.inventar.nuni.inventari.R.id.action_settings;
import static com.inventar.nuni.inventari.R.id.transition_current_scene;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SwipeRefreshLayout mySwipeRefreshLayout;
    FloatingActionButton fab;
    private ArrayList<String> input_emer;
    private ArrayList<String> input_id;
    private ArrayList<String> input_njesi;
    private ArrayList<String> input_kategori;
    private ArrayList<String> input_cmim;
    private ArrayList<String> input_data;
    private String info_koha;
    private double koha;
    private String url_kerkuar = "https://dl.dropboxusercontent.com/s/q6hhhxrbcw4u02y/artikulli.txt?dl=0";
    private double koha_fillestare = 120000;
    private int gjatesia2, gjatesia;
    private List<String> id_db;
    private List<String> emri_db;
    private List<String> njesi_db;
    private List<String> kategori_db;
    private List<String> cmim_db;
    private List<String> data_db;
    private boolean lidhja_kthe;
    DatabazeCon mydb = new DatabazeCon(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitleTextColor(Color.WHITE);
        myToolbar.setBackgroundColor(Color.DKGRAY);
        setSupportActionBar(myToolbar);
        startService(new Intent(getBaseContext(), BackgroundService.class));

        //rifreskim
        //dy opsone OSE pas cdo shtimi behet rifreskim automatik,ose rifreskim cdo disa minuta
        //per rifreskim pas cdo shtimi beji uncomment startActivity(new Intent(this,MainActivity.class)); tek klasa ShtoArtikull
        //ndersa per rifreskim automtik cdo 2 minuta + sa here hapet aplikacioni nga fillimi
        rifresko_draw();
        rifresko_nderfaqe(koha_fillestare);

        //hidh ne databaze nga teksti
        try {
            text_to_db();
            gjatesia = id_db.size();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        //recycleview
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);
        //linear-layout

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        //merr info nga databaza
        merr_info();


            //adapteri i recycleview
            mAdapter = new MyAdapter(input_id, input_emer, this, input_njesi, input_kategori, input_cmim, input_data);
            recyclerView.setAdapter(mAdapter);

            //swipe-refresh

            mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
            mySwipeRefreshLayout.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            rifresko_draw();
                            update();
                        }
                    }
            );
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case action_settings:

                Intent i = new Intent(this, Settings.class);
                startActivityForResult(i, 1);
        }

        return super.onOptionsItemSelected(item);
    }

    private void update() {
        try {
            mydb.delete();

            text_to_db();
            gjatesia2 = id_db.size();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        //nqs pas refreshit me e madhe tregon qe jane shtuar te dhena

        if (gjatesia2 > gjatesia) {
            Toast.makeText(MainActivity.this, "Te dhena te reja!", Toast.LENGTH_LONG).show();
        }

        //nese pas refresh gjatesia eshte me e vogel tregon qe ka me pak te dhena tashme ne liste //keshtu qe fshijme db dhe i beje fetch serish
        else if (gjatesia2 < mydb.getTaskCount()) {
            mydb.delete();
            text_to_db();

        } else {
            Toast.makeText(MainActivity.this, "Nuk ka levizje ne te dhena!", Toast.LENGTH_LONG).show();
        }
        gjatesia = gjatesia2;
        Cursor rezultat = mydb.shfaq_info();
        if (rezultat.getCount() == 0) {
            Toast.makeText(this, "Nuk ka te dhena!", Toast.LENGTH_LONG).show();
            return;
        }
        input_id.clear();
        input_emer.clear();
        while (rezultat.moveToNext()) {
            input_id.add("Id Artikullit: " + rezultat.getString(0));
            input_emer.add("Emri Artikullit: " + rezultat.getString(1));
            input_njesi.add("Njesia: " + rezultat.getString(2));
            input_kategori.add("Kategoria: " + rezultat.getString(3));
            input_cmim.add("Cmimi: " + rezultat.getString(4));
            input_data.add("Data: " + rezultat.getString(5));
        }
        // adapteri
        mAdapter = new MyAdapter(input_id, input_emer, this, input_njesi, input_kategori, input_cmim, input_data);
        recyclerView.setAdapter(mAdapter);
        mySwipeRefreshLayout.setRefreshing(false);
    }


    public void text_to_db() {
    if(kontrollo_lidhje()==true) {
        DownloadTask task = new DownloadTask();
        String res = null;
        try {
            res = task.execute(url_kerkuar).get();
            String[] records = res.split(";");
            id_db = new ArrayList<>();
            emri_db = new ArrayList<>();
            njesi_db = new ArrayList<>();
            kategori_db = new ArrayList<>();
            cmim_db = new ArrayList<>();
            data_db = new ArrayList<>();

            for (String record : records) {
                String[] recordData = record.split(",");
                id_db.add(recordData[0]);
                emri_db.add(recordData[1]);
                njesi_db.add(recordData[2]);
                kategori_db.add(recordData[3]);
                cmim_db.add(recordData[4]);
                data_db.add(recordData[5]);
            }
            //mydb.fshi();
            for (int i = 0; i < emri_db.size(); i++) {
                mydb.shto_artikull(id_db.get(i), emri_db.get(i), njesi_db.get(i), kategori_db.get(i), cmim_db.get(i), data_db.get(i));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
     }

    }

    public void merr_info() {
        //inicializim arraylist-e per te marre te dhenat nga databaza
        input_emer = new ArrayList<>();
        input_id = new ArrayList<>();
        input_njesi = new ArrayList<>();
        input_kategori = new ArrayList<>();
        input_cmim = new ArrayList<>();
        input_data = new ArrayList<>();

        //mmarim te dhenat nga db,nqs eshte bosh do shfaqet ky meseazh!
        Cursor rezultat = mydb.shfaq_info();
        if (rezultat.getCount() == 0) {
            Toast.makeText(this, "Nuk ka te dhena!Shtoni ne databaze!", Toast.LENGTH_LONG).show();
            return;
        }
        for (rezultat.moveToFirst(); !rezultat.isAfterLast(); rezultat.moveToNext()) {
            input_id.add("Id Artikullit: " + rezultat.getString(0));
            input_emer.add("Emri Artikullit: " + rezultat.getString(1));
            input_njesi.add("Njesia: " + rezultat.getString(2));
            input_kategori.add("Kategoria: " + rezultat.getString(3));
            input_cmim.add("Cmimi: " + rezultat.getString(4));
            input_data.add("Data: " + rezultat.getString(5));
        }
    }

    public void rifresko_nderfaqe(double koha_funx) {
        final CountDownTimer rifreskim = new CountDownTimer((long) koha_funx, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                update();
                this.start();
            }
        }.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                info_koha = data.getStringExtra("koha");
                koha = Double.parseDouble(info_koha);

                double koha_rifreskim = koha * 60000;
                Log.i("Vlera: ", String.valueOf(koha_rifreskim));
                koha_fillestare = koha_rifreskim;
                Log.i("pas rif koha_fillest ", String.valueOf(koha_fillestare));
                rifresko_nderfaqe(koha_fillestare);
            }
        }
    }

    public boolean kontrollo_lidhje() {
            boolean lidhja = false;
            ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                    || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                lidhja = true;

            } else if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                    || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

                lidhja = false;
            }
            return lidhja;
        }

    public void rifresko_draw(){
        final ProgressDialog dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Ju lutem prisni...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                    if (dialog.isShowing())
                        dialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


    }





