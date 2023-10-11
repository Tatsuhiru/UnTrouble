package com.example.untrouble;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchView extends AppCompatActivity {
    TextView title,desk,desk2,link;
    String index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        title=findViewById(R.id.judul);
        desk=findViewById(R.id.deskripsi);
        desk2=findViewById(R.id.deskripsi2);

        int position=getIntent().getIntExtra("position",1);

        title.setText(com.example.untrouble.SearchActivity.list.get(position).getJudul());
        desk.setText(com.example.untrouble.SearchActivity.list.get(position).getDeskripsi());
        desk2.setText(com.example.untrouble.SearchActivity.list.get(position).getDeskripsi2());
        index = com.example.untrouble.SearchActivity.list.get(position).getIndex();

        String video00 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/1-xGerv5FOk?si=yYhh5qVU9tzoG5W_\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        String video01 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/BGpzGu9Yp6Y?si=M8P_jas8bX0Uf5Uw\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        String video02 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/dhYOPzcsbGM?si=hIfAh7LH4KlCEBx1\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        String video03 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/60ItHLz5WEA?si=O45eyMjxDtd4DPKv\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        String video04 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/HhjHYkPQ8F0?si=4FvLz5bIjSMc3RXN\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        String video05 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/vjexVOf-s2Y?si=whlEurnpVNMRPLit\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        String video06 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/hdonNbzHHXE?si=u4jNv0ksJSvQgo70\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        String video07 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/Az-mGR-CehY?si=wah2u4aHx_4mtmNl\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        String video08 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/YQRHrco73g4?si=PWeWXFhcYFxZtRH1\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        String video09 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/hK9mMhcLdiY?si=oRn1pJqNiIbDbEE2\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        String video10 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/wJnBTPUQS5A?si=V8PK809Lz8ZDqqyp\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        String video99 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/y6120QOlsfU?si=d4jwJWm2foW0qXed\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

        switch(Integer.parseInt(index)) {
            case 0:
                webView.loadData(video00, "text/html","utf-8");
                break;
            case 1:
                webView.loadData(video01, "text/html","utf-8");
                break;
            case 2:
                webView.loadData(video02, "text/html","utf-8");
                break;
            case 3:
                webView.loadData(video03, "text/html","utf-8");
                break;
            case 4:
                webView.loadData(video04, "text/html","utf-8");
                break;
            case 5:
                webView.loadData(video05, "text/html","utf-8");
                break;
            case 6:
                webView.loadData(video06, "text/html","utf-8");
                break;
            case 7:
                webView.loadData(video07, "text/html","utf-8");
                break;
            case 8:
                webView.loadData(video08, "text/html","utf-8");
                break;
            case 9:
                webView.loadData(video09, "text/html","utf-8");
                break;
            case 10:
                webView.loadData(video10, "text/html","utf-8");
                break;
            default:
                webView.loadData(video99, "text/html","utf-8");
                break;
        }
    }
}