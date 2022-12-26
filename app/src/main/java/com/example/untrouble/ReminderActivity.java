package com.example.untrouble;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.util.Calendar.MINUTE;

public class ReminderActivity extends AppCompatActivity {

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";
    private static final String TAG = "ReminderActivity";
    private com.example.untrouble.DatabaseHelper databaseHelper;
    private ListView itemsListView;
    private FloatingActionButton fab;
    private final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.3F);
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.reminder);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {


            switch (item.getItemId()) {
                case R.id.search:
                    startActivity(new Intent(getApplicationContext(), com.example.untrouble.SearchActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.reminder:
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });

        databaseHelper = new com.example.untrouble.DatabaseHelper(this);
        fab = findViewById(R.id.fab);
        itemsListView = findViewById(R.id.itemsList);

        populateListView();
        onFabClick();
        hideFab();
    }

    //Mengatur notifikasi
    private void scheduleNotification(Notification notification, long delay) {
        Intent notificationIntent = new Intent(this, com.example.untrouble.Notifikasi.class);
        notificationIntent.putExtra(com.example.untrouble.Notifikasi.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(com.example.untrouble.Notifikasi.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getLayoutInflater().getContext().getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, delay, pendingIntent);
        }
    }

    private Notification getNotification(String content) {

        //Saat notifikasi di klik di arahkan ke MainActivity
        Intent intent = new Intent(this, ReminderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getLayoutInflater().getContext(), default_notification_channel_id);
        builder.setContentTitle("Reminder");
        builder.setContentText(content);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.ic_notif);
        builder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        return builder.build();
    }

    //Memasukkan data ke database
    private void insertDataToDb(String title, String date, String time) {
        boolean insertData = databaseHelper.insertData(title, date, time);
        if (insertData) {
            try {
                populateListView();
                toastMsg("Pengingat berhasil dibuat");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            toastMsg("Ada masalah saat membuat pengingat");
    }

    //Mengambil seluruh data dari database ke listview
    private void populateListView() {
        try {
            ArrayList<ModelData> items = databaseHelper.getAllData();
            ItemAdapter itemsAdopter = new ItemAdapter(this, items);
            itemsListView.setAdapter(itemsAdopter);
            itemsAdopter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Menyembunyikan tombol floating tambah saat listview di scroll
    private void hideFab() {
        itemsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    fab.show();
                }else{
                    fab.hide();
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    private void onFabClick() {
        final MediaPlayer buttonSound = MediaPlayer.create(this, R.raw.button_sound);
        try {
            fab.setOnClickListener(v -> {
                v.startAnimation(buttonClick);
                showAddDialog();
                buttonSound.start();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Implementasi klik dari tombol tambah
    @SuppressLint("SimpleDateFormat")
    private void showAddDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getLayoutInflater().getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams")
        final View dialogView = inflater.inflate(R.layout.custom_dialog_todo, null);
        dialogBuilder.setView(dialogView);

        final EditText judul = dialogView.findViewById(R.id.edit_title);
        final TextView tanggal = dialogView.findViewById(R.id.date);
        final TextView waktu = dialogView.findViewById(R.id.time);
        final MediaPlayer buttonSound = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer errorSound = MediaPlayer.create(this, R.raw.error_sound);


        final long date = System.currentTimeMillis();
        SimpleDateFormat dateSdf = new SimpleDateFormat("d MMMM");
        String dateString = dateSdf.format(date);
        tanggal.setText(dateString);

        SimpleDateFormat timeSdf = new SimpleDateFormat("hh : mm a");
        String timeString = timeSdf.format(date);
        waktu.setText(timeString);

        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        //Set tanggal
        tanggal.setOnClickListener(v -> {
            final DatePickerDialog datePickerDialog = new DatePickerDialog(getLayoutInflater().getContext(),
                    (view, year, monthOfYear, dayOfMonth) -> {
                        String newMonth = getMonth(monthOfYear + 1);
                        tanggal.setText(dayOfMonth + " " + newMonth);
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMinDate(date);
        });

        //Set waktu
        waktu.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getLayoutInflater().getContext(),
                    (view, hourOfDay, minute) -> {
                        String time;
                        @SuppressLint("DefaultLocale") String minTime = String.format("%02d", minute);
                        if (hourOfDay >= 0 && hourOfDay < 12) {
                            time = hourOfDay + " : " + minTime + " AM";
                        } else {
                            if (hourOfDay != 12) {
                                hourOfDay = hourOfDay - 12;
                            }
                            time = hourOfDay + " : " + minTime + " PM";
                        }
                        waktu.setText(time);
                        cal.set(Calendar.HOUR, hourOfDay);
                        cal.set(Calendar.MINUTE, minute);
                        cal.set(Calendar.SECOND, 0);
                        Log.d(TAG, "onTimeSet: Time has been set successfully");
                    }, cal.get(Calendar.HOUR), cal.get(MINUTE), false);
            timePickerDialog.show();
        });

        dialogBuilder.setTitle("Buat pengingat baru");
        dialogBuilder.setPositiveButton("Tambah", (dialog, whichButton) -> {
            String title = judul.getText().toString();
            String date1 = tanggal.getText().toString();
            String time = waktu.getText().toString();
            buttonSound.start();
            if (title.length() != 0) {
                try {
                    insertDataToDb(title, date1, time);
                    scheduleNotification(getNotification(title), cal.getTimeInMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                toastMsg("Oops, Judul tugas harus diisi.");
                errorSound.start();
            }
        });
        dialogBuilder.setNegativeButton("Batal", (dialog, whichButton) -> {
            dialog.cancel();
            errorSound.start();
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    //Metode pesan toast
    private void toastMsg(String msg) {
        Toast t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER, 0,0);
        t.show();
    }

    //Mengkonversi bulan dari huruf menjadi angka
    private String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
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