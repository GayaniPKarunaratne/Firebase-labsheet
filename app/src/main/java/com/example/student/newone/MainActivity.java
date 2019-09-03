package com.example.student.newone;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText txt_id, txt_name, txt_address, txt_contact;
    Button btn_save, btn_show, btn_update, btn_delete;
    Student student;
    DatabaseReference dbRsf;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_id = findViewById(R.id.txtid);
        txt_name = findViewById(R.id.txtName);
        txt_address = findViewById(R.id.txtAddress);
        txt_contact = findViewById(R.id.txtContact);

        btn_save = findViewById(R.id.btnSave);
        btn_show = findViewById(R.id.btnShow);
        btn_delete = findViewById(R.id.btnDelete);
        btn_update = findViewById(R.id.btnUpdate);

        student = new Student();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRsf = FirebaseDatabase.getInstance().getReference().child("Student");

                student.setId(txt_id.getText().toString().trim());
                student.setAddress(txt_address.getText().toString().trim());
                student.setName(txt_name.getText().toString().trim());
                student.setContactNum(Integer.parseInt(txt_contact.getText().toString().trim()));

                // dbRsf.push().setValue(student);
                dbRsf.child("st1").setValue(student);
                Toast.makeText(getApplicationContext(), "Adding Succes", Toast.LENGTH_LONG).show();
                clearData();

            }
        });


        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Student").child("st1");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()){
                            txt_id.setText(dataSnapshot.child("id").getValue().toString());
                            txt_name.setText(dataSnapshot.child("name").getValue().toString());
                            txt_address.setText(dataSnapshot.child("address").getValue().toString());
                            txt_contact.setText(dataSnapshot.child("contactNum").getValue().toString());
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No Source to display",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



    }



    public void clearData(){
        txt_id.setText("");
        txt_name.setText("");
        txt_address.setText("");
        txt_contact.setText("");
    }
}
