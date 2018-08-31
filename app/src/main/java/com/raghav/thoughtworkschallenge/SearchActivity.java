package com.raghav.thoughtworkschallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;


import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    SearchView searchview;
    Beer beer;
    ArrayList<Beer> arrList_Beer, filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initview();
        //Initiate Recylerview
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        beer = new Beer();
        arrList_Beer = GlobalClass.getBeerArrayList();
        filteredList = new ArrayList<>();
        //Setting Search toolbar
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchedBeerName) {
                searchedBeerName = searchedBeerName.toString().toLowerCase();

                filteredList.clear();

                for (int i = 0; i < arrList_Beer.size(); i++) {

                    final String beerName = arrList_Beer.get(i).getName().toLowerCase();

                    if (beerName.contains(searchedBeerName)) {

                        filteredList.add(arrList_Beer.get(i));
                    }
                }

                GlobalClass.listUpdate(recyclerView,filteredList,SearchActivity.this);
                return false;
            }
        });
    }

    private void initview() {
        recyclerView = findViewById(R.id.search_recycler_view);
        searchview = findViewById(R.id.searchView);
    }
}
