package com.example.untrouble;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchView extends AppCompatActivity {
    ImageView imageView;
    TextView title,desk,desk2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        imageView=findViewById(R.id.gambar);
        title=findViewById(R.id.judul);
        desk=findViewById(R.id.deskripsi);
        desk2=findViewById(R.id.deskripsi2);

        int position=getIntent().getIntExtra("position",1);

        imageView.setImageResource(com.example.untrouble.SearchActivity.list.get(position).getImg());
        title.setText(com.example.untrouble.SearchActivity.list.get(position).getJudul());
        desk.setText(com.example.untrouble.SearchActivity.list.get(position).getDeskripsi());
        desk2.setText(com.example.untrouble.SearchActivity.list.get(position).getDeskripsi2());
    }
}