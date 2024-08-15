package com.example.scribesupport.splashscreen;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import com.example.scribesupport.R;
import com.example.scribesupport.login.LoginActivity;
import com.example.scribesupport.signup.TypeOfUserActivity;

import java.util.ArrayList;
import java.util.Locale;

public class FrontPageActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private TextToSpeech textToSpeech;
    private GestureDetectorCompat gestureDetector;
    private Button loginButton;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                    // Speak welcome message upon initialization
                    textToSpeech.speak("Welcome to the Scribe Support app. Swipe left for login, and swipe right for signup. If you wanna listen again tap on the screen", TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });

        // Initialize GestureDetector
        gestureDetector = new GestureDetectorCompat(this, this);

        loginButton = findViewById(R.id.login_button);
        signupButton = findViewById(R.id.signup_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignupActivity();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {}

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        textToSpeech.speak("Welcome to the Scribe Support app. Swipe left for login, and swipe right for signup. If you wanna listen again tap on the screen", TextToSpeech.QUEUE_FLUSH, null, null);
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {}

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // Detect left swipe for login and right swipe for signup
        if (e1.getX() - e2.getX() > 50) {
            startLoginActivity();
        } else if (e2.getX() - e1.getX() > 50) {
            startSignupActivity();
        }
        return true;
    }

    private void startLoginActivity() {
        Intent intent = new Intent(FrontPageActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void startSignupActivity() {
        Intent intent = new Intent(FrontPageActivity.this, TypeOfUserActivity.class);
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
}



//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.scribesupport.login.LoginActivity;
//import com.example.scribesupport.R;
//import com.example.scribesupport.SignupActivity;
//import com.example.scribesupport.signup.TypeOfUserActivity;
//
//public class FrontPageActivity extends AppCompatActivity {
//
//    Button login;
//    Button signup;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_front_page);
//
//        login=findViewById(R.id.login_button);
//        signup=findViewById(R.id.signup_button);
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i =new Intent(FrontPageActivity.this, LoginActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
//
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i =new Intent(FrontPageActivity.this, TypeOfUserActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
//    }
//}