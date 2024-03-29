package com.example.untrouble;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public static List<SearchData> list = new ArrayList<>();
    com.example.untrouble.Adapter adapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupBottomNavigation();
        setupRecyclerView();
        populateRecyclerView();
    }

    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.search:
                    return true;
                case R.id.reminder:
                    startActivity(new Intent(getApplicationContext(), ReminderActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return true;
        });
    }

    private void setupRecyclerView() {
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void populateRecyclerView() {
        list.clear();

        String[] aktivitas = getResources().getStringArray(R.array.aktivitas);
        String[] deskripsi = getResources().getStringArray(R.array.deskripsi);
        String[] deskripsi2 = getResources().getStringArray(R.array.deskripsi2);
        String[] minidesk = getResources().getStringArray(R.array.minidesk);
        String[] index = getResources().getStringArray(R.array.index);
        int[] gambar = {
                R.raw.a_cleanup,
                R.raw.b_scan,
                R.raw.c_update,
                R.raw.d_temp,
                R.raw.e_backup,
                R.raw.f_casing,
                R.raw.g_keyboard,
                R.raw.h_monitor,
                R.raw.i_fan,
                R.raw.j_mobo,
                R.raw.k_pasta
        };

        for (int g=0;g< index.length;g++){
            SearchData data = new SearchData(gambar[g], aktivitas[g],deskripsi[g],deskripsi2[g],minidesk[g],index[g]);
            list.add(data);
        }

        adapter = new Adapter(this,list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.tombolcari);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                if (adapter.getItemCount() == 0) {
                    Toast.makeText(SearchActivity.this, "Item yang Anda cari tidak ditemukan.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}