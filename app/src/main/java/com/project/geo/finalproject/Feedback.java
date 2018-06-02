package com.project.geo.finalproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.geo.finalproject.Model.FeedbackClass;

public class Feedback extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference users;
    private DatabaseReference Feedback;

    EditText ET;
    Button BtnDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        String Email = getIntent().getStringExtra("Email");
        Email = Email.replace('.', ' ');


        final String[] Reason = new String[1];
        //get Instance from database
        database = FirebaseDatabase.getInstance();
        //getReference from users
        users = database.getReference("users");
        //getReference from feedback
        Feedback = database.getReference("feedback");


        final Spinner Sp = findViewById(R.id.spinner3);
        ET = findViewById(R.id.editText3);
        BtnDel = findViewById(R.id.button4);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Feedback.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Reason));
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        Sp.setAdapter(adapter);

        Sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 5) {
                    ET.setVisibility(View.VISIBLE);
                } else {
                    ET.setVisibility(View.GONE);
                    Reason[0] = Sp.getSelectedItem().toString();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.equals("")) {
                    Reason[0] = ET.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final String finalEmail = Email;
        BtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //////
                try {

                    final FeedbackClass Fb = new FeedbackClass(finalEmail, Reason[0]);

                    Feedback.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Feedback.push().setValue(Fb);


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(finalEmail).exists()) {
                                users.child(finalEmail).removeValue();
                                Intent intent = new Intent(Feedback.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(Feedback.this, "Account Deleted ", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } catch (Exception ex) {
                    Toast.makeText(Feedback.this, ex.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void fun() {
        GetPath Get = new GetPath();
        if (Get.readFromFile(Feedback.this).isEmpty() == false) {
            Typeface face = Typeface.createFromAsset(getAssets(), Get.readFromFile(Feedback.this));
            ET.setTypeface(face);
            BtnDel.setTypeface(face);
        }
        if (Get.readFromFile2(Feedback.this).isEmpty() == false) {
            Float size = Float.parseFloat(Get.readFromFile2(Feedback.this));
            ET.setTextScaleX(size);
            BtnDel.setTextScaleX(size);
        }
    }

}
