package com.inventar.nuni.inventari;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class BackgroundService extends Service {
    private List<String> id_db,emri_db,njesi_db,kategori_db,cmim_db,data_db;
    private String url_kerkuar = "https://dl.dropboxusercontent.com/s/q6hhhxrbcw4u02y/artikulli.txt?dl=0";
    DatabazeCon mydb = new DatabazeCon(this);


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        final CountDownTimer rifreskim = new CountDownTimer((long) 10000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {

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

                this.start();
            }
        }.start();


        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
