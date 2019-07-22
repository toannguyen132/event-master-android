package project.com.eventmaster.ui.profile;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.eventmaster.R;
import project.com.eventmaster.adapter.CommonInfoAdapter;
import project.com.eventmaster.data.CommonInfo;
import project.com.eventmaster.data.model.CurrentUser;
import project.com.eventmaster.utils.SharedPreferencesHelper;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.profile_list) RecyclerView profileList;
    @BindView(R.id.txt_profile_name) TextView profileName;

    CurrentUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);

        // profile
        currentUser = CurrentUser.getFromCache();
        profileName.setText(currentUser.getName());

        // adapter list
        CommonInfoAdapter adapter = new CommonInfoAdapter(createCommonList(currentUser));
        profileList.setAdapter(adapter);
        profileList.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<CommonInfo> createCommonList(CurrentUser user) {
        List<CommonInfo> info = new ArrayList<>();

        info.add(new CommonInfo("Email:", user.getEmail()));
        info.add(new CommonInfo("Address:", user.getAddress()));

        return info;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
