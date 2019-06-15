package project.com.eventmaster.data.repository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.network.RetrofitClientInstance;
import project.com.eventmaster.services.AuthService;
import project.com.eventmaster.services.EventService;

public class EventRepository {

    List<Event> events;

    private EventService service;

    // singleton
    static EventRepository instance;

    public EventRepository() {
        service = RetrofitClientInstance.getClient().create(EventService.class);
    }

    static public EventRepository getInstance() {
        if (instance == null) {
            instance = new EventRepository();
        }

        return instance;
    }

    // search events
    public Observable<List<Event>> search() {
        return Observable.create(emitter -> {
            service.getEvents()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Event>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<Event> events) {
                            emitter.onNext(events);
                        }

                        @Override
                        public void onError(Throwable e) {
                            emitter.onError(new Exception(e));
                        }

                        @Override
                        public void onComplete() {
                            emitter.onComplete();
                        }
                    });
        });
    }
}
