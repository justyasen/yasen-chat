package com.example.yasenchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText password, email;
    Button registerButton, loginButton;

    //Checks whether email is valid or not by checking if it matches email pattern.
    private boolean isEmailValid(CharSequence email)
    {
        if(email == null)
        {
            return false;
        }
        else
        {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing all buttons/text fields for register/login page.
        mAuth = FirebaseAuth.getInstance();
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);

        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Checking whether email field is empty. 
                if(email.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this , "Please enter your email. ", Toast.LENGTH_SHORT).show();
                }
                else if (!isEmailValid(email.getText().toString())) //If it isn't empty, we go into the isEmailValid function.
                {
                    Toast.makeText(MainActivity.this, "Please enter a valid email. ", Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please enter your password. ", Toast.LENGTH_SHORT).show();
                }
                else //If everything passes, we create a user with the given email and password.
                {
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this, "Registration Successful ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Error in registration: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }


        });
    }
}