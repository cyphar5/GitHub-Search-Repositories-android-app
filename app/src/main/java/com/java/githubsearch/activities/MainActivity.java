package com.java.githubsearch.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.java.githubsearch.R;
import com.java.githubsearch.adapters.RepositoryAdapter;
import com.java.githubsearch.model.RepoObject;
import com.java.githubsearch.model.Repository;
import com.java.githubsearch.rest.ApiClient;
import com.java.githubsearch.rest.api_calls.GetDefaultRepo;
import com.java.githubsearch.utility.Constants;
import com.java.githubsearch.utility.Util;
import com.lapism.searchview.SearchView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements Callback<RepoObject>, View.OnClickListener {

    RepositoryAdapter repositoryAdapter;
    RecyclerView recyclerView;
    SearchView searchView;
    private Switch switchStars, switchForks , switchUpdatedOn , switchDesending ;
    private TextView startDate , endDate , clearFilters ,saveFilters;
    Map<String, String> data = new HashMap<>();
    ImageView crossButton;

    private  RelativeLayout filterContainer;
    Calendar myCalendar = Calendar.getInstance();
    private String url="";
    private boolean shown = true;
    private String start = "" ,end = "";
    private ImageView filterView , filterImage ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.search_adapter);
         searchView = findViewById(R.id.searchView);
        searchView.setHint(R.string.search_repositories);

        filterContainer = findViewById(R.id.am_filter);
        switchStars = findViewById(R.id.fl_stars);
        switchForks = findViewById(R.id.fl_forks);
        switchUpdatedOn = findViewById(R.id.fl_updated_on);
        switchDesending = findViewById(R.id.fl_desending);
        startDate = findViewById(R.id.fl_sd);
        startDate.setOnClickListener(this);
        endDate = findViewById(R.id.fl_ed);
        endDate.setOnClickListener(this);
        crossButton = findViewById(R.id.fl_cross);
        crossButton.setOnClickListener(this);
        clearFilters = findViewById(R.id.fl_clear_all);
        clearFilters.setOnClickListener(this);
        saveFilters = findViewById(R.id.fl_apply_all);
        saveFilters.setOnClickListener(this);
        filterImage = findViewById(R.id.am_filter_image);

        filterView = findViewById(R.id.am_filter_image);
        filterContainer.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setNestedScrollingEnabled(false);

        url =""+  (char)(ThreadLocalRandom.current().nextInt(65, 90 + 1));
        data.put("q",url);
        if(Util.isNetWorkAvailable(this))
        fetchingRepositories(data);
        else {
            Toast.makeText(this, "Internet not available", Toast.LENGTH_LONG).show();

        }

        filterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shown) {
                    recyclerView.setVisibility(View.GONE);
                    shown = false;
                    filterContainer.setVisibility(View.VISIBLE);
                }
                else{
                    recyclerView.setVisibility(View.VISIBLE);
                    shown = true;
                    filterContainer.setVisibility(View.GONE);
                }
            }
        });

        searchView.setVoiceIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Map<String,String> map = getFilters();
                String result = filterByDate();
                String finalQuery = query + result;
                map.put("q",finalQuery);
                url = query;
                if(searchView.isOpen()){
                    searchView.close(true);
                    searchView.clearFocus();
                    searchView.getShouldClearOnClose();
                    searchView.removeFocus();

                }
                fetchingRepositories(map);
                recyclerView.setVisibility(View.VISIBLE);

                searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(hasFocus)
                            filterView.setVisibility(View.GONE);
                        else
                            filterView.setVisibility(View.VISIBLE);

                    }
                });

                searchView.setOnOpenCloseListener(new SearchView.OnOpenCloseListener() {
                    @Override
                    public boolean onClose() {
                        filterView.setVisibility(View.VISIBLE);
                            return false;
                    }

                    @Override
                    public boolean onOpen() {
                        filterView.setVisibility(View.GONE);
                        return false;
                    }
                });
                return true;
            }
        });


        switchStars.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchStars.isChecked()) {
                    switchStars.setChecked(true);
                    switchForks.setChecked(false);
                    switchUpdatedOn.setChecked(false);
                }
            }
        });

        switchForks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchForks.isChecked()) {
                    switchStars.setChecked(false);
                    switchForks.setChecked(true);
                    switchUpdatedOn.setChecked(false);
                }
            }
        });

        switchUpdatedOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchUpdatedOn.isChecked()) {
                    switchStars.setChecked(false);
                    switchForks.setChecked(false);
                    switchUpdatedOn.setChecked(true);
                }
            }
        });


    }

    public void fetchingRepositories(Map<String,String> lmap) {
        //  showDialog();
        searchView.showProgress();
        GetDefaultRepo apiService = ApiClient.getClient().create(GetDefaultRepo.class);
        Call<RepoObject> call = apiService.getRepository(lmap);
        call.enqueue(MainActivity.this);
        searchView.showProgress();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onResponse(Call<RepoObject> call, Response<RepoObject> response) {
        Log.v("RESPONSE", response.toString());
            searchView.hideProgress();
        if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
            RepoObject repo = response.body();
            ArrayList<Repository> list = repo.getItems();
            if (list != null && list.size() > 0) {
                Util.hideEmptyContentLayout(this);
                repositoryAdapter = new RepositoryAdapter(this, list);
                recyclerView.setAdapter(repositoryAdapter);
            } else {
                Toast.makeText(this,"No Data Found", Toast.LENGTH_LONG).show();
                recyclerView.setVisibility(View.GONE);
            }

        }

    }


    @Override
    public void onFailure(Call<RepoObject> call, Throwable t) {
        Log.v("RESPONSE", "error");
        searchView.hideProgress();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fl_cross:
                shown = true;
                filterContainer.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                break;

            case R.id.fl_sd:
                new DatePickerDialog(this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case R.id.fl_ed:
                new DatePickerDialog(this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case R.id.fl_clear_all:
                clearFilters();
                break;

            case R.id.am_filter_image:
                if(shown) {
                    recyclerView.setVisibility(View.GONE);
                    shown = false;
                    filterContainer.setVisibility(View.VISIBLE);
                }
                else{
                    recyclerView.setVisibility(View.VISIBLE);
                    shown = true;
                    filterContainer.setVisibility(View.GONE);
                }
                break;

            case R.id.fl_apply_all:
                recyclerView.setVisibility(View.VISIBLE);
                shown = true;
                filterContainer.setVisibility(View.GONE);
                Map<String,String> map = getFilters();
                String result = filterByDate();
                String query = searchView.getQuery().toString();
                if(query.length()>0){}
                else
                    query=url;
                String finalQuery =  query + result;
                map.put("q",finalQuery);
                if(searchView.isOpen()){
                    searchView.removeFocus();
                    searchView.close(true);
                }
                fetchingRepositories(map);

        }
    }

    public Map<String,String> getFilters(){
        Map<String,String> map = new HashMap<>();
        if(switchStars.isChecked()){
            map.put("sort","stars");
        }
        else if(switchForks.isChecked()){
            map.put("sort","forks");
        }
        else if(switchUpdatedOn.isChecked()){
            map.put("sort","fork");
        }

        if(switchDesending.isChecked()){
            map.put("order","asc");
        }

        else
            map.put("order","desc");

        return map;
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            monthOfYear++;
            String mon=monthOfYear+"" , day = ""+dayOfMonth ;
            if(mon.length()<2)
                mon = "0"+ monthOfYear;
            if(day.length()<2)
                day = "0" + dayOfMonth;
            end = year+"-"+ mon +"-"+ day;
            start = year+"-"+ mon +"-"+ day;
            startDate.setText(day+"-"+mon +"-"+year);
        }

    };

    DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            monthOfYear++;
            String mon=monthOfYear+"" , day = ""+dayOfMonth ;
            if(mon.length()<2)
                mon = "0"+ monthOfYear;
            if(day.length()<2)
                day = "0" + dayOfMonth;
            end = year+"-"+ mon +"-"+ day;
            start = year+"-"+ mon +"-"+ day;
            endDate.setText(day+"-"+mon+"-"+year);
        }

    };

    public String filterByDate(){
        String query ="" ;
        if(start.length()>0)
            query = " pushed:" + start ;

        if(end.length()>0){
            query = query + ".." + end ;
        }

        return query;

    }


    public void clearFilters(){

        switchStars.setChecked(false);
        switchForks.setChecked(false);
        switchUpdatedOn.setChecked(false);
        startDate.setText("");
        endDate.setText("");
        switchDesending.setChecked(false);
        start = "";
        end = "";
    }

}
