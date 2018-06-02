package com.project.geo.finalproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.project.geo.finalproject.Model.user;


public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference users;
    EditText Username, Password;
    ProgressDialog progress;
    ImageButton imageButton;
    TextView regestration, Loginbtn;
    boolean visible = false;

    public void Reset() {
        Username.setText("");
        Password.setText("");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        database = FirebaseDatabase.getInstance();
        users = database.getReference("users");

        regestration = findViewById(R.id.regist);
        Loginbtn = findViewById(R.id.Loginbtn);
        imageButton = (ImageButton) findViewById(R.id.imageButton2);

        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);

        FontFun();
        // navigate from login to registration
        regestration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        Registration.class);
                startActivity(intent);
            }

        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visible == false) {
                    imageButton.setBackgroundResource(R.drawable.ic_visibility_off_white_24dp);
                    Password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    visible = true;
                } else if (visible == true) {
                    imageButton.setBackgroundResource(R.drawable.ic_visibility_white_24dp);
                    Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    visible = false;
                }
            }
        });

        final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // login action
        Loginbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    progress = new ProgressDialog(MainActivity.this);
                    progress.setMessage("Cheek Permission ....");
                    progress.show();
                    Sigin(Username.getText().toString(), Password.getText().toString());
                } else {
                    Toast.makeText(MainActivity.this, "Cheek internet connection ", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void Sigin(final String username, final String password) {

        String user2 = username;

        user2 = user2.replace('.', ' ');

        final String finalUser = user2;

        users.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {

                    if (Username.getText().toString().isEmpty() == false) {


                        if (dataSnapshot.child(finalUser).exists()) {

                            if (!finalUser.isEmpty()) {
                                user login = dataSnapshot.child(finalUser).getValue(user.class);
                                if (login.getPassword().equals(password)) {
                                    Reset();

                                    Intent intent = new Intent(MainActivity.this, Home.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    String Email = login.getEmail();
                                    Email = Email.replace(' ', '.');
                                    intent.putExtra("Username", Email);
                                    intent.putExtra("Fname", login.getFirst_name());
                                    intent.putExtra("Lname", login.getLast_name());
                                    intent.putExtra("Gender", login.getGender());
                                    intent.putExtra("ProfileImage", login.getImage_url());
                                    intent.putExtra("Birthdte", login.getDate());
                                    startActivity(intent);
                                    progress.dismiss();

                                } else {

                                    Toast.makeText(MainActivity.this, " Invalid Password ", Toast.LENGTH_SHORT).show();
                                    progress.dismiss();

                                }
                            } else {


                                Toast.makeText(MainActivity.this, " Enter Username ", Toast.LENGTH_SHORT).show();
                                progress.dismiss();

                            }
                        } else {


                            Toast.makeText(MainActivity.this, " Username is not register ", Toast.LENGTH_SHORT).show();
                            progress.dismiss();


                        }

                    } else {

                        Toast.makeText(MainActivity.this, " Enter Username and Password ", Toast.LENGTH_SHORT).show();
                        progress.dismiss();

                    }

                } catch (Exception ex) {
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void FontFun() {
        GetPath Get = new GetPath();
        if (Get.readFromFile(MainActivity.this).isEmpty() == false) {

            Typeface face = Typeface.createFromAsset(getAssets(), Get.readFromFile(MainActivity.this));
            Username.setTypeface(face);
            Password.setTypeface(face);
            regestration.setTypeface(face);
            Loginbtn.setTypeface(face);

        }
    }
}
