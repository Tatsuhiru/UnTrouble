package com.example.untrouble;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.search:
                    startActivity(new Intent(this, SearchActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.reminder:
                    startActivity(new Intent(this, ReminderActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.home:
                    return true;
            }
            finish();
            return false;
        });

        findViewById(R.id.buttonquiz).setOnClickListener(this::openQuiz);
        findViewById(R.id.buttonabout).setOnClickListener(this::openAbout);
        findViewById(R.id.buttonexit).setOnClickListener(view -> onBackPressed());
    }

    private void openQuiz(View view) {
        startActivity(new Intent(this, Quiz.class));
        playButtonSound();
    }

    private void openAbout(View view) {
        startActivity(new Intent(this, About.class));
        playButtonSound();
    }

    private void playButtonSound() {
        MediaPlayer buttonSound = MediaPlayer.create(this, R.raw.button_sound);
        buttonSound.start();
    }

    @Override
    public void onBackPressed() {
        MediaPlayer errorSound = MediaPlayer.create(this, R.raw.error_sound);
        errorSound.start();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!!");
        builder.setIcon(android.R.drawable.stat_sys_warning);
        builder.setMessage("Keluar Dari Aplikasi?");
        builder.setPositiveButton("YES", (dialog, which) -> {
            finishAffinity();
            System.exit(0);
            playButtonSound();
        });
        builder.setNegativeButton("NO", (dialog, which) -> {
            dialog.cancel();
            playButtonSound();
        });
        builder.create().show();
    }
}