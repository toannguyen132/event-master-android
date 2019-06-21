package project.com.eventmaster.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import project.com.eventmaster.data.model.Event;

public class SearchedEventAdapter extends PagedListAdapter<Event, SearchedEventViewHolder> {

    public SearchedEventAdapter() {
        super(Event.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public SearchedEventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return SearchedEventViewHolder.create(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedEventViewHolder searchedEventViewHolder, int i) {
        searchedEventViewHolder.bindTo(getItem(i));
    }
}
