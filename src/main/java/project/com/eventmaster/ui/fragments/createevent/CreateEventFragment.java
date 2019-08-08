package project.com.eventmaster.ui.fragments.createevent;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.eventmaster.R;
import project.com.eventmaster.data.Result;
import project.com.eventmaster.data.model.Category;
import project.com.eventmaster.data.model.CreateEventRequest;
import project.com.eventmaster.data.model.FileResponse;
import project.com.eventmaster.data.repository.FileRepository;
import project.com.eventmaster.utils.DateInputMask;
import project.com.eventmaster.utils.DisplayHelper;
import project.com.eventmaster.utils.FileUtils;

public class CreateEventFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    private CreateEventViewModel mViewModel;

    private final int REQUEST_PERMISSON = 1;

    @BindView(R.id.imageButton)
    ImageButton imageButton;

    @BindView(R.id.button_create)
    Button createButton;

    @BindView(R.id.create_event_name)
    EditText textName;

    @BindView(R.id.create_event_desc)
    EditText textDescription;

    @BindView(R.id.create_event_location)
    EditText textLocation;

    @BindView(R.id.create_event_startdate)
    EditText textStartDate;

    @BindView(R.id.create_event_starttime)
    EditText textStartTime;

    @BindView(R.id.create_event_enddate)
    EditText textEndDate;

    @BindView(R.id.create_event_endtime)
    EditText textEndTime;

    @BindView(R.id.create_event_categories)
    Spinner spinnerCategories;

    String image;
    View view;
    private String selectedCategory;

    public static CreateEventFragment newInstance() {
        return new CreateEventFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.create_event_fragment, container, false);

        try {
            // bind view
            ButterKnife.bind(this, view);

            // set up image, text input
            setupView();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup view model
        mViewModel = ViewModelProviders.of(this).get(CreateEventViewModel.class);

        // get categories
        mViewModel.fetchCategories();

        // observe uploading
        observeUpload();

        observeCreating();
    }

    private void setupView() {
        imageButton.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
            } else {
                openGallery();
            }
        });

        createButton.setOnClickListener(view -> {
            createEvent();
        });

        DateInputMask.create(textStartDate);
        DateInputMask.create(textEndDate);

    }

    private void setUpSpinner(List<Category> categories){
        List<String> values = new ArrayList<>();
        for (Category cat : categories) {
            values.add(cat.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, values);
        spinnerCategories.setAdapter(adapter);
        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = categories.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedCategory = categories.get(0).getId();
            }
        });
    }

    private void observeUpload() {
        mViewModel.getFileResponse().observe(this, response ->
                image = response.getId());
    }

    private void observeCreating() {
        mViewModel.getCreateResult().observe(this, response -> {
            Toast.makeText(this.getContext(), "Create Success", Toast.LENGTH_SHORT).show();
            clearView();

        });

        mViewModel.getError().observe(this, error -> {
            Toast.makeText(getContext(), error.getError().getMessage(), Toast.LENGTH_SHORT).show();
        });

        mViewModel.getCategories().observe(this, categories -> {
            setUpSpinner(categories);
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSON && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSON);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE && resultCode != 0) {
            Uri uri = data.getData();
            Picasso.get().load(uri).into(imageButton);

            String path = FileUtils.getPath(getContext(), uri);
            File file = new File(path);

            String type = getActivity().getContentResolver().getType(uri);

            // upload
            mViewModel.uploadFile(file, type);
        }
    }

    private CreateEventRequest buildRequest() {
        CreateEventRequest request = new CreateEventRequest();

        request.setImage(Arrays.asList(new String[] {image}));
        request.setName(textName.getText().toString());
        request.setDescription(textDescription.getText().toString());
        request.setLocation(textLocation.getText().toString());
        request.setStartDate(DisplayHelper.getInstance().toIsoDate(
                textStartDate.getText().toString(),
                textStartTime.getText().toString()
        ));
        request.setEndDate(
                DisplayHelper.getInstance().toIsoDate(
                        textEndDate.getText().toString(),
                        textEndTime.getText().toString()
                )
        );
        request.setCategory(Arrays.asList(new String[] {selectedCategory}));

        return request;
    }

    private void clearView() {
        textName.setText("");
        textLocation.setText("");
        textDescription.setText("");
        textEndDate.setText("");
        textEndTime.setText("");
        textStartDate.setText("");
        textStartTime.setText("");
    }

    private void createEvent() {
        CreateEventRequest request = buildRequest();
        mViewModel.create(request);
    }

}
