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

public class AdminLoginActivity extends AppCompatActivity {
    EditText adminEmail;
    EditText adminPassword;
    Button adminLoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adminEmail = findViewById(R.id.admin_email);
        adminPassword = findViewById(R.id.admin_password);


        adminLoginButton = findViewById(R.id.admin_login_button);
        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String adminEmailValue = adminEmail.getText().toString();
                String adminPasswordValue = adminPassword.getText().toString();
                if(adminEmailValue.equals("test@gmail.com") && adminPasswordValue.equals("Test@12345")){
                    Intent intent = new Intent(AdminLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AdminLoginActivity.this, "Please Enter the value again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}