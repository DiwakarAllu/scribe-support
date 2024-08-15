package com.example.scribesupport.scribe;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.scribesupport.R;
import com.example.scribesupport.home.HomeActivity;
import com.example.scribesupport.home.HomeFragment;

public class SearchScribeActivity extends AppCompatActivity {

    private Spinner stateSpinner, districtSpinner, citySpinner;
    private CardView nearMe,search;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_scribe);

        toolbar=findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchScribeActivity.this, HomeActivity.class));
                finish();
            }
        });

        stateSpinner=findViewById(R.id.state);
        districtSpinner=findViewById(R.id.dist);
        citySpinner=findViewById(R.id.city);

        stateSpinner.getBackground().setColorFilter(getResources().getColor(R.color.black_shade_1),
                PorterDuff.Mode.SRC_ATOP);
        districtSpinner.getBackground().setColorFilter(getResources().getColor(R.color.black_shade_1),
                PorterDuff.Mode.SRC_ATOP);
        citySpinner.getBackground().setColorFilter(getResources().getColor(R.color.black_shade_1),
                PorterDuff.Mode.SRC_ATOP);

        nearMe=findViewById(R.id.searchScribe);
        nearMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchScribeActivity.this,AvailableVolunteersActivity.class));
            }
        });

    }
}