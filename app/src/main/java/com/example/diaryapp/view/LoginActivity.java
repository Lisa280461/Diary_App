package com.example.diaryapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diaryapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button signIn;
    private FirebaseAuth mFirebaseAuth;
    private AuthStateListener authStateListener;
    private TextView goToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email_login_field);
        password = findViewById(R.id.password_login_field);
        signIn = findViewById(R.id.login_button);
        goToRegister = findViewById(R.id.register_redirect);

        authStateListener = firebaseAuth -> {
            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
            if( mFirebaseUser != null )
            {
                Toast.makeText(LoginActivity.this,"You are logged in!",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, NewEntryFragment.class);
                startActivity(i);
            }
            else Toast.makeText(LoginActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
        };

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = email.getText().toString();
                String passwordInput = password.getText().toString();


                if(emailInput.isEmpty())
                {
                    email.setError("Email field cannot be empty!");
                    email.requestFocus();
                }
                else if(passwordInput.isEmpty())
                {
                    password.setError("Password field cannot be empty!");
                    password.requestFocus();
                }
                else  if(emailInput.isEmpty() && passwordInput.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(emailInput.isEmpty() && passwordInput.isEmpty()))
                {
                    mFirebaseAuth.signInWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(LoginActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()){
                                        System.out.println(task.getException().getMessage());
                                        Toast.makeText(LoginActivity.this,"Something went wrong, please try again!",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Intent intToHome = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intToHome);
                                    }
                                }
                            });
                }
            }
        });

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intSignUp);
            }
        });

    }


}