package com.project.geo.finalproject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.project.geo.finalproject.Model.user;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference users;

    EditText editEmail, editPassword, editCPassword, editPhone, editFName, editLName, birthdate;
    String Gender, Birthdate, PhoneNumber, email;
    RadioGroup radio;
    RadioButton Male, Female;
    TextView Login, registClick;

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("users");

        editFName = findViewById(R.id.Fname);
        editLName = findViewById(R.id.Lname);
        editEmail = findViewById(R.id.Email);
        radio = findViewById(R.id.radiogro);
        Male = findViewById(R.id.male);
        Female = findViewById(R.id.female);
        editPassword = findViewById(R.id.Password);
        editCPassword = findViewById(R.id.Cpassword);
        birthdate = findViewById(R.id.birthdate);

        editPhone = findViewById(R.id.phone);
        final Calendar myCalendar = Calendar.getInstance();
        registClick = findViewById(R.id.RegistClick);
        Login = findViewById(R.id.Loginbak);

        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Registration.this,
                        MainActivity.class);
                startActivity(intent);
            }

        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                String myFormat = "dd/mm/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                // Birthdate = sdf.format(myCalendar.getTime());
                int month = 0;
                month = monthOfYear;
                month = month + 1;
                Birthdate = dayOfMonth + " / " + month + " / " + year;
                ((EditText) findViewById(R.id.birthdate)).setText(Birthdate);
            }

        };

        birthdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Registration.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        registClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {


                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        //we are connected to a network

                        if (editFName.getText().toString().isEmpty() == false && editLName.getText().toString().isEmpty() == false
                                && editEmail.getText().toString().isEmpty() == false && editPhone.getText().toString().isEmpty() == false &&
                                editPassword.getText().toString().isEmpty() == false && Birthdate.isEmpty() == false)

                        {
                            if (Male.isChecked()) {
                                Gender = "Male";
                            } else if (Female.isChecked()) {
                                Gender = "Female";

                            } else {
                                Gender = "Unknown";

                            }


                            if (editPhone.getText().toString().length() == 11) {
                                PhoneNumber = editPhone.getText().toString();
                            } else {

                                if (editPhone.getText().toString().isEmpty() == true || editPhone.getText().toString().length() > 11
                                        || editPhone.getText().toString().length() < 11)
                                    Toast.makeText(Registration.this, "Invalid phone number", Toast.LENGTH_SHORT).show();

                            }

                            if (isEmailValid(editEmail.getText().toString())) {
                                email = editEmail.getText().toString();

                                email = email.replace('.', ' ');

                                final user User = new user(editFName.getText().toString(),
                                        editLName.getText().toString(), email,
                                        editPassword.getText().toString(), PhoneNumber, birthdate.getText().toString(), Gender, "false");


                                users.addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.child(email).exists()) {

                                            if (editEmail.getText().toString().isEmpty() == false) {
                                                Toast.makeText(Registration.this, "This User already exist", Toast.LENGTH_SHORT).show();
                                            }

                                        } else {

                                            if (editPassword.getText().toString().equals(editCPassword.getText().toString())) {
                                                users.child(User.getEmail()).setValue(User);
                                                onBackPressed();
                                                Toast.makeText(Registration.this, "Sucsses Registed", Toast.LENGTH_SHORT).show();


                                            } else {
                                                Toast.makeText(Registration.this, "plz enter the same password ", Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(Registration.this, " We wait you for ever ", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            } else {

                                Toast.makeText(Registration.this, "Invalid Email ", Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            Toast.makeText(Registration.this, "Enter your data to complete registration  ", Toast.LENGTH_SHORT).show();


                        }
                    } else {
                        Toast.makeText(Registration.this, "No internet Connection exist  ", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception ex) {

                }
            }


        });


    }

    public void Fun() {
        GetPath Get = new GetPath();
        if (Get.readFromFile(Registration.this).isEmpty() == false) {
            Typeface face = Typeface.createFromAsset(getAssets(), Get.readFromFile(Registration.this));
            editFName.setTypeface(face); editLName.setTypeface(face); editEmail.setTypeface(face); Male.setTypeface(face);
            Female.setTypeface(face); birthdate.setTypeface(face); editPassword.setTypeface(face); editCPassword.setTypeface(face);
            registClick.setTypeface(face); Login.setTypeface(face);
        }
        if (Get.readFromFile2(Registration.this).isEmpty() == false) {
            Float size = Float.parseFloat(Get.readFromFile2(Registration.this));
            editFName.setTextScaleX(size); editLName.setTextScaleX(size); editEmail.setTextScaleX(size); Male.setTextScaleX(size);
            Female.setTextScaleX(size); birthdate.setTextScaleX(size); editPassword.setTextScaleX(size); editCPassword.setTextScaleX(size);
            registClick.setTextScaleX(size); Login.setTextScaleX(size);
        }

    }

}
