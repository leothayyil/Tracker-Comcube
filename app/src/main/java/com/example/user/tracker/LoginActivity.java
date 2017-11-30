package com.example.user.tracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         username = (EditText)findViewById(R.id.EdtusernameId);
         password = (EditText)findViewById(R.id.EdtPasswordId);
        login = (Button) findViewById(R.id.loginBtn);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){


                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);

                }else{

                    Toast.makeText(LoginActivity.this, "Please Enter Valid Details", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
