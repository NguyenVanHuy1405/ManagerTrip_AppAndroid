package com.example.final_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Database db;
    ArrayList<dataSearch> dataSearches = new ArrayList<>();
    SearchView searchView;
    RecyclerView recyclerView;
    private SearchAdapter adapter;
    private EditText eFrom, eTo;
    private Button btnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.recyclerviewSearch);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.search:
                        return true;

                    case R.id.upload:
                        startActivity(new Intent(getApplicationContext(), UploadActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        recyclerView= findViewById(R.id.recyclerviewSearch);
        btnSearch = findViewById(R.id.btnSearch);
        searchView =findViewById(R.id.searchView);
        eFrom =findViewById(R.id.eFrom);
        eTo =findViewById(R.id.eTo);

        adapter = new SearchAdapter(SearchActivity.this);
        db = new Database(SearchActivity.this);
        List<dataSearch> list = db.getAll();
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<dataSearch> list=db.searchByKey(s);
                adapter.setList(list);
                return true;
            }
        });
//        search = findViewById(R.id.searchView);
//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });
//        displayData();
//        searchAdapter = new SearchAdapter(SearchActivity.this,dataSearches);
//        recyclerView.setAdapter(searchAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
//    }
//    void displayData(){
//        Cursor cursor = myData.readAllData();
//        if(cursor.getCount() == 0){
//            Toast.makeText(SearchActivity.this,"No data you want to search", Toast.LENGTH_LONG).show();
//        }else{
//            while (cursor.moveToNext()){
//                dataSearches.add(new dataSearch(cursor.getString(0),
//                       cursor.getString(1),
//                       cursor.getString(2),
//                       cursor.getString(3),
//                       cursor.getString(4),
//                        cursor.getString(4)));
//            }
//        }
//    }
    }
}