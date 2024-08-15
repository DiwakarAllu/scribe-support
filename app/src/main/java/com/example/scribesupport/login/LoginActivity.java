package com.example.scribesupport.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scribesupport.MainActivity;
import com.example.scribesupport.R;
import com.example.scribesupport.SignupActivity;
import com.example.scribesupport.home.HomeActivity;
import com.example.scribesupport.splashscreen.FrontPageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText,forgotpassword;

    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        forgotpassword=findViewById(R.id.forgetPassWordRedirectText);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e=loginUsername.getText().toString();
                String p=loginPassword.getText().toString();

                if(e.isEmpty()){
                    loginUsername.setError("Please Enter Valid Email");
                }else if(p.isEmpty()){
                    loginPassword.setError("Password is empty");
                }else {
                        auth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
           }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.scribesupport.login.LoginActivity.this, FrontPageActivity.class);
                startActivity(intent);
              //  moveTaskToBack(false);
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });

        if(user!=null){
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }else {
            // startActivity(new Intent(LoginActivity.this,FrontPageActivity.class));
        }
    }



}