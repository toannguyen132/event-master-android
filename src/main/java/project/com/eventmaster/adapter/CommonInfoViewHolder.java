package project.com.eventmaster.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.eventmaster.R;

public class CommonInfoViewHolder extends RecyclerView.ViewHolder {

    View view;

    @BindView(R.id.txt_common_label) TextView viewLabel;
    @BindView(R.id.txt_common_value) TextView viewValue;


    public CommonInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        ButterKnife.bind(this, view);
    }

    public static CommonInfoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_info, parent, false);
        return new CommonInfoViewHolder( view );
    }

    public void bind(String Label, String Value) {
        viewLabel.setText(Label);
        viewValue.setText(Value);
    }
}
