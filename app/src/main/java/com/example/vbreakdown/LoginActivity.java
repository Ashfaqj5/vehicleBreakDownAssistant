package com.example.vbreakdown;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vbreakdown.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
    EditText uemail;
    EditText upass;
    Button ulogin;
    Button uregister;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uemail = findViewById(R.id.username);
        upass=findViewById(R.id.password);
        ulogin= findViewById(R.id.login);
        uregister = findViewById(R.id.btnNewUser);
        firebaseAuth=FirebaseAuth.getInstance();
//        FirebaseUser user=firebaseAuth.getCurrentUser();
//        if(user!=null){
//            finish();
//            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//        }
        ulogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ue=uemail.getText().toString().trim();
                String up=upass.getText().toString().trim();
                if(ue.isEmpty()||up.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Please enter credentials",Toast.LENGTH_SHORT).show();
                }else
                validate(ue,up);
            }
        });
        uregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });
    }
    private void validate(String e,String p){
        firebaseAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"loggin successfull",Toast.LENGTH_SHORT).show();
                    startActivity((new Intent(LoginActivity.this,HomeActivity.class)));
                }
                else{
                    Toast.makeText(LoginActivity.this, "email password doesn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}