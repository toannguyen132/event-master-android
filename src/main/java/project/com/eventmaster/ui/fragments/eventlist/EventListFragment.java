package project.com.eventmaster.ui.fragments.eventlist;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;
import java.util.concurrent.Executor;

import project.com.eventmaster.R;
import project.com.eventmaster.adapter.EventAdapter;
import project.com.eventmaster.adapter.SearchedEventAdapter;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.data.repository.SearchedEventDataSource;
import project.com.eventmaster.network.RetrofitClientInstance;
import project.com.eventmaster.services.EventService;
import project.com.eventmaster.utils.MainThreadExecutor;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //
    private SearchedEventAdapter adapter;
    private EventListViewModel viewModel;
    private View view;
    private SearchedEventDataSource dataSource;
    private EventService eventService;
    private Executor executor;

    public EventListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventListFragment newInstance(String param1, String param2) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        this.view = view;

        executor = new MainThreadExecutor();

        setupService();
        setupRecyclerView();
        setupDataSource("");

        return view;
    }


    private void setupService() {
        eventService = RetrofitClientInstance.getClient().create(EventService.class);
    }

    private void setupRecyclerView() {
        // SETUP ADAPTER
//         adapter = new EventAdapter(viewModel.getEvents().getValue());
        adapter = new SearchedEventAdapter();

        //setup view
        RecyclerView listView = view.findViewById(R.id.list_events);

        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void setupDataSource(String queryString) {
        dataSource = new SearchedEventDataSource(eventService, queryString);

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(2)
                .setInitialLoadSizeHint(4)
                .setEnablePlaceholders(true)
                .build();

        PagedList<Event> list = new PagedList.Builder<>(dataSource, config)
                .setFetchExecutor(executor)
                .setNotifyExecutor(executor)
                .build();

        adapter.submitList(list);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
