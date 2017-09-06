package com.inventar.nuni.inventari;

import java.util.ArrayList;

/**
 * Created by n.telo on 9/6/2017.
 */

public class MerrInfo {
    String input_emer,input_id,input_njesi,input_kategori,input_cmim,input_data;


    public MerrInfo(String input_emer, String input_id, String input_njesi, String input_kategori, String input_cmim, String input_data) {
        this.input_emer = input_emer;
        this.input_id = input_id;
        this.input_njesi = input_njesi;
        this.input_kategori = input_kategori;
        this.input_cmim = input_cmim;
        this.input_data = input_data;
    }

    public String getInput_emer() {
        return input_emer;
    }

    public String getInput_id() {
        return input_id;
    }

    public String getInput_njesi() {
        return input_njesi;
    }

    public String getInput_kategori() {
        return input_kategori;
    }

    public String getInput_cmim() {
        return input_cmim;
    }

    public String getInput_data() {
        return input_data;
    }

}
