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

public class RegistrationActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button signUp;
    private TextView goToLogin;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email_register_field);
        password = findViewById(R.id.password_register_field);
        signUp = findViewById(R.id.register_button);
        goToLogin = findViewById(R.id.login_redirect);
        mFirebaseAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(v -> {
            String emailInput = email.getText().toString();
            String passwordInput = email.getText().toString();

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
                Toast.makeText(RegistrationActivity.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
            }

            else  if(!(emailInput.isEmpty() && passwordInput.isEmpty())) {
                mFirebaseAuth.createUserWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(
                        RegistrationActivity.this, task -> {
                            if(!task.isSuccessful())
                            {
                                System.out.println(task.getException().getMessage());
                                Toast.makeText(RegistrationActivity.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {

                                Intent intToHome = new Intent(RegistrationActivity.this, MainActivity.class);
                                startActivity(intToHome);
                            }
                        });
            }
            else
            {
                Toast.makeText(RegistrationActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();
            }

        });

        goToLogin.setOnClickListener(v -> {
            Intent i = new Intent (RegistrationActivity.this, LoginActivity.class);
            startActivity(i);
        });
    }
}
