package project.com.eventmaster.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import project.com.eventmaster.data.model.Event;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<Event> event = new MutableLiveData<>();

    public LiveData<Event> getEvent() {
        return event;
    }

    public void setEvent(Event newEvent) {
        event.setValue(newEvent);
    }
}
