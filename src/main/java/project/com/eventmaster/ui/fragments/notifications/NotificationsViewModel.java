package project.com.eventmaster.ui.fragments.notifications;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import project.com.eventmaster.data.model.CurrentUser;
import project.com.eventmaster.data.model.Notification;
import project.com.eventmaster.utils.SharedPreferencesHelper;

public class NotificationsViewModel extends ViewModel {
    MutableLiveData<List<Notification>> notifications = new MutableLiveData<>();

    public MutableLiveData<List<Notification>> getNotifications() {
        return notifications;
    }

    public void fetchNotifications() {
        CurrentUser user = SharedPreferencesHelper.getInstance().getObject(SharedPreferencesHelper.KEY_CURRENT_USER, CurrentUser.class);
        notifications.setValue(user.getNotifications());
    }
}
