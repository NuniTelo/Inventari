package com.inventar.nuni.inventari;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by n.telo on 9/6/2017.
 */

public class Items {
   String id_db;
    String emri_db;
    String njesi_db;
    String kategori_db;
    String cmim_db;
    String data_db;



    public Items(String id_db, String emri_db, String njesi_db, String kategori_db, String cmim_db, String data_db) {
        this.id_db = id_db;
        this.emri_db = emri_db;
        this.njesi_db = njesi_db;
        this.kategori_db = kategori_db;
        this.cmim_db = cmim_db;
        this.data_db = data_db;
    }

    public String getId_db() {

        return id_db;
    }

    public String getEmri_db() {
        return emri_db;
    }


    public String getNjesi_db() {
        return njesi_db;
    }

    public String getKategori_db() {
        return kategori_db;
    }

    public String getCmim_db() {
        return cmim_db;
    }

    public String getData_db() {
        return data_db;
    }






}


