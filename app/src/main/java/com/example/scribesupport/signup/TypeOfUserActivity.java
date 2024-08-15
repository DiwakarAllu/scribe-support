package com.example.scribesupport.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.scribesupport.R;
import com.example.scribesupport.signup.needy.FrontPageNeedyActivity;
import com.example.scribesupport.signup.needy.NeedyRegistrationActivity;
import com.example.scribesupport.signup.volunteer.FrontPageVolunteerActivity;
import com.example.scribesupport.signup.volunteer.VolunteerRegistrationActivity;
import com.example.scribesupport.splashscreen.FrontPageActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Locale;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;


public class TypeOfUserActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    Button needy,volunteer;
    TextView back;
    private TextToSpeech textToSpeech;
    private GestureDetectorCompat gestureDetector;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_user);

        needy=findViewById(R.id.needy);
        volunteer=findViewById(R.id.volunteer);
        back=findViewById(R.id.back_button);
        toolbar=findViewById(R.id.toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TypeOfUserActivity.this,FrontPageActivity.class));
                finish();
            }
        });

        needy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i =new Intent(TypeOfUserActivity.this, FrontPageNeedyActivity.class);
            startActivity(i);
            finish();
            }
        });

        volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(TypeOfUserActivity.this, FrontPageVolunteerActivity.class);
                startActivity(i);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(TypeOfUserActivity.this, FrontPageActivity.class);
                startActivity(i);
                finish();
            }
        });

        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                    // Speak instructions upon initialization
                    textToSpeech.speak("To proceed further, swipe up for needy registration or swipe down for volunteer registration. Swipe right to go back.If you wanna listen again tap on the screen", TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });

        // Initialize GestureDetectorCompat
        gestureDetector = new GestureDetectorCompat(this, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        textToSpeech.speak("To proceed further, swipe up for needy registration or swipe down for volunteer registration. Swipe right to go back.If you wanna listen again tap on the screen", TextToSpeech.QUEUE_FLUSH, null, null);
        return true;
    }


    @Override
    public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        // Detect swipes: up for needy registration, down for volunteer registration, right to go back
        float deltaX = e2.getX() - e1.getX();
        float deltaY = e2.getY() - e1.getY();
        int SWIPE_THRESHOLD = dpToPx1(120); // Convert 120dp to pixels
        int VELOCITY_THRESHOLD = dpToPx1(200); // Convert 200dp to pixels
//        if (deltaY < -50) {
//            startNeedyRegistration();
//        } else if (deltaY > 50) {
//            startVolunteerRegistration();
//        } else if (deltaX > 50) {
//            goBackToFrontPage();
//        }
//
//        return true;

            if (e1 == null) return false;
            if (Math.abs(deltaY) > SWIPE_THRESHOLD && Math.abs(velocityY) > VELOCITY_THRESHOLD) {
                if (deltaY < 0) {
                    startNeedyRegistration();
                } else {
                    startVolunteerRegistration();
                }
            } else if (Math.abs(deltaX) > SWIPE_THRESHOLD && Math.abs(velocityX) > VELOCITY_THRESHOLD) {
                if (deltaX > 0) {
                    goBackToFrontPage();
                }
            } else {
                return false;
            }
            return true;
        }

    private int dpToPx1(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }


    private void startNeedyRegistration() {
        Intent intent = new Intent(TypeOfUserActivity.this, NeedyRegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    private void startVolunteerRegistration() {
        Intent intent = new Intent(TypeOfUserActivity.this, VolunteerRegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    private void goBackToFrontPage() {
        Intent intent = new Intent(TypeOfUserActivity.this, FrontPageActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Shutdown TextToSpeech to release resources
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    @Override
    public void onBackPressed() {
        // Check if the current activity is VolunteerProfileViewActivity
        if (this instanceof TypeOfUserActivity) {
            // Navigate back to the previous activity
            super.onBackPressed();
        } else {
            // If the current activity is not VolunteerProfileViewActivity,
            // perform default back button behavior
            super.onBackPressed();
        }
    }


}


//public class TypeOfUserActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
//
//    private static final int SPEECH_REQUEST_CODE = 0;
//
//    private TextToSpeech textToSpeech;
//    private GestureDetector gestureDetector;
//    private TextView messageTextView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_type_of_user);
//
//        messageTextView = findViewById(R.id.message_text_view);
//
//        // Initialize TextToSpeech
//        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if (status != TextToSpeech.ERROR) {
//                    textToSpeech.setLanguage(Locale.US);
//                    // Speak instructions upon initialization
//                    textToSpeech.speak("To proceed, swipe up for needy registration or swipe down for volunteer registration. Swipe right to go back.", TextToSpeech.QUEUE_FLUSH, null, null);
//                }
//            }
//        });
//
//        // Initialize GestureDetector
//        gestureDetector = new GestureDetector(this, this);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return gestureDetector.onTouchEvent(event);
//    }
//
//    @Override
//    public boolean onDown(MotionEvent e) {
//        return true;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent e) {}
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent e) {
//        return true;
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        return true;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent e) {}
//
//    @Override
//    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        // Detect swipes: up for needy registration, down for volunteer registration, right to go back
//        float deltaX = e2.getX() - e1.getX();
//        float deltaY = e2.getY() - e1.getY();
//
//        if (deltaY < -50) {
//            startNeedyRegistration();
//        } else if (deltaY > 50) {
//            startVolunteerRegistration();
//        } else if (deltaX > 50) {
//            goBackToFrontPage();
//        }
//
//        return true;
//    }
//
//    private void startNeedyRegistration() {
//        Intent intent = new Intent(TypeOfUserActivity.this, NeedyRegistrationActivity.class);
//        startActivity(intent);
//        finish();
//    }
//
//    private void startVolunteerRegistration() {
//        Intent intent = new Intent(TypeOfUserActivity.this, VolunteerRegistrationActivity.class);
//        startActivity(intent);
//        finish();
//    }
//
//    private void goBackToFrontPage() {
//        Intent intent = new Intent(TypeOfUserActivity.this, FrontPageActivity.class);
//        startActivity(intent);
//        finish();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // Shutdown TextToSpeech to release resources
//        if (textToSpeech != null) {
//            textToSpeech.stop();
//            textToSpeech.shutdown();
//        }
//    }
//}
