package com.checking.choaching_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.checking.choaching_app.R;
import com.checking.choaching_app.databinding.ItemVideoBinding;
import com.checking.choaching_app.models.Video;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    Context context;
    ArrayList<Video> videos;

    public VideoAdapter(Context context, ArrayList<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    public void setVideoList(ArrayList<Video> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_video, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videos.get(position);

        if (video.getSubject().equalsIgnoreCase("math")) {
            holder.binding.image.setImageResource(R.drawable.math_image);
        } else if (video.getSubject().equalsIgnoreCase("physics")) {
            holder.binding.image.setImageResource(R.drawable.physics_image);
        } else if (video.getSubject().equalsIgnoreCase("chemistry")) {
            holder.binding.image.setImageResource(R.drawable.chemistry_image);
        } else if (video.getSubject().equalsIgnoreCase("biology")) {
            holder.binding.image.setImageResource(R.drawable.biology_image);
        } else {
            holder.binding.image.setImageResource(R.drawable.placeholder);
        }

        holder.binding.title.setText(video.getTitle());
        holder.binding.subject.setText("Subject :" + video.getSubject());
        holder.binding.description.setText(video.getDescription());
        holder.binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoUrl = video.getVideoUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }


    public class VideoViewHolder extends RecyclerView.ViewHolder {
        ItemVideoBinding binding;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemVideoBinding.bind(itemView);
        }
    }


}
