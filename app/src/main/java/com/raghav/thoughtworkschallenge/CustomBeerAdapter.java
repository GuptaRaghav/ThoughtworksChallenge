package com.raghav.thoughtworkschallenge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomBeerAdapter extends RecyclerView.Adapter<CustomBeerAdapter.BeerRowViewHolder> {

    private ArrayList<Beer> dataSet;

    Context context;


    public static class BeerRowViewHolder extends RecyclerView.ViewHolder {

        TextView tv_beerName;
        TextView tv_styleName;
        TextView tv_Abv;

        public BeerRowViewHolder(View itemView) {
            super(itemView);
            this.tv_beerName = itemView.findViewById(R.id.tv_beerName);
            this.tv_styleName = itemView.findViewById(R.id.tv_styleName);
            this.tv_Abv = itemView.findViewById(R.id.tv_Abv);
        }
    }

    public CustomBeerAdapter(ArrayList<Beer> data,Context ctx) {
        this.dataSet = data;
        this.context = ctx;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public CustomBeerAdapter.BeerRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_beer_item, parent, false);

        BeerRowViewHolder beerRowViewHolder = new BeerRowViewHolder(view);
        return beerRowViewHolder;

    }

    @Override
    public void onBindViewHolder(CustomBeerAdapter.BeerRowViewHolder holder, int position) {

        TextView tv_beerName = holder.tv_beerName;
        TextView tv_styleName  = holder.tv_styleName;
        TextView tv_Abv = holder.tv_Abv;

        tv_beerName.setText(dataSet.get(position).getName());
        tv_beerName.setSingleLine();
        tv_styleName.setText(dataSet.get(position).getStyle());
        tv_styleName.setSingleLine();
        tv_Abv.setText(dataSet.get(position).getAbv());
    }


}
