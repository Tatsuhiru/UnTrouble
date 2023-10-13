package com.example.untrouble;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> implements Filterable {
    Context context;
    List<SearchData> data;
    List<SearchData> alldata;

    public Adapter(Context context, List<SearchData> data){
        this.context = context;
        this.data = data;
        this.alldata = new ArrayList<>(data);
    }

    @NotNull
    public Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list, parent, false);
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.image.setImageResource(data.get(position).getImg());
        holder.judul.setText(data.get(position).getJudul());
        holder.desc.setText(data.get(position).getMinidesk());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SearchView.class);
            intent.putExtra("position",position);
            context.startActivity(intent);
        });
    }
    public int getItemCount(){return data.size();}

    public Filter getFilter(){return Filterr;}

    private Filter Filterr=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String search=constraint.toString().toLowerCase();
            List<SearchData> temp=new ArrayList<>();
            if(search.length() == 0){
                temp.addAll(alldata);
            }else {
                for (SearchData item:alldata){
                    if(item.getJudul().toLowerCase().contains(search)){
                        temp.add(item);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=temp;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data.clear();
            data.addAll((Collection<? extends SearchData>) results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView judul, desc;

        public MyViewHolder(View itemView){
            super(itemView);
            image=itemView.findViewById(R.id.listImage);
            judul=itemView.findViewById(R.id.listTitle);
            desc=itemView.findViewById(R.id.listDesc);
        }
    }
}
