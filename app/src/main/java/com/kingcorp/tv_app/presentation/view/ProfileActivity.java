package com.kingcorp.tv_app.presentation.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.kingcorp.tv_app.R;

public class ProfileActivity extends AppCompatActivity {

    private ImageView mBackBtn;
    private Spinner mLocationSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setCustomToolbar();

        mBackBtn = findViewById(R.id.back_btn);

        mLocationSpinner = findViewById(R.id.location_chooser);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.locations, R.layout.item_location_spinner);

        adapter.setDropDownViewResource(R.layout.item_location_spinner);

        mLocationSpinner.setAdapter(adapter);

        mBackBtn.setOnClickListener(view -> {
         finish();
        });
    }

    private void setCustomToolbar() {
        Toolbar toolbar = findViewById(R.id.prof_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
