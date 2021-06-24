package com.example.vbreakdown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FuelAct extends AppCompatActivity {
    EditText phone;
    EditText reg;
    EditText fuel;
    EditText quantity;
    Button submit;
    Button mapbtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel);
        phone=findViewById(R.id.phone);
        fuel=findViewById(R.id.etfuel);
        reg=findViewById(R.id.etregno);
        quantity=findViewById(R.id.etQuantity);
        submit=findViewById(R.id.submit);
        mapbtn=findViewById(R.id.btnmap);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode= FirebaseDatabase.getInstance();
                reference=rootNode.getReference("FuelRequest");
                int q=Integer.parseInt(quantity.getEditableText().toString());
                String ph=phone.getEditableText().toString();
                String f=fuel.getEditableText().toString();
                String r=reg.getEditableText().toString();
                if(q>5 &&!ph.isEmpty()&&!f.isEmpty()&&!r.isEmpty()) {
                    userHelper d = new userHelper(ph,r,f,q);
                    reference.child(ph).setValue(d);
                    Toast.makeText(FuelAct.this,"Request sent successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(FuelAct.this,HomeActivity.class));
                }
                else{
                    Toast.makeText(FuelAct.this,"Fill the form completely and correctly",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FuelAct.this,MapActivity.class));
            }
        });
    }
}