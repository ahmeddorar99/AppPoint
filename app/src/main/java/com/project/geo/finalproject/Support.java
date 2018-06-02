package com.project.geo.finalproject;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.geo.finalproject.Model.Tech_Problem;

import java.util.Calendar;

public class Support extends AppCompatActivity {
    String EMAIL, mEmail, Content;
    TextView tv, ContentMessage;
    Button BtnSend;
    Tech_Problem[] mtech_problem = new Tech_Problem[1];

    private FirebaseDatabase database;
    private DatabaseReference technicalProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        EMAIL = getIntent().getStringExtra("Username");
        mEmail = EMAIL.replace('.', ' ');
        tv = (TextView) findViewById(R.id.textView34);
        ContentMessage = (TextView) findViewById(R.id.EditT);
        tv.setText(EMAIL);
        BtnSend = (Button) findViewById(R.id.button7);
        database = FirebaseDatabase.getInstance();
        technicalProblem = database.getReference("TechnicalProblem");

        Fun();
        BtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Content = ContentMessage.getText().toString();
                if (Content.isEmpty()) {
                    Toast.makeText(Support.this, "Enter Your Message to send it ", Toast.LENGTH_SHORT).show();
                } else {
                    mtech_problem[0] = new Tech_Problem(Content,EMAIL, Calendar.getInstance().getTime().toString());
                    technicalProblem.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                technicalProblem.push().setValue(mtech_problem[0]);
                                Toast.makeText(Support.this, "Thanks  , We will Contact with you ", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            } catch (Exception ex) {
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }

    private void Fun() {
        GetPath Get = new GetPath();
        if (Get.readFromFile(Support.this).isEmpty() == false) {
            Typeface face = Typeface.createFromAsset(getAssets(), Get.readFromFile(Support.this));
            tv.setTypeface(face);
            ContentMessage.setTypeface(face);
            BtnSend.setTypeface(face);
        }
        if (Get.readFromFile2(Support.this).isEmpty() == false) {
            Float size = Float.parseFloat(Get.readFromFile2(Support.this));
            tv.setTextSize(size);
            ContentMessage.setTextSize(size);
            BtnSend.setTextSize(size);
        }
    }
}
