package com.example.app_control.Merch;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_control.R;

public class MerchVH extends RecyclerView.ViewHolder{
    public TextView txt_name,txt_position,txt_option,txt_price;
    public ImageView img_wallpaper;
    public MerchVH(@NonNull View itemView)
    {
        super(itemView);
        txt_name = itemView.findViewById(R.id.txt_lm_title);
        txt_position = itemView.findViewById(R.id.txt_lm_information);
        txt_option = itemView.findViewById(R.id.txt_lm_option);
        //txt_price = itemView.findViewById(R.id.txt_lm_price);
        img_wallpaper = itemView.findViewById(R.id.img_lm_model);
    }
}
