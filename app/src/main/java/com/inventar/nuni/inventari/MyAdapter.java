package com.inventar.nuni.inventari;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inventar.nuni.inventari.info.ItemInfoMActivity;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<String> values;
    private ArrayList<String> emr;
    private ArrayList<String>njesi;
    private ArrayList<String>kategori;
    private ArrayList<String>cmim;
    private ArrayList<String>data;
    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {
        // cdoe d dhene eshte string
        public TextView id;
        public TextView emer;
        public View layout;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            id = (TextView) v.findViewById(R.id.shitje_rreshti);
            emer = (TextView) v.findViewById(R.id.emer_rreshti);
        }
    }

    public MyAdapter(ArrayList<String> myInfoId, ArrayList<String> emri, Context context,ArrayList<String> njesi,ArrayList<String> kategori,ArrayList<String> cmim,ArrayList<String> data) {
        values = myInfoId;
        emr=emri;
        this.njesi=njesi;
        this.kategori=kategori;
        this.cmim=cmim;
        this.data=data;
        this.context=context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.rreshti_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //pjesa kryeseore e shfaqjes se te dhenave

        final String idd = values.get(position);
        holder.id.setText("Id Artikullit: "+idd);

        final String emri_id= emr.get(position);
        holder.emer.setText("Emri Artikullit: "+emri_id);

        holder.emer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(context,ItemInfoMActivity.class);
                it.putExtra("id",values.get(position));
                it.putExtra("pozicioni",position);
                context.startActivity(it);

            }
        });

            }

            @Override
            public int getItemCount() {
                return values.size();
            }

        }