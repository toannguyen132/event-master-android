package project.com.eventmaster.ui.fragments.notifications;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.eventmaster.R;
import project.com.eventmaster.adapter.NotificationAdapter;
import project.com.eventmaster.data.model.Notification;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel mViewModel;

    @BindView(R.id.list_notifications) RecyclerView listView;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notifications_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);

        NotificationAdapter adapter = new NotificationAdapter(Arrays.asList());
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mViewModel.getNotifications().observe(this, new Observer<List<Notification>>() {
            @Override
            public void onChanged(@Nullable List<Notification> notifications) {
                adapter.updateList(notifications);
            }
        });
        mViewModel.fetchNotifications();
    }

}
