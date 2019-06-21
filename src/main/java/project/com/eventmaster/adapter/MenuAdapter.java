package project.com.eventmaster.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {

    final static public List<String> MENU_ITEMS = Arrays.asList(new String[] { "View Profile", "My Events", "Subscriptions", "Log Out" });
    final static public int MENU_PROFILE = 0;
    final static public int MENU_EVENTS = 1;
    final static public int MENU_SUBSCRIBTIONS = 2;
    final static public int MENU_LOGOUT = 3;

    private List<String> items;
    private OnMenuSelected listener;

    public MenuAdapter() {
        this.items = MENU_ITEMS;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return MenuViewHolder.create(viewGroup, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, int i) {
        menuViewHolder.bindTo(items.get(i), i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnMenuSelected(OnMenuSelected listener) {
        this.listener = listener;
    }

    public interface OnMenuSelected{
        void onSelected(int i, String name);
    }
}
