package com.checking.choaching_app.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.checking.choaching_app.R;
import com.checking.choaching_app.activity.MainActivity;

public class UserLoginActivity extends AppCompatActivity {
    EditText userEmail;
    EditText userPassword, userName;
    Button userLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        userName = findViewById(R.id.user_name);

        userLoginButton = findViewById(R.id.user_login_button);
        userLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userPasswordValue = userPassword.getText().toString();
                String userEmailValue = userEmail.getText().toString();
                String userNameValue = userName.getText().toString();
                if(userNameValue.equals("")){
                    Toast.makeText(UserLoginActivity.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                }
                if (userEmailValue.equals("test@gmail.com") && userPasswordValue.equals("Test@12345")) {
                    Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                    intent.putExtra("userName", userNameValue);
                    intent.putExtra("userEmail", userEmailValue);
                    startActivity(intent);
                } else {
                    Toast.makeText(UserLoginActivity.this, "Please Enter the value again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}