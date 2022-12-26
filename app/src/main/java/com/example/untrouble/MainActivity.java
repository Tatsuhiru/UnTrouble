package com.example.untrouble;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
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
                    startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.reminder:
                    startActivity(new Intent(getApplicationContext(), com.example.untrouble.ReminderActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.home:
                    return true;
            }
            finish();
            return false;
        });

        final MediaPlayer buttonSound = MediaPlayer.create(this, R.raw.button_sound);

        Button button1 = findViewById(R.id.buttontutorial);
        button1.setOnClickListener(v -> {
            buttonSound.start();
            openTutorial();
        });

        Button button2 = findViewById(R.id.buttonabout);
        button2.setOnClickListener(v -> {
            openAbout();
            buttonSound.start();
        });

        Button button3 = findViewById(R.id.buttonexit);
        button3.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void openAbout() {
        Intent intent = new Intent(this, com.example.untrouble.about.class);
        startActivity(intent);
    }

    public void openTutorial(){
        Intent intent = new Intent(this, com.example.untrouble.tutorial.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        final MediaPlayer errorSound = MediaPlayer.create(this, R.raw.error_sound);
        final MediaPlayer buttonSound = MediaPlayer.create(this, R.raw.button_sound);
        errorSound.start();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!!");
        builder.setIcon(android.R.drawable.stat_sys_warning);
        builder.setMessage("Keluar Dari Aplikasi?");
        builder.setPositiveButton("YES", (dialog, which) -> {
            finishAffinity();
            System.exit(0);
            buttonSound.start();
        });
        builder.setNegativeButton("NO", (dialog, which) -> {
            dialog.cancel();
            buttonSound.start();
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

}