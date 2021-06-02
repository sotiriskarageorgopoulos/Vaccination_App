package com.example.nationalvaccinationagency.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nationalvaccinationagency.R;

import java.util.ArrayList;

public class VideoRecViewAdapter extends RecyclerView.Adapter<VideoRecViewAdapter.ViewHolder> {
    private ArrayList<String> videos = new ArrayList<>();
    private MediaController mediaController;
    private Context context;

    public VideoRecViewAdapter(Context context) {
        this.context = context;
        mediaController = new MediaController(this.context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.youtube_video,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(videos.get(position));
                holder.videoView.setVideoURI(uri);
                holder.videoView.setMediaController(mediaController);
                mediaController.setAnchorView(holder.videoView);
                holder.videoView.start();
            }
        });


    }

    @Override
    public int getItemCount() {
        return this.videos.size();
    }

    public void setVideos(ArrayList<String> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private VideoView videoView;
        private ImageButton videoBtn;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            this.videoView = itemView.findViewById(R.id.youtube_video);
            this.videoBtn = itemView.findViewById(R.id.video_btn);
        }
    }
}
