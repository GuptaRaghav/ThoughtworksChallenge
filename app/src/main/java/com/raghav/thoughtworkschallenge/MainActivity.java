package com.raghav.thoughtworkschallenge;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class MainActivity extends AppCompatActivity {

    ArrayList<Beer> arrList_Beer,sortedList_Beer,filteredList_Beer;
    ArrayList<String> arr_AllBeerStyle,arr_DistinctBeerStyle,arr_FilterBeerType;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    Beer beer;
    Toolbar toolbar;
    BottomNavigationView bottom_navigation;
    CharSequence[] sortingorder = {"Ascending","Descending"};
    boolean[] checkedBeerStyle;
    AlertDialog alertDialog = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_appbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_search:
                Intent searchIntent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(searchIntent);
                return true;
            case R.id.action_cart:
               /* Intent wirelessIntent = new Intent("android.settings.WIRELESS_SETTINGS");
                startActivity(wirelessIntent);*/
                return true;

    }
 return super.onOptionsItemSelected(item);
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrList_Beer = new ArrayList<>();
        arr_AllBeerStyle = new ArrayList<>();
        arr_DistinctBeerStyle = new ArrayList<>();
        arr_FilterBeerType = new ArrayList<>();
        filteredList_Beer = new ArrayList<>();
        initview();

        getData();

        //Initiate Toolbar
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        //Initiate Recylerview
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        //BottomNavigationView
        bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.menu_list:
                        sortAlphabetically();
                        return true;
                    case R.id.menu_sort:
                        createsortDialog();
                        return true;
                    case R.id.menu_filter:
                        createfilterDialog();
                        return true;

                }
                return false;

            }
        });
    }
    private void createfilterDialog(){

        getBeerStyle();
        createstyleselector();
        arr_FilterBeerType.clear();
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("Filter result by Beer style")
                .setMultiChoiceItems(arr_DistinctBeerStyle.toArray(new CharSequence[arr_DistinctBeerStyle.size()]),checkedBeerStyle,new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {

                        checkedBeerStyle[which] = isChecked;
                        arr_FilterBeerType.add(arr_DistinctBeerStyle.get(which));
                        // Get the current focused item
                        //To select particular style and add it in style filter
                        // /*String currentItem = DistinctBeerStyle.g*/
                    }
                })
                .setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            filteredList_Beer.clear();
                            for (String style : arr_FilterBeerType) {

                                for (Beer beer : GlobalClass.getBeerArrayList()) {

                                    if (style.equalsIgnoreCase(beer.getStyle())) {
                                        filteredList_Beer.add(beer);
                                    }

                                }
                            }
                            GlobalClass.listUpdate(recyclerView, filteredList_Beer, MainActivity.this);
                        }catch (Exception e){
                            Log.e("Exception Here",e.getMessage());
                        }
                    }
                })
                .create();
        alertDialog.show();
    }
    private void createstyleselector(){
        checkedBeerStyle = new boolean[arr_DistinctBeerStyle.size()];
        for(int i=0;i<arr_DistinctBeerStyle.size();i++){
            checkedBeerStyle[i] = false;
        }
    }
    private void getBeerStyle(){
        for(Beer beer:GlobalClass.getBeerArrayList()){
            String beerStyle = beer.getStyle();
            arr_AllBeerStyle.add(beerStyle);
        }
        Set<String> treeset_AllBeerStyleList = new TreeSet<String>(arr_AllBeerStyle);
        Iterator<String> itr = treeset_AllBeerStyleList.iterator();
        while (itr.hasNext()) {
            arr_DistinctBeerStyle.add(itr.next());
        }
    }
    private void createsortDialog(){

        alertDialog = new AlertDialog.Builder(this)
                .setTitle("Set Sorting Order By ABV")
                .setSingleChoiceItems(sortingorder, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        switch(item)
                        {
                            case 0:
                                GlobalClass.setSortingOrder("Ascending");
                                sortedList_Beer = GlobalClass.getBeerArrayList();
                                Collections.sort(sortedList_Beer, Beer.AbvComparator);
                                GlobalClass.listUpdate(recyclerView,sortedList_Beer,MainActivity.this);

                                break;
                            case 1:
                                GlobalClass.setSortingOrder("Descending");
                                sortedList_Beer = GlobalClass.getBeerArrayList();
                                Collections.sort(sortedList_Beer, Beer.AbvComparator);
                                GlobalClass.listUpdate(recyclerView,sortedList_Beer,MainActivity.this);

                                break;
                        }
                        alertDialog.dismiss();
                    }
                })
                .create();
                alertDialog.show();
    }
    private void getData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getString(R.string.url_api);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,new Response.Listener<JSONArray>(){

                @Override
                public void onResponse(JSONArray response) {
                    JSONArray jsonArray_beer = response;
                    try {
                    for(int i=0;i<jsonArray_beer.length();i++) {
                            beer = new Beer();
                            JSONObject jsonObject_Beer = jsonArray_beer.getJSONObject(i);
                            beer.setName(getValue(jsonObject_Beer,"name"));
                            beer.setStyle(getValue(jsonObject_Beer,"style"));
                            beer.setAbv(getValue(jsonObject_Beer,"abv"));
                            arrList_Beer.add(beer);

                        }
                        GlobalClass.setBeerArrayList(arrList_Beer);

                        /*arrList_Beer.sort();*/
                    }catch (Exception e){
                        Log.e("ExceptiongetingJSONOBJ",e.getMessage());
                    }
                    /*GlobalClass.listUpdate(recyclerView,GlobalClass.getBeerArrayList(),MainActivity.this);*/
                    sortAlphabetically();
                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
// Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }
    private void sortAlphabetically(){
        GlobalClass.setAlphabeticallySort(true);
        sortedList_Beer = GlobalClass.getBeerArrayList();
        Collections.sort(sortedList_Beer, Beer.AbvComparator);
        GlobalClass.listUpdate(recyclerView,sortedList_Beer,MainActivity.this);
        GlobalClass.setAlphabeticallySort(false);
    }
    private String getValue(JSONObject jsonObject_Beer,String Key){
        String Attr = null;
        try {
                Attr = jsonObject_Beer.getString(Key);
            if (Attr.isEmpty() || Attr == null){
                Attr = "NA";
            }
        }catch(Exception e){
            Log.e("ErrorinObj",e.getMessage());
        }
        return Attr;

    }
    public void listUpdate(ArrayList<Beer> arrayList, Context ctx){

        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        RecyclerView.Adapter mAdapter = new CustomBeerAdapter(arrayList, ctx);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }
    private void initview(){
        recyclerView = findViewById(R.id.main_recycler_view);
        toolbar = findViewById(R.id.action_toolbar);
    }
}
