package com.raghav.thoughtworkschallenge;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class GlobalClass extends Application {

    public static ArrayList<Beer> getBeerArrayList() {
        return beerArrayList;
    }


    public static void setBeerArrayList(ArrayList<Beer> beerArrayList) {
        GlobalClass.beerArrayList = beerArrayList;
    }

    private static ArrayList<Beer> beerArrayList = new ArrayList<>();

    public static String getSortingOrder() {
        return sortingOrder;
    }

    public static void setSortingOrder(String sortingOrder) {
        GlobalClass.sortingOrder = sortingOrder;
    }

    private static String sortingOrder;

    public static boolean isAlphabeticallySort() {
        return alphabeticallySort;
    }

    public static void setAlphabeticallySort(boolean alphabeticallySort) {
        GlobalClass.alphabeticallySort = alphabeticallySort;
    }

    private static boolean alphabeticallySort = false;

    static public void listUpdate(RecyclerView recyclerView,ArrayList<Beer> arrayList, Context ctx){

        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        RecyclerView.Adapter mAdapter = new CustomBeerAdapter(arrayList, ctx);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }
}
