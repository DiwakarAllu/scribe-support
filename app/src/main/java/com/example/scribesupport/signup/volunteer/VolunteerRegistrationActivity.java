package com.example.scribesupport.signup.volunteer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.scribesupport.R;
import com.example.scribesupport.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
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

public class VolunteerRegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;
    private EditText nameEditText, emailEditText,phoneNum,password,confmpass,address,pin;
    private TextView dob;
    private Spinner stateSpinner, districtSpinner, citySpinner;
    private Button registerButton;

    private RadioGroup radioGrp;

    private String uGender;
    private String uDOB;

    FirebaseAuth mAuth;
    DatabaseReference reference;

    CheckBox eng,tel,tamil,kan,hin;

    private List<String> selectedLanguages = new ArrayList<>();
    String[] statesInIndia = {
            "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar",
            "Chhattisgarh", "Goa", "Gujarat", "Haryana",
            "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka",
            "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
            "Meghalaya", "Mizoram", "Nagaland", "Odisha",
            "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu",
            "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand",
            "West Bengal", "Andaman and Nicobar Islands", "Chandigarh",
            "Dadra and Nagar Haveli and Daman and Diu", "Delhi", "Lakshadweep",
            "Puducherry"
    };

    Map<String, List<String>> stateDistrictMap = new HashMap<>();
    Map<String, List<String>> districtCityMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_volunteer_registration);

        nameEditText=findViewById(R.id.name);
        emailEditText=findViewById(R.id.email);
        phoneNum=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        confmpass=findViewById(R.id.cpass);
        toolbar=findViewById(R.id.toolbar);
        address=findViewById(R.id.address);
        pin=findViewById(R.id.pin);


        toolbar=findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VolunteerRegistrationActivity.this,FrontPageVolunteerActivity.class));
            }
        });
        radioGrp = findViewById(R.id.gender);
        radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                //statusTV.setText(radioButton.getText());
                uGender=radioButton.getText().toString();
                Toast.makeText(VolunteerRegistrationActivity.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(VolunteerRegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                                c.set(year, month, day);
//                                dob.setText(dateFormat.format(c.getTime()));
//                                uDOB=dateFormat.format(c.getTime());
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

        // Populate the state and district map
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


        eng=findViewById(R.id.english);
        tel=findViewById(R.id.telugu);
        kan=findViewById(R.id.kannada);
        hin=findViewById(R.id.hindi);
        tamil=findViewById(R.id.tamil);


//--------------------------------------------------------------------------------------------------
        registerButton=findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(eng.isChecked()){
                    selectedLanguages.add("English");
                }
                if (tel.isChecked()){
                    selectedLanguages.add("Telugu");
                }
                if(hin.isChecked()){
                    selectedLanguages.add("Hindi");
                }
                if (kan.isChecked()){
                    selectedLanguages.add("Kannada");
                }
                if(tamil.isChecked()){
                    selectedLanguages.add("Tamil");
                }



                mAuth = FirebaseAuth.getInstance();
                reference = FirebaseDatabase.getInstance().getReference("Volunteer User Data");

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
                                //  HelperClass helperClass = new HelperClass(n, e, ph, p,uGender,uDOB,addr,pc);
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
                                map.put("Languages known",selectedLanguages);

                                reference.child(user.getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(VolunteerRegistrationActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                                            selectedLanguages.clear();
                                            startActivity(new Intent(VolunteerRegistrationActivity.this, LoginActivity.class));
                                        }else{
                                            Toast.makeText(VolunteerRegistrationActivity.this, "Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            selectedLanguages.clear();
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
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//        Toast.makeText(getApplicationContext(),
//                        statesInIndia[position],
//                        Toast.LENGTH_LONG).show();
//        String selectedState = (String) parent.getItemAtPosition(position);
//        List<String> districts = stateDistrictMap.get(selectedState);
//        ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, districts);
//        districtSpinner.setAdapter(districtAdapter);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
       // Toast.makeText(this, "Please select your state", Toast.LENGTH_SHORT).show();
    }
}