package com.example.vbreakdown;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class RegistrationActivity extends AppCompatActivity {
    private EditText userName,userEmail,userPass,userPassConfirm;
    private Button regButton;
    private TextView RLogin;
    private FirebaseAuth fbAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();
        fbAuth=FirebaseAuth.getInstance();
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    //upload to database
                    String e=userEmail.getText().toString().trim();
                    String p=userPass.getText().toString().trim();
                    fbAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(RegistrationActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                            }
                            else
                                Toast.makeText(RegistrationActivity.this,"Registeration falied",Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });
        RLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }
    private void setupUIViews(){
        userName=(EditText)findViewById(R.id.etUserName);
        userEmail=(EditText)findViewById(R.id.etUserEmail);
        userPass=(EditText)findViewById(R.id.etpw);
        userPassConfirm=(EditText)findViewById(R.id.etConfirmpw);
        regButton=(Button)findViewById(R.id.btnRegister);
        RLogin=(TextView)findViewById(R.id.Rlogin);
    }
    private boolean validate(){
        Boolean result=false;
        String n=userName.getText().toString();
        String e=userEmail.getText().toString();
        String p=userPass.getText().toString();
        String cp=userPassConfirm.getText().toString();
        if(n.isEmpty()||e.isEmpty()|| p.isEmpty()){
            Toast.makeText(this,"Please enter the details ",Toast.LENGTH_SHORT).show();
        }
        else if(!p.equals(cp)){
            Toast.makeText(this,"passwords doesn't match  ",Toast.LENGTH_SHORT).show();
        }
        else{
            result=true;
        }
        return result;
    }
}