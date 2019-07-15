package project.com.eventmaster.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import project.com.eventmaster.data.model.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    List<Notification> notificationList;

    public NotificationAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public void updateList(List<Notification> newList){
        notificationList = newList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return NotificationViewHolder.create(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, int i) {
        notificationViewHolder.bind(notificationList.get(i));
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
