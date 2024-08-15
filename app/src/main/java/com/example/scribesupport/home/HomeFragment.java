package com.example.scribesupport.home;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.scribesupport.home.UploadPdfActivity;

import com.example.scribesupport.R;
import com.example.scribesupport.scribe.SearchScribeActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment implements GestureDetector.OnGestureListener,View.OnTouchListener{

    private static CardView uploadMaterial,searchScribe,studyMaterial,viewUploads;
    View view;

    private FlashlightController flashlightController;
    private TextToSpeech textToSpeech;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    private GestureDetectorCompat gestureDetector;
    private LocationManager locationManager;

    private static final int STATE_INITIAL = 0;
    private static final int STATE_CALCULATOR = 1;


    private int currentState = STATE_INITIAL;
    private static final int REQUEST_LOCATION_PERMISSION = 1001;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home,null,false);

        flashlightController = new FlashlightController(requireContext());

        uploadMaterial=view.findViewById(R.id.uploadMaterial);
        searchScribe=view.findViewById(R.id.searchScribe);

        searchScribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchScribeActivity.class));
            }
        });

        uploadMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(HomeFragment.this, UploadPdfActivity.class);
                startActivity(new Intent(getContext(),UploadPdfActivity.class));
            }
        });

        textToSpeech=new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.setLanguage(Locale.US);
                textToSpeech.speak("You'r in Home fragment, swipe left to speak",TextToSpeech.QUEUE_ADD,null,null);
            }
        });

        gestureDetector = new GestureDetectorCompat(this.getActivity(),HomeFragment.this);
        view.setOnTouchListener(HomeFragment.this);

        locationManager= (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);

        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }


    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {

    }
    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        textToSpeech.speak("You'r in Home fragment, swipe left to speak",TextToSpeech.QUEUE_ADD,null,null);
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

        Log.d("Gesture", "onFling: Called");
        float deltaX = e2.getX() - e1.getX();
        float deltaY = e2.getY() - e1.getY();

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (Math.abs(deltaX) > 50 && Math.abs(velocityX) > 50) {
                if (deltaX > 0) {
                    // Right swipe
                    textToSpeech.speak("Swiped Right", TextToSpeech.QUEUE_ADD, null, null);
                } else {
                    // Left swipe
                    //textToSpeech.speak("Swiped Left", TextToSpeech.QUEUE_ADD, null, null);
                    speakOut();
                }
            }
        }
        return  true;

    }

    private void speakOut(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"speak to text");

          try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), " " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = result.get(0);

            switch (currentState) {
                case STATE_INITIAL:
                    evaluateExpression1(spokenText);
                    break;
                case STATE_CALCULATOR:
                    evaluateCalcExpression(spokenText);
                    currentState=STATE_INITIAL;
                    break;
            }
        }
    }

    private void evaluateExpression1(String spokenText) {
        spokenText = spokenText.toLowerCase();

        if (spokenText.contains("time")) {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            String currentTime = sdf.format(new Date());
            Toast.makeText(getContext(), "Time :"+currentTime, Toast.LENGTH_SHORT).show();
            textToSpeech.speak("The current time is " + currentTime, TextToSpeech.QUEUE_FLUSH, null, null);
        } else if (spokenText.contains("date")) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
            String currentDate = sdf.format(new Date());
            Toast.makeText(getContext(), "Date :"+currentDate, Toast.LENGTH_SHORT).show();
            textToSpeech.speak("Today's date is " + currentDate, TextToSpeech.QUEUE_FLUSH, null, null);
        } else if (spokenText.contains("battery")) {
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = getContext().registerReceiver(null, ifilter);
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = level / (float) scale;
            int batteryPercentage = (int) (batteryPct * 100);
            Toast.makeText(getContext(), "Battery :"+batteryPercentage, Toast.LENGTH_SHORT).show();
            textToSpeech.speak("The current battery level is " + batteryPercentage + " percent", TextToSpeech.QUEUE_FLUSH, null, null);
        } else if (spokenText.contains("location")) {
            // Get current location
            getCurrentLocation();
            //textToSpeech.speak("Sorry, location feature is not implemented yet", TextToSpeech.QUEUE_FLUSH, null, null);
        } else if (spokenText.contains("exit")) {
            getActivity().finish();
        } else if (spokenText.contains("calculator")) {
//            currentState = STATE_CALCULATOR;
//            textToSpeech.speak("Please say a mathematical expression", TextToSpeech.QUEUE_FLUSH, null, null);
//            textToSpeech.playSilence(750, TextToSpeech.QUEUE_ADD, null);
//            speakOut();
            startActivity(new Intent(getContext(),CalculatorActivity.class));
        }else if (spokenText.contains("flashlight")) {
            flashlightController.turnOnFlashlight();
        } else {
            textToSpeech.speak("Sorry, I couldn't understand the command", TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

      private void evaluateCalcExpression(String spokenText) {
        String[] tokens = spokenText.toLowerCase().split(" ");

        double num1 = Double.parseDouble(tokens[0]);
        String operator = tokens[1];
        double num2 = Double.parseDouble(tokens[2]);

        double result = 0;

        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*" :
                result = num1 * num2;
                break;
            case "into" :
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
            default:
                textToSpeech.speak("Sorry, I couldn't understand the expression", TextToSpeech.QUEUE_FLUSH, null, null);
                return;
        }
        textToSpeech.speak("The result is " + (int)result, TextToSpeech.QUEUE_FLUSH, null, null);
    }


    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            textToSpeech.speak("You need to give permission to access your location, try again afterwards" , TextToSpeech.QUEUE_FLUSH, null, null);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);

        } else {
            LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (!addresses.isEmpty()) {
                            Address address = addresses.get(0);
                            String locationName = address.getAddressLine(0);
                            Toast.makeText(getContext(), "Location:"+locationName, Toast.LENGTH_LONG).show();
                            Log.d("",locationName);
                            textToSpeech.speak("Your current location is " + locationName, TextToSpeech.QUEUE_FLUSH, null, null);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "Location not available", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

}