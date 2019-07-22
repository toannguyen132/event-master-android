package project.com.eventmaster.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import project.com.eventmaster.R;
import project.com.eventmaster.adapter.MenuAdapter;
import project.com.eventmaster.ui.myevents.MyEventActivity;
import project.com.eventmaster.ui.profile.ProfileActivity;
import project.com.eventmaster.ui.subscriptions.SubscriptionsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements MenuAdapter.OnMenuSelected {

    View view;
    OnLogoutListener listener;
    static final int CODE_MY_EVENTS = 1;


    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance() {

        Bundle args = new Bundle();

        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menu, container, false);

        setupMenuList();

        return view;
    }

    private void setupMenuList() {
        MenuAdapter adapter = new MenuAdapter();
        adapter.setOnMenuSelected(this);

        RecyclerView listView = view.findViewById(R.id.menu_list);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    @Override
    public void onSelected(int i, String name) {
        Toast.makeText(view.getContext(), "clicked: " + name, Toast.LENGTH_LONG).show();

        switch (i) {
            case MenuAdapter.MENU_LOGOUT:
                if (listener != null) {
                    listener.onLogout();
                }

            break;
            case MenuAdapter.MENU_PROFILE:
                openProfile();
                break;

            case MenuAdapter.MENU_EVENTS:
                openMyEvents();
                break;

            case MenuAdapter.MENU_SUBSCRIBTIONS:
                openSubscriptions();
                break;
        }
    }

    private void openMyEvents() {
        Intent intent = new Intent(this.getActivity(), MyEventActivity.class);
        startActivityForResult(intent, CODE_MY_EVENTS);
    }

    private void openProfile() {
        Intent intent = new Intent(this.getActivity(), ProfileActivity.class);
        startActivity(intent);
    }

    private void openSubscriptions() {
        Intent intent = new Intent(this.getActivity(), SubscriptionsActivity.class);
        startActivity(intent);
    }


    /** log out **/

    public void setOnLogoutListener(OnLogoutListener listener) {
        this.listener = listener;
    }

    public interface OnLogoutListener{
        void onLogout();
    }
}
