package com.example.scribesupport.signup.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.scribesupport.R;
import com.example.scribesupport.signup.TypeOfUserActivity;
import com.example.scribesupport.signup.needy.FrontPageNeedyActivity;

public class FrontPageVolunteerActivity extends AppCompatActivity {

    Button skip,next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_front_page_volunteer);

        skip=findViewById(R.id.skip_button);
        next=findViewById(R.id.next_button);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FrontPageVolunteerActivity.this, TypeOfUserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FrontPageVolunteerActivity.this, "Registration...", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(FrontPageVolunteerActivity.this, VolunteerRegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        // Check if the current activity is VolunteerProfileViewActivity
        if (this instanceof FrontPageVolunteerActivity) {
            // Navigate back to the previous activity
            super.onBackPressed();
        } else {
            // If the current activity is not VolunteerProfileViewActivity,
            // perform default back button behavior
            super.onBackPressed();
        }
    }

}