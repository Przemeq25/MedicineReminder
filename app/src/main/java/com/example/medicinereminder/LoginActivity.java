package com.example.medicinereminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton, signupButton;
    private EditText loginText, passwordText;

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
                if(validate())
                {

                    Toast.makeText(LoginActivity.this, "Witaj " + loginText.getText().toString(), Toast.LENGTH_SHORT).show();
                    AddMedicinesFragment fragment =  new AddMedicinesFragment();
                    fragment.setName(loginText.getText().toString());
                    Intent it = new Intent(LoginActivity.this, MainActivity.class);
                    it.putExtra("IDENT",loginText.getText().toString());
                    startActivity(it);





                }


            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }



    private Boolean validate(){
        Boolean validate = false;
        User user = new User(loginText.getText().toString(),passwordText.getText().toString());
        String login = loginText.getText().toString();
        String password = passwordText.getText().toString();

        if(login.isEmpty() && password.isEmpty())
        {
            Toast.makeText(this, "Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show();
        }
        if (DataBaseController.checkLoginData(user.getLogin(),user.getPassword())){
            validate = true;
        }
        else
        {
            Toast.makeText(this, "Niepoprawne dane logowania", Toast.LENGTH_SHORT).show();
        }

        return validate;
    }

}
