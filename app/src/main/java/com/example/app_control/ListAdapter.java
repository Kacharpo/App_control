package com.example.app_control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private List<ListElement> nData;
    private LayoutInflater nInflater;
    private Context context;

    public ListAdapter(List<ListElement> itemList,Context context){
        this.nInflater = LayoutInflater.from(context);
        this.context = context;
        this.nData = itemList;
    }

    @Override
    public int getItemCount(){
        return nData.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = nInflater.inflate(R.layout.list_element,null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder,final int position){
        holder.binData(nData.get(position));
    }

    public void setItem(List<ListElement> items){
        nData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iv);
            name = itemView.findViewById(R.id.tv_c_uno);
        }

        void binData(final ListElement item){
            iconImage.setImageResource(item.getImage());
            name.setText(item.getName());
        }
    }
}
