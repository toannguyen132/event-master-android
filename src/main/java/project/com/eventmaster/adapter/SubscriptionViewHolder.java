package project.com.eventmaster.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.eventmaster.R;
import project.com.eventmaster.data.model.Category;

public class SubscriptionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txt_common_label)
    TextView viewCatName;

    @BindView(R.id.txt_common_value)
    TextView viewSubscribe;

    View view;

    OnClickListener onClickListener;

    public SubscriptionViewHolder(@NonNull View view, OnClickListener listener) {
        super(view);
        this.view = view;
        this.onClickListener = listener;
        ButterKnife.bind(this, view);
    }

    static public SubscriptionViewHolder create(ViewGroup parent, OnClickListener listener) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_info, parent, false);
        return new SubscriptionViewHolder(view, listener);
    }

    public void bind(Category category) {
        viewCatName.setText(category.getName());
        viewSubscribe.setText(category.isSubscribe() ? "ON" : "OFF");
        if (category.isSubscribe()) {
            viewSubscribe.setTextColor(Color.GREEN);
        } else {
            viewSubscribe.setTextColor(Color.RED);
        }

        view.setOnClickListener(view -> {
            onClickListener.onClick(category);
        });
    }

    public interface OnClickListener{
        void onClick(Category category);
    }
}
