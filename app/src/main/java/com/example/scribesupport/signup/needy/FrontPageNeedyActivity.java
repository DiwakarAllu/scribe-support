package com.example.scribesupport.signup.needy;

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

public class FrontPageNeedyActivity extends AppCompatActivity {

    Button skip,next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_front_page_needy);

        skip=findViewById(R.id.skip_button);
        next=findViewById(R.id.next_button);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FrontPageNeedyActivity.this, TypeOfUserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FrontPageNeedyActivity.this, "Registration...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(FrontPageNeedyActivity.this,NeedyRegistrationActivity.class));
                finish();
            }
        });
    }
}