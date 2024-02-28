package com.checking.choaching_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.checking.choaching_app.R;
import com.checking.choaching_app.databinding.ItemNotificationBinding;
import com.checking.choaching_app.models.Notification;

import java.util.ArrayList;
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private Context context;
    private ArrayList<Notification> notifications;

    public NotificationAdapter(Context context, ArrayList<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.binding.title.setText(notification.getTitle());
        holder.binding.date.setText(notification.getDate());
        holder.binding.message.setText(notification.getMessage());

        Glide.with(context)
                .load(notification.getImgUrl())
                .into(holder.binding.image);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        ItemNotificationBinding binding;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemNotificationBinding.bind(itemView);
        }
    }
}
