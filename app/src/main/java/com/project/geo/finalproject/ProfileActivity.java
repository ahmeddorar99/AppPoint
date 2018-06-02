package com.project.geo.finalproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.geo.finalproject.Model.user;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends Activity {

    private TextView mfullname, memail;
    private FirebaseDatabase database;
    private DatabaseReference users;

    private EditText Fname, Lname, Phone, Gender, Birthdate, Password, Cpassword, NFname, NLname, NPhone, NBirthdate;
    private String NewFname, NewLname, NewPhone, NewBirthdate, NewPassword, Active, mEmail, GenderStr, mBirthdate, EMAIL, ProfileImage;
    private Button updateBtn, SaveBtn, Delete, BtnActive;
    user UserData;
    private CircleImageView mimageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        UserData = new user();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("users");
        ProfileImage = getIntent().getStringExtra("ProfileImage");
        EMAIL = getIntent().getStringExtra("Username");
        mEmail = EMAIL.replace('.', ' ');

        mfullname = (TextView) findViewById(R.id.fullname);
        memail = (TextView) findViewById(R.id.email);
        memail.setText(EMAIL);

        Fname = (EditText) findViewById(R.id.fname);
        Lname = (EditText) findViewById(R.id.lname);
        Phone = (EditText) findViewById(R.id.phone);
        Gender = (EditText) findViewById(R.id.gender);
        Password = (EditText) findViewById(R.id.Password);
        Cpassword = (EditText) findViewById(R.id.Cpassword);

        Birthdate = (EditText) findViewById(R.id.birthdate);
        mimageButton = (CircleImageView) findViewById(R.id.profile_image);

        updateBtn = (Button) findViewById(R.id.btnupdate);
        SaveBtn = (Button) findViewById(R.id.btnsave);
        Delete = (Button) findViewById(R.id.btndelete);

        NFname = (EditText) findViewById(R.id.fname2);
        NLname = (EditText) findViewById(R.id.lname2);
        NPhone = (EditText) findViewById(R.id.phone2);
        NBirthdate = (EditText) findViewById(R.id.birthdate2);
        BtnActive = (Button) findViewById(R.id.button6);

        if (ProfileImage != null) {
            Picasso.get().load(ProfileImage).into(mimageButton);
        }

        ChangFont();

        final Calendar myCalendar = Calendar.getInstance();
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
                int month = 0;
                month = monthOfYear;
                month = month + 1;
                mBirthdate = dayOfMonth + " / " + month + " / " + year;
                ((EditText) findViewById(R.id.birthdate)).setText(mBirthdate);
            }

        };


        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(mEmail).exists()) {
                    UserData = dataSnapshot.child(mEmail).getValue(user.class);
                    mfullname.setText(UserData.getFirst_name() + " " + UserData.getLast_name());

                    Gender.setText(UserData.getGender());
                    GenderStr = UserData.getGender();
                    Phone.setText(UserData.getPhone());
                    Birthdate.setText(UserData.getDate());
                    Active = UserData.getActivation();
                    Fname.setText(UserData.getFirst_name());
                    Lname.setText(UserData.getLast_name());

                    NPhone.setText(UserData.getPhone());
                    NBirthdate.setText(UserData.getDate());
                    NFname.setText(UserData.getFirst_name());
                    NLname.setText(UserData.getLast_name());
                    if (UserData.getImage_url() != null) {
                        mimageButton.setImageURI(Uri.parse(UserData.getImage_url()));
                    }
                    if (Active.equals("null")) {
                        BtnActive.setVisibility(View.VISIBLE);
                    } else if (Active.equals("true")) {
                        BtnActive.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = SaveBtn.getVisibility();
                if (i == 8) {
                    NFname.setVisibility(View.GONE);
                    NLname.setVisibility(View.GONE);
                    NBirthdate.setVisibility(View.GONE);
                    NPhone.setVisibility(View.GONE);

                    Fname.setVisibility(View.VISIBLE);
                    Lname.setVisibility(View.VISIBLE);
                    Birthdate.setVisibility(View.VISIBLE);
                    Phone.setVisibility(View.VISIBLE);
                    Password.setVisibility(View.VISIBLE);
                    Cpassword.setVisibility(View.VISIBLE);
                    SaveBtn.setVisibility(View.VISIBLE);
                } else {
                    Fname.setVisibility(View.GONE);
                    Lname.setVisibility(View.GONE);
                    Birthdate.setVisibility(View.GONE);
                    Phone.setVisibility(View.GONE);
                    SaveBtn.setVisibility(View.GONE);
                    Password.setVisibility(View.GONE);
                    Cpassword.setVisibility(View.GONE);

                    SaveBtn.setVisibility(View.GONE);
                    NFname.setVisibility(View.VISIBLE);
                    NLname.setVisibility(View.VISIBLE);
                    NBirthdate.setVisibility(View.VISIBLE);
                    NPhone.setVisibility(View.VISIBLE);
                }
            }
        });
        Birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ProfileActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NewFname = Fname.getText().toString();
                NewLname = Lname.getText().toString();
                NewPhone = Phone.getText().toString();
                NewBirthdate = Birthdate.getText().toString();
                NewPassword = Password.getText().toString();

                final user NewUser = new user(NewFname, NewLname, mEmail, NewPassword, NewPhone, GenderStr, NewBirthdate, Active);
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(mEmail).exists()) {
                            if (Password.getText().toString().equals(Cpassword.getText().toString())) {
                                users.child(NewUser.getEmail()).setValue(NewUser);

                            } else {
                                Toast.makeText(ProfileActivity.this, "plz enter the same password ", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });


        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProfileActivity.this, Feedback.class);
                intent.putExtra("Email", EMAIL);
                startActivity(intent);

            }
        });

        mimageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UploudImage.class);
                intent.putExtra("Email", EMAIL);
                startActivity(intent);
            }
        });
    }


    public void fun(View v) {
        UserData.Activation();
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(mEmail).exists()) {
                    users.child(UserData.getEmail()).setValue(UserData);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        onBackPressed();
    }

    public void ChangFont() {
        GetPath Get = new GetPath();

        if (Get.readFromFile(ProfileActivity.this).isEmpty() == false) {
            Typeface face = Typeface.createFromAsset(getAssets(), Get.readFromFile(ProfileActivity.this));
            Fname.setTypeface(face); memail.setTypeface(face); mfullname.setTypeface(face); Lname.setTypeface(face);
            Fname.setTypeface(face); Phone.setTypeface(face); Gender.setTypeface(face); Birthdate.setTypeface(face);
            updateBtn.setTypeface(face); SaveBtn.setTypeface(face); Delete.setTypeface(face); NFname.setTypeface(face);
            NLname.setTypeface(face); NPhone.setTypeface(face); NBirthdate.setTypeface(face); BtnActive.setTypeface(face);
            Password.setTypeface(face); Cpassword.setTypeface(face);

        }
        if (Get.readFromFile2(ProfileActivity.this).isEmpty() == false) {


        }
    }
}
