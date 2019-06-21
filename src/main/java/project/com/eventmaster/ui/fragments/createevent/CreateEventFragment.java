package project.com.eventmaster.ui.fragments.createevent;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import project.com.eventmaster.R;

public class CreateEventFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    private CreateEventViewModel mViewModel;

    ImageButton imageButton;
    View view;

    public static CreateEventFragment newInstance() {
        return new CreateEventFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.create_event_fragment, container, false);

        // set up image, text input
        setupView();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CreateEventViewModel.class);
        // TODO: Use the ViewModel

    }

    private void setupView() {
        imageButton = view.findViewById(R.id.imageButton);

        imageButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE && resultCode != 0) {
            Uri uri = data.getData();
            Picasso.get().load(uri).into(imageButton);
        }
    }

}
