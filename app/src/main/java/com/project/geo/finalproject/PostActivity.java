package com.project.geo.finalproject;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.Toast;
import android.Manifest;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.geo.finalproject.Model.PostClass;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class PostActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference Posts, Long_Lati;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath, DownloudUri;
    private Boolean FoundImage = false;
    private final int PICK_IMAGE_REQUEST = 71;
    private int startAge, endAge;
    private String username, date, content, Fname, Lname, targetPeople, productType, price,ProfileImage;
    private Double aLong2, lati2;
    private boolean flag1, flag2, flag3;
    private EditText Con, Price, StartAge, EndAge;
    private CheckBox Female, Children, Male;
    private Button PostBtn;
    private Switch SW;
    private GpsTracker gpsTracker;
    private ImageButton mimageButton, OpenGalary, OpenCamera;
    private PostClass[] Post;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_post);

        try {

            Posts = database.getReference("Posts");
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            username = getIntent().getStringExtra("Username");
            Fname = getIntent().getStringExtra("Fname");
            Lname = getIntent().getStringExtra("Lname");
            ProfileImage= getIntent().getStringExtra("ProfileImage");
            SW = findViewById(R.id.switch1);
            PostBtn = findViewById(R.id.button3);
            Post = new PostClass[1];
            Con = findViewById(R.id.Content);
            Price = findViewById(R.id.editText11);
            StartAge = findViewById(R.id.editText4);
            EndAge = findViewById(R.id.editText2);
            final Spinner ProductType = findViewById(R.id.spinner);
            Children = findViewById(R.id.checkBox);
            Female = findViewById(R.id.checkBox2);
            Male = findViewById(R.id.checkBox3);
            final TableLayout Tb1 = findViewById(R.id.Tb1);
            final TableLayout Tb2 = findViewById(R.id.Tb2);
            mimageButton = (ImageButton) findViewById(R.id.imageButton4);
            OpenGalary = (ImageButton) findViewById(R.id.imageButton6);
            OpenCamera = (ImageButton) findViewById(R.id.imageButton5);

            Fun();
            Date currentTime = Calendar.getInstance().getTime();

            date = currentTime.toString();


            SW.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SW.isChecked()) {
                        Tb1.setVisibility(View.VISIBLE);
                        Tb2.setVisibility(View.VISIBLE);

                    } else {
                        Tb1.setVisibility(View.GONE);
                        Tb2.setVisibility(View.GONE);
                    }
                }
            });

            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(PostActivity.this,
                    android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ProductType.setAdapter(myAdapter);

            ProductType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 1) {
                        productType = "Foodstuffs";
                    } else if (i == 2) {
                        productType = "Electronics";
                    } else if (i == 3) {
                        productType = "Clothes";
                    } else if (i == 4) {
                        productType = "Other";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            OpenGalary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chooseImage();
                    mimageButton.setVisibility(View.VISIBLE);

                }
            });
            PostBtn.setEnabled(false);
            OpenCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenCamer();
                }
            });

            gpsTracker = new GpsTracker(PostActivity.this);

            if (gpsTracker.canGetLocation()) {
                lati2 = gpsTracker.getLatitude();
                aLong2 = gpsTracker.getLongitude();

            } else {
                gpsTracker.showSettingsAlert();
            }
            if (lati2>0.0&&aLong2>0.0){
                PostBtn.setEnabled(true);
            }
                PostBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (FoundImage == true) {
                            uploadImage();
                        }
                        String Str_long2 = aLong2.toString();
                        Str_long2 = Str_long2.replace('.', ' ');

                        String Str_lati2 = lati2.toString();
                        Str_lati2 = Str_lati2.replace('.', ' ');

                        Long_Lati = Posts.child(Str_long2 + "_" + Str_lati2);

                        if (Con.getText().toString().isEmpty() == false) {

                            Long_Lati.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    try {
                                        if (SW.isChecked() == false) {
                                            content = Con.getText().toString();

                                            if (FoundImage == false) {

                                                Post[0] = new PostClass(username, date, content, Fname, Lname, aLong2, lati2, "post", ProfileImage);
                                                Long_Lati.push().setValue(Post[0]);
                                                Toast.makeText(PostActivity.this, "Post Successfully  ", Toast.LENGTH_SHORT).show();
                                                onBackPressed();


                                            }

                                        } else if (SW.isChecked()) {

                                            targetPeople = "";
                                            if (Male.isChecked() && flag1 == false) {
                                                targetPeople = targetPeople + "Male ";
                                                flag1 = true;
                                            }
                                            if (Female.isChecked() && flag2 == false) {
                                                targetPeople = targetPeople + "Female ";
                                                flag2 = true;
                                            }
                                            if (Children.isChecked() && flag3 == false) {
                                                targetPeople = targetPeople + "Children ";
                                                flag3 = true;
                                            }

                                            if (StartAge.getText().toString().isEmpty() == false && EndAge.getText().toString().isEmpty() == false &&
                                                    Price.getText().toString().isEmpty() == false && productType.isEmpty() == false || Male.isChecked() || Female.isChecked() || Children.isChecked()) {
                                                content = Con.getText().toString();

                                                startAge = Integer.parseInt(StartAge.getText().toString());
                                                endAge = Integer.parseInt(EndAge.getText().toString());
                                                if (startAge > endAge) {
                                                    Toast.makeText(PostActivity.this, "Choose correct Range of age ", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    price = Price.getText().toString();

                                                    //Adver
                                                    if (FoundImage == false) {
                                                        Post[0] = new PostClass(username, date, content, Fname,Lname, aLong2, lati2, StartAge.getText().toString(), EndAge.getText().toString(),
                                                                targetPeople, productType, price, "adv", ProfileImage);
                                                    }

                                                    Long_Lati.push().setValue(Post[0]);
                                                    Toast.makeText(PostActivity.this, "Post Successfully  ", Toast.LENGTH_SHORT).show();
                                                    onBackPressed();

                                                }
                                            }
                                        }

                                    } catch (Exception EX) {
                                        Toast.makeText(PostActivity.this, "Try to post again", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        } else {
                            Toast.makeText(PostActivity.this, " Enter your Post ", Toast.LENGTH_SHORT).show();

                        }

                    }
                });


        } catch (Exception ex) {
        }
    }


    public void Fun() {
        GetPath Get = new GetPath();
        if (Get.readFromFile(PostActivity.this).isEmpty() == false) {
            Typeface face = Typeface.createFromAsset(getAssets(), Get.readFromFile(PostActivity.this));
            SW.setTypeface(face);
            PostBtn.setTypeface(face);
            Con.setTypeface(face);
            Price.setTypeface(face);
            StartAge.setTypeface(face);
            EndAge.setTypeface(face);
            Children.setTypeface(face);
            Female.setTypeface(face);
            Male.setTypeface(face);
        }
        if (Get.readFromFile2(PostActivity.this).isEmpty() == false) {
            Float size = Float.parseFloat(Get.readFromFile2(PostActivity.this));
            SW.setTextScaleX(size);
            PostBtn.setTextScaleX(size);
            Con.setTextScaleX(size);
            Price.setTextScaleX(size);
            StartAge.setTextScaleX(size);
            EndAge.setTextScaleX(size);
            Children.setTextScaleX(size);
            Female.setTextScaleX(size);
            Male.setTextScaleX(size);
        }
    }

    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/PostImages/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            DownloudUri = taskSnapshot.getDownloadUrl();
                            if (SW.isChecked() == false) {
                                Post[0] = new PostClass(username, date, content, Fname, Lname, aLong2, lati2, "post", DownloudUri.toString(),ProfileImage);
                                Long_Lati.push().setValue(Post[0]);
                            }
                            if (SW.isChecked() == true){
                                startAge = Integer.parseInt(StartAge.getText().toString());
                                endAge = Integer.parseInt(EndAge.getText().toString());
                                if (startAge < endAge) {
                                    Post[0] = new PostClass(username, date, content, Fname,Lname, aLong2, lati2, StartAge.getText().toString(), EndAge.getText().toString(),
                                            targetPeople, productType, price, DownloudUri.toString(), "adv",ProfileImage);
                                    Long_Lati.push().setValue(Post[0]);
                                }
                            }
                            onBackPressed();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(PostActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });

        }
    }

    private void chooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        FoundImage = true;


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            mimageButton.setImageURI(filePath);
            mimageButton.setVisibility(View.VISIBLE);

        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            filePath = data.getData();
            mimageButton.setImageURI(filePath);
            mimageButton.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    public  void OpenCamer()
    {
        try {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }catch (Exception ex)
        {
            Toast.makeText(gpsTracker, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
