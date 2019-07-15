package project.com.eventmaster.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.eventmaster.R;
import project.com.eventmaster.data.model.Notification;
import project.com.eventmaster.data.repository.UserRepository;
import project.com.eventmaster.utils.ActivityHelper;
import project.com.eventmaster.utils.DisplayHelper;

public class NotificationViewHolder extends RecyclerView.ViewHolder implements UserRepository.OnReadNotificationResponse {

    static final String TAG = "NotificationViewHolder";

    View view;
    @BindView(R.id.item_title) TextView textTitle;
    @BindView(R.id.item_desc) TextView textDesc;
    UserRepository userResponsitory;


    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        ButterKnife.bind(this, view);

        userResponsitory = UserRepository.getInstance();
        userResponsitory.setReadNotificationListender(this);
    }

    public static NotificationViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    public void bind(Notification item) {
        textTitle.setText(item.getMessage());
        textDesc.setText(DisplayHelper.getInstance().dateFormat(item.getCreatedAt()));
        if (!item.getRead()) {
            view.setBackgroundColor(Color.parseColor("#B8DBFF"));
        }

        // onclick
        view.setOnClickListener(view -> {
            if (item.getData() != null) {
                ActivityHelper.getInstance().openEventDetail(view.getContext(), item.getData());
                userResponsitory.readNotification(item.getId());
            }
        });
    }

    @Override
    public void onReadNotificationSuccess() {
        Log.d(TAG, "read notification successfully");
        view.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onReadNotificationFailed(Exception e) {
        Log.d(TAG, "read notification failed");
    }
}
