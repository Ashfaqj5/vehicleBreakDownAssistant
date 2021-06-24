package com.example.vbreakdown;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class TowingAct extends AppCompatActivity {
    Button towService;
    private static final int REQUEST_CALL=1;
    EditText wheel;
    EditText phone;
    EditText model;
    EditText reg;
    EditText add;
    Button submit;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_towing);
        towService=findViewById(R.id.towService);
        towService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
        wheel=findViewById(R.id.nWheels);
        phone=findViewById(R.id.phone);
        model=findViewById(R.id.model);
        reg=findViewById(R.id.reg);
        add=findViewById(R.id.additional);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode= FirebaseDatabase.getInstance();
                reference=rootNode.getReference("TowingRequest");
                int w=Integer.parseInt(wheel.getEditableText().toString());
                String ph=phone.getEditableText().toString();
                String m=model.getEditableText().toString();
                String r=reg.getEditableText().toString();
                String ad=add.getEditableText().toString();
                if(w>1 &&!ph.isEmpty()&&!m.isEmpty()&&!r.isEmpty()) {
                    userHelper d = new userHelper(w, ph, m, r, ad);
                    reference.child(ph).setValue(d);
                    Toast.makeText(TowingAct.this,"Request sent successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(TowingAct.this,HomeActivity.class));
                }
                else{
                    Toast.makeText(TowingAct.this,"Fill the form completely and correctly",Toast.LENGTH_SHORT).show();
                }
//
            }
        });
    }
    private void makePhoneCall(){
        String ph="tel:"+"9985044924";
        if(ContextCompat.checkSelfPermission(TowingAct.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(TowingAct.this,
                    new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }else{
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(ph)));
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        if(requestCode==REQUEST_CALL){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }
            else{
                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}