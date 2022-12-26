package com.example.untrouble;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

public class tabhome extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.tabhome,container, false);
        WebView web;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        web = v.findViewById(R.id.webtutor2);
        web.loadUrl("file:///android_asset/tutorial2.html");
        WebSettings webSettings = web.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        web.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_INSET);
        web.setScrollbarFadingEnabled(false);
        return v;
    }

}