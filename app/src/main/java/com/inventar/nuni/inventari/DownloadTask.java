package com.inventar.nuni.inventari;


import android.os.AsyncTask;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DownloadTask extends AsyncTask<String, Void, String> {


    @Override
    //string...params -> mban 1 ose nje liste me url qe ne duam te shkarkojme

    protected String doInBackground(String... urls) {

        String rez = " ";

        URL url;
        HttpURLConnection urlConnection = null;

        //hapi pare e konvertojme ne url
        try {
            url = new URL(urls[0]);
            // vendosim lidhjen
            urlConnection = (HttpURLConnection) url.openConnection();

            //mban inputin e te dhenava
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            while (scanner.hasNext()) {
                rez += scanner.nextLine();
            }
            return rez;

        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null) {
        }
    }
}