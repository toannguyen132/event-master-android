package project.com.eventmaster.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import project.com.eventmaster.data.model.Category;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionViewHolder> {

    List<Category> categories;
    SubscriptionViewHolder.OnClickListener onClickListener;

    public SubscriptionAdapter(List<Category> categories, SubscriptionViewHolder.OnClickListener listener) {
        this.categories = categories;
        this.onClickListener = listener;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return SubscriptionViewHolder.create(viewGroup, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionViewHolder subscriptionViewHolder, int i) {
        subscriptionViewHolder.bind(categories.get(i));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
