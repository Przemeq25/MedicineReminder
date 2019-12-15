package com.example.medicinereminder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton, signupButton;
    private EditText loginText, passwordText;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginText = findViewById(R.id.LoginText);
        passwordText = findViewById(R.id.PasswordText);
        loginButton = findViewById(R.id.LoginButton);
        signupButton = findViewById(R.id.SignUpButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });
    }



    private void changeActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


}
