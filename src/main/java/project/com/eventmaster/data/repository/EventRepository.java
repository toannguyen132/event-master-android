package project.com.eventmaster.data.repository;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import project.com.eventmaster.data.model.Category;
import project.com.eventmaster.data.model.CreateEventRequest;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.data.model.PaymentRequest;
import project.com.eventmaster.data.model.SearchEventsResponse;
import project.com.eventmaster.network.RetrofitClientInstance;
import project.com.eventmaster.services.EventService;
import project.com.eventmaster.services.PaymentService;
import project.com.eventmaster.utils.TokenHelper;

public class EventRepository {

    List<Event> events;

    private EventService service;
    private PaymentService paymentService;

    // singleton
    static EventRepository instance;

    public EventRepository() {
        service = RetrofitClientInstance.getClient().create(EventService.class);
        paymentService = RetrofitClientInstance.getClient().create(PaymentService.class);
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
            service.getEvents("", 1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<SearchEventsResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(SearchEventsResponse response) {
                            emitter.onNext(response.getEvents());
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

    public Observable<Event> create(CreateEventRequest request) {
        String token = TokenHelper.getInstance().getToken();
        return service.createEvent(token, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Event> getSingleEvent(String id) {
        return service.getSingleEvent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getCategories(FetchCategoryListener onResponse) {
        String token = TokenHelper.getInstance().getToken();

        service.fetchCategories(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Category>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Category> events) {
                        if (onResponse != null) {
                            onResponse.onFetchCategorySuccess(events);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (onResponse != null) {
                            onResponse.onFetchCategoryFailed(new Exception(e));
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * payment
     */
    public void createPayment(PaymentRequest request, CreatePaymentListener listener) {
        String token = TokenHelper.getInstance().getToken();

        paymentService.createPayment(token, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        if (listener != null) {
                            listener.onCreatePaymentSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.onCreatePaymentFailed(new Exception(e));
                        }
                    }
                });
    }

    public interface FetchCategoryListener{
        void onFetchCategorySuccess(List<Category> categories);
        void onFetchCategoryFailed(Exception e);
    }

    public interface CreatePaymentListener{
        void onCreatePaymentSuccess();
        void onCreatePaymentFailed(Exception e);
    }
}
