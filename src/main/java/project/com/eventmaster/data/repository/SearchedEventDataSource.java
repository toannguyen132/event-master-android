package project.com.eventmaster.data.repository;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.data.model.SearchEventsResponse;
import project.com.eventmaster.services.EventService;

public class SearchedEventDataSource extends PageKeyedDataSource<Integer, Event> {

    EventService eventService;
    String queryString;

    public SearchedEventDataSource(EventService eventService, String queryString) {
        this.eventService = eventService;
        this.queryString = queryString;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Event> callback) {
        eventService.getEvents(queryString, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchEventsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchEventsResponse searchEventsResponse) {
                        callback.onResult(searchEventsResponse.getEvents(),
                                0,
                                searchEventsResponse.getEvents().size(),
                                null,
                                2);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "error occurs");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Event> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Event> callback) {
        eventService.getEvents(queryString, params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchEventsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchEventsResponse searchEventsResponse) {
                        if (searchEventsResponse.getEvents().size() == 0) {
                            callback.onResult(searchEventsResponse.getEvents(), null);
                        } else {
                            callback.onResult(searchEventsResponse.getEvents(), params.key + 1);
                        }
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
