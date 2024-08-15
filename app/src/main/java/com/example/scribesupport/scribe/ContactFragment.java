package com.example.scribesupport.scribe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.scribesupport.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ContactFragment extends Fragment {

    View view;
    Button makeCall,sendEmail;
    TextView name,phoneNumber,email,languages;
    HashMap<String, Object> userData;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        makeCall=view.findViewById(R.id.makecall_button);
        sendEmail=view.findViewById(R.id.send_email_button);

        name=view.findViewById(R.id.username1);
        phoneNumber=view.findViewById(R.id.phone_number);
        email=view.findViewById(R.id.email);
        languages=view.findViewById(R.id.languages);


        Bundle args = getArguments();
        if (args != null) {
          userData = (HashMap<String, Object>) args.getSerializable("userData");
            if (userData != null) {
                String name1 = (String) userData.get("userName");
                String phoneNumber1 = (String) userData.get("phoneNumber");
                String email1 = (String) userData.get("email");
                Object l = new ArrayList<>();
                l=userData.get("Languages known");

                StringBuilder languagesBuilder = new StringBuilder();
                if (l instanceof ArrayList<?>) {
                    ArrayList<String> languagesList = (ArrayList<String>) l;
                    for (String language : languagesList) {
                        Log.d("Languages", "Adding language: " + language);
                        languagesBuilder.append(language).append("\n\n");
                    }
                }
                name.setText(name1);
                phoneNumber.setText(phoneNumber1);
                email.setText(email1);
                languages.setText(languagesBuilder.toString());

            }
        }

        makeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the phone number from the user data
                String phoneNumber = (String) userData.get("phoneNumber");

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = (String) userData.get("email");

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + email));
                startActivity(Intent.createChooser(emailIntent, "Send Email"));
            }
        });

        return view;
    }


}