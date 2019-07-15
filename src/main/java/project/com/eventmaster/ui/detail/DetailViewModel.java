package project.com.eventmaster.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

import java.util.logging.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.data.repository.EventRepository;

public class DetailViewModel extends ViewModel {
    private EventRepository eventRepository;

    private MutableLiveData<Event> event = new MutableLiveData<>();

    public LiveData<Event> getEvent() {
        return event;
    }

    public void setEvent(Event newEvent) {
        event.setValue(newEvent);
    }

    public DetailViewModel() {
        eventRepository = EventRepository.getInstance();
    }

    public void fetchEvent(String id) {
        eventRepository.getSingleEvent(id).subscribe(new Observer<Event>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Event event) {
                setEvent(event);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("DetailViewModel", e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
