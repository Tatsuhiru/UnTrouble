package com.example.untrouble;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<com.example.untrouble.ModelData> arrayList;

    public ItemAdapter(Context context, ArrayList<com.example.untrouble.ModelData> arrayList) {
        super();
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        convertView = layoutInflater.inflate(R.layout.daftar_todo, null);
        TextView titleTextView = convertView.findViewById(R.id.title);
        TextView dateTextView = convertView.findViewById(R.id.dateTitle);
        TextView timeTextView = convertView.findViewById(R.id.timeTitle);
        final ImageView delImageView = convertView.findViewById(R.id.delete);
        delImageView.setTag(position);

        //Menghapus tugas dari database saat icon hapus di klik
        delImageView.setOnClickListener(v -> {
            final int pos = (int) v.getTag();
            deleteItem(pos);
        });

        com.example.untrouble.ModelData modelData = arrayList.get(position);
        titleTextView.setText(modelData.getTitle());
        dateTextView.setText(modelData.getDate());
        timeTextView.setText(modelData.getTime());
        return convertView;
    }

    //Menghapus tugas dari listview
    private void deleteItem(int position) {
        deleteItemFromDb(arrayList.get(position).getId());
        arrayList.remove(position);
        notifyDataSetChanged();
    }

    //Menghapus tugas dari database
    private void deleteItemFromDb(int id) {
        com.example.untrouble.DatabaseHelper databaseHelper = new com.example.untrouble.DatabaseHelper(context);
        try {
            databaseHelper.deleteData(id);
            toastMsg("Tugas berhasil di hapus");
        } catch (Exception e) {
            e.printStackTrace();
            toastMsg("Ada kesalahan saat menghapus tugas");
        }
    }

    //Metode pesan toast
    private void toastMsg(String msg) {
        Toast t = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER,0,0);
        t.show();
    }
}
