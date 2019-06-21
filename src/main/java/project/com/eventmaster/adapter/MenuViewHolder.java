package project.com.eventmaster.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import project.com.eventmaster.R;

public class MenuViewHolder extends RecyclerView.ViewHolder {

    TextView menuName;
    MenuAdapter.OnMenuSelected listener;

    public MenuViewHolder(@NonNull View itemView, MenuAdapter.OnMenuSelected listener) {
        super(itemView);

        menuName = itemView.findViewById(R.id.menu_text);
        this.listener = listener;
    }

    public static MenuViewHolder create(ViewGroup parent, MenuAdapter.OnMenuSelected listener) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);

        return new MenuViewHolder(view, listener);
    }

    public void bindTo(String text, int i){
        menuName.setText(text);
        if (listener != null) {
            menuName.setOnClickListener(view -> listener.onSelected(i, text));
        }
    }
}
