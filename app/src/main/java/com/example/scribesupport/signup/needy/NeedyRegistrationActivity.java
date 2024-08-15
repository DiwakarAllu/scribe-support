package com.example.scribesupport.signup.needy;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.scribesupport.signup.volunteer.FrontPageVolunteerActivity;
import com.example.scribesupport.signup.volunteer.VolunteerRegistrationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class NeedyRegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, GestureDetector.OnGestureListener {

    private Toolbar toolbar;
    private EditText nameEditText, emailEditText,phoneNum,password, confmpass, address,pin;
    private TextView dob;
    private Spinner stateSpinner, districtSpinner, citySpinner;
    private Button registerButton;

    private RadioGroup radioGrp;

    private String uGender;
    private String uDOB;

    FirebaseAuth mAuth;
    DatabaseReference reference;



    Map<String, List<String>> stateDistrictMap = new HashMap<>();
    Map<String, List<String>> districtCityMap = new HashMap<>();


    private TextToSpeech textToSpeech;

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    private GestureDetectorCompat gestureDetector;

    String[] prompts={"Whats your name?","Whats your email Id?","Whats your phone number","Tell your password","repeat your password","Are you male or female","Whats your date of birth","tell your address","Whats your pin code","Your state is","your district","Your city"};
    ArrayList<EditText> editTextList = new ArrayList<>();
    private int idx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_needy_registration);

        nameEditText=findViewById(R.id.name);
        emailEditText=findViewById(R.id.email);
        phoneNum=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        confmpass=findViewById(R.id.cpass);
        toolbar=findViewById(R.id.toolbar);
        address=findViewById(R.id.address);
        pin=findViewById(R.id.pin);


        editTextList.add(nameEditText);
        editTextList.add(emailEditText);
        editTextList.add(phoneNum);
        editTextList.add(password);
        editTextList.add(confmpass);
        editTextList.add(address);
        editTextList.add(pin);



        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.US);
                    speakNextPrompt(0);
                    speakOut();
                }
            }
        });


        toolbar=findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NeedyRegistrationActivity.this, FrontPageNeedyActivity.class));
            }
        });

        radioGrp = findViewById(R.id.gender);
        radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                //statusTV.setText(radioButton.getText());
                uGender=radioButton.getText().toString();
                Toast.makeText(NeedyRegistrationActivity.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        dob=findViewById(R.id.dob);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NeedyRegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        c.set(year, month, day);
                        //dob.setText(dateFormat.format(c.getTime()));
                        dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        uDOB= (dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        stateSpinner=findViewById(R.id.state);
        districtSpinner=findViewById(R.id.dist);
        citySpinner=findViewById(R.id.city);

        stateSpinner.getBackground().setColorFilter(getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC_ATOP);
        districtSpinner.getBackground().setColorFilter(getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC_ATOP);
        citySpinner.getBackground().setColorFilter(getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC_ATOP);
//--------------------------------------------------------------------

        stateDistrictMap.put("Andhra Pradesh", Arrays.asList("Krishna", "Guntur", "Vizag"));
        stateDistrictMap.put("Karnataka", Arrays.asList("Bengaluru", "Mysuru", "Dharwad"));
        stateDistrictMap.put("Tamil Nadu", Arrays.asList("Chennai", "Coimbatore", "Madurai"));
        stateDistrictMap.put("Maharashtra", Arrays.asList("Mumbai", "Pune", "Nagpur"));

        // Populate the district and city map
        districtCityMap.put("Krishna", Arrays.asList("Vijayawada", "Machilipatnam", "Gudlavalleru"));
        districtCityMap.put("Guntur", Arrays.asList("Guntur", "Narasaraopet", "Tenali"));
        districtCityMap.put("Vizag", Arrays.asList("Visakhapatnam", "Anakapalle", "Narsipatnam"));
        districtCityMap.put("Bengaluru", Arrays.asList("Bangalore", "Ramanagara", "Chikkaballapur"));
        districtCityMap.put("Mysuru", Arrays.asList("Mysore", "Mandya", "Chamrajanagar"));
        districtCityMap.put("Chennai", Arrays.asList("Chennai", "Tambaram", "Avadi"));
        districtCityMap.put("Coimbatore", Arrays.asList("Coimbatore", "Pollachi", "Tiruppur"));
        districtCityMap.put("Madurai", Arrays.asList("Madurai", "Usilampatti", "Melur"));
        districtCityMap.put("Mumbai", Arrays.asList("Mumbai", "Navi Mumbai", "Thane"));
        districtCityMap.put("Pune", Arrays.asList("Pune", "Solapur", "Satara"));
        districtCityMap.put("Nagpur", Arrays.asList("Nagpur", "Wardha", "Chandrapur"));


//        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(stateDistrictMap.keySet()));
//        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        stateSpinner.setAdapter(stateAdapter);

        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, R.layout.spinner_list, new ArrayList<>(stateDistrictMap.keySet()));
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);


        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedState = (String) parent.getItemAtPosition(position);
                List<String> districts = stateDistrictMap.get(selectedState);

                ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_list, districts);
                districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                districtSpinner.setAdapter(districtAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDistrict = (String) parent.getItemAtPosition(position);
                List<String> cities = districtCityMap.get(selectedDistrict);

                ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_list, cities);
                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySpinner.setAdapter(cityAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statesInIndia);
//        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        stateSpinner.setAdapter(stateAdapter);
//        stateSpinner.setOnItemSelectedListener(this);
//
//        ArrayAdapter<String> distAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statesInIndia);
//        distAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        districtSpinner.setAdapter(stateAdapter);
//        districtSpinner.setOnItemSelectedListener(this);


        registerButton=findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth = FirebaseAuth.getInstance();
                reference = FirebaseDatabase.getInstance().getReference("Needy User Data");

                String n = nameEditText.getText().toString();
                String e = emailEditText.getText().toString();
                String p = password.getText().toString();
                String c = confmpass.getText().toString();
                String ph = phoneNum.getText().toString();
                String addr=address.getText().toString();
                String pc = pin.getText().toString();

                if(n.isEmpty()){
                    nameEditText.setError("Please Enter Your Name");
                }else if(e.isEmpty()){
                    emailEditText.setError("Please Enter Your Email");
                }else if(p.isEmpty()){
                    password.setError("Please Enter Your Password");
                }else if(c.isEmpty() || !p.equals(c)){
                    confmpass.setError("Please Check Your Password, it didn't match!");
                }else{
                    mAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                FirebaseUser user = mAuth.getCurrentUser();
                                // HelperClass helperClass = new HelperClass(n, e, ph, p,uGender,uDOB,addr,pc);
                                //  reference.child(user.getUid()).setValue(helperClass);

                                HashMap<String,Object> map = new HashMap<>();
                                map.put("userName",n);
                                map.put("email",e);
                                map.put("phoneNumber",ph);
                                map.put("password",p);
                                map.put("gender",uGender);
                                map.put("DOB",uDOB);
                                map.put("address",addr);
                                map.put("pinCode",pc);

                                reference.child(user.getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(NeedyRegistrationActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(NeedyRegistrationActivity.this, "Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }

            }
        });
    }

    private void speakNextPrompt(int idx){
        textToSpeech.speak(prompts[idx],TextToSpeech.QUEUE_ADD,null,null);
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
            Toast.makeText(getApplicationContext(), " " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (idx < editTextList.size()) {
                editTextList.get(idx).setText(result.get(0));
                idx++;
                // Continue to prompt speech input for the next TextView
                if (idx < editTextList.size()) {
                    speakNextPrompt(idx);
                    speakOut();
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Toast.makeText(this, "Please select your state", Toast.LENGTH_SHORT).show();
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
        return false;
    }

    @Override
    public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {
        submitForm();
    }

    @Override
    public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
//


private void submitForm(){

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Needy User Data");

        String n = nameEditText.getText().toString();
        String e = emailEditText.getText().toString();
        String p = password.getText().toString();
        String c = confmpass.getText().toString();
        String ph = phoneNum.getText().toString();
        String addr=address.getText().toString();
        String pc = pin.getText().toString();

        if(n.isEmpty()){
            nameEditText.setError("Please Enter Your Name");
        }else if(e.isEmpty()){
            emailEditText.setError("Please Enter Your Email");
        }else if(p.isEmpty()){
            password.setError("Please Enter Your Password");
        }else if(c.isEmpty() || !p.equals(c)){
            confmpass.setError("Please Check Your Password, it didn't match!");
        }else{
            mAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        FirebaseUser user = mAuth.getCurrentUser();
                        // HelperClass helperClass = new HelperClass(n, e, ph, p,uGender,uDOB,addr,pc);
                        //  reference.child(user.getUid()).setValue(helperClass);

                        HashMap<String,Object> map = new HashMap<>();
                        map.put("userName",n);
                        map.put("email",e);
                        map.put("phoneNumber",ph);
                        map.put("password",p);
                        map.put("gender",uGender);
                        map.put("DOB",uDOB);
                        map.put("address",addr);
                        map.put("pinCode",pc);

                        reference.child(user.getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(NeedyRegistrationActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(NeedyRegistrationActivity.this, "Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }
}


    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}


