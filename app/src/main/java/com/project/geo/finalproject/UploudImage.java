package com.project.geo.finalproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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
import com.project.geo.finalproject.Model.user;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class UploudImage extends Activity {
    private ImageButton Image_Btn;
    private Button BtnSub;
    FirebaseStorage storage;
    StorageReference storageReference;

    private String Email,UriImage;
    private FirebaseDatabase database;
    private DatabaseReference users;
    private user NewUser;
    private CircleImageView test1, test2;
    private Uri filePath, Downloud;
    private final int PICK_IMAGE_REQUEST = 71;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploud_image);
        GetPath Get = new GetPath();
        if (Get.readFromFile(UploudImage.this).isEmpty() == false) {
            Typeface face = Typeface.createFromAsset(getAssets(), Get.readFromFile(UploudImage.this));
            BtnSub.setTypeface(face);
        }

        database = FirebaseDatabase.getInstance();
        users = database.getReference("users");
        Email = getIntent().getStringExtra("Email");
        Email = Email.replace('.', ' ');
        Image_Btn = findViewById(R.id.imageButton);
        BtnSub = findViewById(R.id.button5);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        test1 = (CircleImageView) findViewById(R.id.test2);
        test2 = (CircleImageView) findViewById(R.id.test3);

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(Email).exists()) {
                    NewUser = dataSnapshot.child(Email).getValue(user.class);
                    UriImage =NewUser.getImage_url();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Image_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
                BtnSub.setVisibility(View.VISIBLE);
                test1.setVisibility(View.VISIBLE);
                test2.setVisibility(View.VISIBLE);


            }
        });
        BtnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count==0) {
                    uploadImage();
                    count++;
                    }
                else
                {
                    onBackPressed();
                }
            }

        });
    }

    private void uploadImage() {

        if (filePath != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("ProfileImage/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Downloud = taskSnapshot.getDownloadUrl();
                            NewUser.setImage_url(Downloud.toString());
                            users.child(Email).setValue(NewUser);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UploudImage.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
  try {
      Intent intent = new Intent();
      intent.setType("image/*");
      intent.setAction(Intent.ACTION_GET_CONTENT);
      startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
  }catch (Exception e)
  {
      Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
  }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            Image_Btn.setImageURI(filePath);
            test1.setImageURI(filePath);
            test2.setImageURI(filePath);
        }
    }
}
