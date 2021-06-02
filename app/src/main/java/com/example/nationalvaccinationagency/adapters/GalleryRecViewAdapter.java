package com.example.nationalvaccinationagency.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.nationalvaccinationagency.FullScreenImageActivity;
import com.example.nationalvaccinationagency.R;

import java.util.ArrayList;

public class GalleryRecViewAdapter extends RecyclerView.Adapter<GalleryRecViewAdapter.ViewHolder> {
    private ArrayList<String> imagesPath = new ArrayList<>();
    private Context context;

    public GalleryRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.image_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imgPath = imagesPath.get(position);
        Glide.with(context)
                .asBitmap()
                .load(imgPath)
                .into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, FullScreenImageActivity.class);
                i.putExtra("imageResId", Integer.valueOf(imgPath.substring(66)));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.imagesPath.size();
    }

    public void setImagesPath(ArrayList<String> imagesPath) {
        this.imagesPath = imagesPath;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.image_item);
        }
    }
}
