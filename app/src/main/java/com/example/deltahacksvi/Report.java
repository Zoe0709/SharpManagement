package com.example.deltahacksvi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
//import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
//import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

//import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import com.google.firebase.FirebaseApp.FirebaseDatabase

public class Report extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    // Obtain the FirebaseAnalytics instance.

    private Button choose_pic, upload_pic, camera;
    private ImageView imageView;
    private Uri filePath;
    private final int PICK_NAME_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference mstorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        storage = FirebaseStorage.getInstance();
        mstorage = storage.getReference();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        //FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //FirebaseUser user = mAuth.getCurrentUser();

        choose_pic = (Button) findViewById(R.id.choose_pic);
        upload_pic = (Button) findViewById(R.id.upload_pic);
        imageView = (ImageView) findViewById(R.id.imgView);

        choose_pic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                chooseImage();
            }
        });

        upload_pic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                uploadImage();

            }
        });

        camera = (Button) findViewById(R.id.camera);


        camera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

    }



    private void uploadImage() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            StorageReference fp = mstorage.child("Photos").child(filePath.getLastPathSegment());
            fp.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(Report.this, "Nice! Upload done!", Toast.LENGTH_SHORT).show();
                }
            });
            fp.putFile(filePath).addOnFailureListener(new OnFailureListener() {
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Report.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_NAME_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_NAME_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





}
