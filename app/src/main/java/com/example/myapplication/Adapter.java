package com.example.myapplication;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<ItemsModel> itemsModelList;
    private List<ItemsModel> itemsModelListFiltered;
    String type;

    public Adapter(List<ItemsModel> itemsModelList, Context context, String type) {
        this.itemsModelList = itemsModelList;
        this.itemsModelListFiltered = itemsModelList;
        this.context = context;
        this.type = type;
    }

    @Override
    public int getCount() {
        return itemsModelListFiltered.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View Myview = inflater.inflate(R.layout.row_items, null);
        ImageView imageView = Myview.findViewById(R.id.imageView);
        TextView tvNames = Myview.findViewById(R.id.tvName);
        imageView.setImageResource(itemsModelListFiltered.get(i).getImage());
        tvNames.setText(fix(itemsModelListFiltered.get(i).getName()));

        Myview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equalsIgnoreCase("Plant")){
                    context.startActivity(new Intent(context, FullScreenPlant.class).putExtra("item",itemsModelListFiltered.get(i)));
                }
                else{
                    context.startActivity(new Intent(context, FullScreenBird.class).putExtra("item",itemsModelListFiltered.get(i)));
                }
            }
        });

        return Myview;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (charSequence == null || charSequence.length() == 0) {
                    filterResults.count = itemsModelList.size();
                    filterResults.values = itemsModelList;
                } else {
                    String searchStr = charSequence.toString().toLowerCase();
                    List<ItemsModel> resultsData = new ArrayList<>();

                    for (ItemsModel itemsModel : itemsModelList) {
                        if (fix2(itemsModel.getName()).contains(searchStr)) {
                            resultsData.add(itemsModel);
                        }
                        filterResults.count = resultsData.size();
                        filterResults.values = resultsData;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemsModelListFiltered = (List<ItemsModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };


        return filter;
    }



    public String fix(String n){
        if(n==null){
            return "";
        }
        else if(n.length()>0){
            n = n.replace("_", " ");
            char first =Character.toUpperCase(n.charAt(0));
            n = first+n.substring(1);
        }
        return n;
    }

    public String fix2(String n){
        if(n==null){
            return "";
        }
        else if(n.length()>0){
            n = n.replace("_", " ");

        }
        return n;
    }

}