package project.com.eventmaster.ui.subscriptions;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.eventmaster.R;
import project.com.eventmaster.adapter.SubscriptionAdapter;
import project.com.eventmaster.adapter.SubscriptionViewHolder;
import project.com.eventmaster.data.model.Category;

public class SubscriptionsActivity extends AppCompatActivity implements SubscriptionViewHolder.OnClickListener {

    SubscriptionsViewModel mViewModel;

    @BindView(R.id.list_subscriptions)
    RecyclerView listSubscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);

        ButterKnife.bind(this);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowHomeEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);

        // prepare adapter
        SubscriptionAdapter adapter = new SubscriptionAdapter(Arrays.asList(), this);
        listSubscriptions.setAdapter(adapter);
        listSubscriptions.setLayoutManager(new LinearLayoutManager(this));

        mViewModel = ViewModelProviders.of(this).get(SubscriptionsViewModel.class);
        mViewModel.getCategories().observe(this, categories -> {
            adapter.setCategories(categories);
        });

        mViewModel.fetchSubscriptions();
    }

    @Override
    public void onClick(Category category) {
        if (category.isSubscribe()) {
            mViewModel.unsubscribeCategory(category.getId());
        } else {
            mViewModel.subscribeCategory(category.getId());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
