package com.example.app_control.Merch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.app_control.MainActivity;
import com.example.app_control.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RVAdapterMerch extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private String modelName;
    StorageReference storageRef;

    private Context context;
    ArrayList<MerchConstructor> list = new ArrayList<>();
    public RVAdapterMerch(Context ctx)
    {
        this.context = ctx;
    }
    public void setItems(ArrayList<MerchConstructor> emp)
    {
        list.addAll(emp);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_merch,parent,false);
        return new MerchVH(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        MerchConstructor e = null;
        this.onBindViewHolder(holder,position,e);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, MerchConstructor e)
    {
        MerchVH vh = (MerchVH) holder;
        MerchConstructor emp = e==null? list.get(position):e;
        vh.txt_name.setText(emp.getName());
        vh.txt_position.setText(emp.getPosition());
        //vh.txt_price.setText(emp.getPrice());
        vh.txt_option.setOnClickListener(v->
        {
            PopupMenu popupMenu =new PopupMenu(context,vh.txt_option);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item->
            {
                switch (item.getItemId())
                {
                    /*case R.id.menu_edit:
                        Intent intent=new Intent(context, MainActivity.class);
                        intent.putExtra("EDIT",emp);
                        context.startActivity(intent);
                        break;
                    case R.id.menu_remove:
                        DAOMerch dao=new DAOMerch();
                        dao.remove(emp.getKey()).addOnSuccessListener(suc->
                        {
                            Toast.makeText(context, "Record is removed", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                            list.remove(emp);
                        }).addOnFailureListener(er->
                        {
                            Toast.makeText(context, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                        });

                        break; */
                }
                return false;
            });
            popupMenu.show();
        });

        modelName = "gl0jsz2cmb571.jpg";

        FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();

        try{
            storageRef = mFirebaseStorage.getReference("Merch/"+modelName);
            File localfile = File.createTempFile("tempfile",".jpg");
            storageRef.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    //binding.imgLpWallpaper.setImageBitmap(bitmap);
                    vh.img_wallpaper.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }catch (IOException ef){
            ef.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
