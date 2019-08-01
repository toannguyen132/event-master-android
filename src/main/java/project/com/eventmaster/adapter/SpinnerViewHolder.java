package project.com.eventmaster.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpinnerViewHolder extends RecyclerView.ViewHolder {

    @BindView(android.R.id.text1)
    TextView text;

    public SpinnerViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(SpinnerItem item) {
        text.setText(item.getLabel());
    }

    public static SpinnerViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new SpinnerViewHolder(view);
    }

    public static class SpinnerItem<T>{
        private String label;
        private T value;

        public SpinnerItem(String label, T value) {
            this.label = label;
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}
