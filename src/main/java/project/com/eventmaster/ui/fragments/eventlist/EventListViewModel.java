package project.com.eventmaster.ui.fragments.eventlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.data.repository.EventRepository;

public class EventListViewModel extends ViewModel {
    private MutableLiveData<List<Event>> events = new MutableLiveData<>();
    private EventRepository repository;

    public LiveData<List<Event>> getEvents() { return events; }

    public EventListViewModel() {
        repository = EventRepository.getInstance();
    }

    public void search() {
        repository.search().subscribe(new Observer<List<Event>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Event> eventsList) {
                events.setValue(eventsList);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
