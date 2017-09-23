package com.mk.chat.android.activities.conversation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mk.chat.R;

import java.io.File;
import java.io.IOException;

public class Conversation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        showFileChooser();
    }


    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private void testUpload() {

        File filePath = new File(String.valueOf(Environment.getExternalStorageDirectory()) + "/" + "Download/" + "IT.pdf");
        Uri file = Uri.fromFile(filePath);
        StorageReference riversRef = storageRef.child("images/IT.pdf");

        riversRef.putFile(file)
                .addOnSuccessListener(taskSnapshot -> {
                    Log.d("on upload success", taskSnapshot.toString());
                })
                .addOnFailureListener(exception -> Log.d("on upload failure", exception.toString()));
    }


    private Uri filePath;
    private static final int PICK_IMAGE_REQUEST = 234;

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

            } catch (IOException e) {
                e.printStackTrace();
            }
            uploadFile();
        }
    }

    private void uploadFile() {

        if (filePath != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference riversRef = storageRef.child("images/pic.jpg");
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
}
