package com.mk.chat.android.activities.conversation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.mk.chat.R;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    //a constant to track the file chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;

    private Button buttonChoose;
    private Button buttonUpload;

    private ImageView imageView;

    private Uri filePath;

    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase_storage_test);

        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        imageView = (ImageView) findViewById(R.id.imageView);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);

        storageReference = FirebaseStorage.getInstance().getReference();
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadFile() {

        if (filePath != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference riversRef = storageReference.child("images/pic.jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                    })
                    .addOnFailureListener(exception -> {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        //double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        //progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                    });
        }
    }

    @Override
    public void onClick(View view) {
        if (view == buttonChoose) {
            showFileChooser();
        }
        else if (view == buttonUpload) {
            uploadFile();
        }
    }
}
