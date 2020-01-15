package com.example.medicinereminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private Button loginButton,signupButton;
    private EditText loginText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        loginText = findViewById(R.id.LoginText);
        passwordText = findViewById(R.id.PasswordText);
        loginButton = findViewById(R.id.LogButton);
        signupButton = findViewById(R.id.RegisterButton);

            loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
            signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    String login = loginText.getText().toString();
                    String password = passwordText.getText().toString();
                    DataBaseController.createUser(login,password);
                    Toast.makeText(SignUpActivity.this, "Rejestracja zakończona sukcesem", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                }

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
        }else if(password.length()<4)
        {
            Toast.makeText(this, "Hasło musi się składać z przynajmniej 4 znaków", Toast.LENGTH_SHORT).show();
        }
        else if(DataBaseController.checkUserExists(login)){
            Toast.makeText(this, "Uzytkownik o takim loginie juz istnieje", Toast.LENGTH_SHORT).show();
        }else{
            validate= true;
        }

        return validate;
    }
}
