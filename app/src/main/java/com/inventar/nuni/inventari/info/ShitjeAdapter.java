package com.inventar.nuni.inventari.info;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inventar.nuni.inventari.R;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class ShitjeAdapter extends RecyclerView.Adapter<ShitjeAdapter.ViewHolder> {
    private ArrayList<String> id;
    private ArrayList<String>njesi;
    private ArrayList<String>sasi;
    private ArrayList<String>data;
    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {
        // cdo e dhene eshte string
        public TextView data;
        public TextView shitje;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            data = (TextView) v.findViewById(R.id.data_rreshti);
            shitje = (TextView) v.findViewById(R.id.shitje_rreshti);
        }
    }

    public ShitjeAdapter(ArrayList<String> id, ArrayList<String> njesi, Context context, ArrayList<String>sasi, ArrayList<String> data) {
        this.id=id;
        this.njesi=njesi;
        this.sasi=sasi;
        this.data=data;
        this.context=context;

    }

    @Override
    public ShitjeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.rreshti_shitje, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //pjesa kryeseore e shfaqjes se te dhenave
        final String idd = data.get(position);
        holder.data.setText("DATA: "+idd);

        final String emri_id= njesi.get(position);
        holder.shitje.setText("SHITJE: "+emri_id);

    }

    @Override
    public int getItemCount() {
        return id.size();
    }


}