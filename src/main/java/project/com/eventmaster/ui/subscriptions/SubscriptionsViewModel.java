package project.com.eventmaster.ui.subscriptions;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import project.com.eventmaster.data.model.Category;
import project.com.eventmaster.data.repository.EventRepository;
import project.com.eventmaster.data.repository.UserRepository;

public class SubscriptionsViewModel extends ViewModel {

    private static final String TAG = "Subscription ViewModel";

    EventRepository eventRepository;
    UserRepository userRepository;

    MutableLiveData<List<Category>> categories = new MutableLiveData<>();

    public SubscriptionsViewModel() {
        eventRepository = EventRepository.getInstance();
        userRepository = UserRepository.getInstance();
    }

    public MutableLiveData<List<Category>> getCategories() {
        return categories;
    }

    public void fetchSubscriptions() {
        eventRepository.getCategories(new EventRepository.FetchCategoryListener() {
            @Override
            public void onFetchCategorySuccess(List<Category> responseCategories) {
                categories.setValue(responseCategories);
            }

            @Override
            public void onFetchCategoryFailed(Exception e) {
                Log.d(TAG, e.getMessage());
            }
        });
    }

    public void subscribeCategory(String id) {
        final SubscriptionsViewModel instance = this;
        userRepository.subscribeCategory(id, new UserRepository.OnSubscribeCategory() {
            @Override
            public void onSubscribeCategorySuccess() {
                instance.fetchSubscriptions();
            }

            @Override
            public void onSubscribeCategoryFailed(Exception e) {
                Log.d(TAG, e.getMessage());
            }
        });
    }

    public void unsubscribeCategory(String id) {
        final SubscriptionsViewModel instance = this;
        userRepository.unSubscribeCategory(id, new UserRepository.OnUnsubscribeCategory() {
            @Override
            public void onUnsubscribeCategorySuccess() {
                instance.fetchSubscriptions();
            }

            @Override
            public void onUnsubscribeCategoryFailed(Exception e) {
                Log.d(TAG, e.getMessage());
            }
        });
    }
}
