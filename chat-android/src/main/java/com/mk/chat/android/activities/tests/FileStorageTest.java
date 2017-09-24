package com.mk.chat.android.activities.tests;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mk.chat.R;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class FileStorageTest extends AppCompatActivity {

    Button testUploadButton;
    Button uploadButton2;
    Button downloadButton;
    TextView downloadedFilePathTV;

    Set<Consumer<Uri>> userChoosedAFileSubscribers = new HashSet<>();

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private static final int PICK_FILE_REQUEST = 234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        testUploadButton = (Button) findViewById(R.id.UploadButton);
        uploadButton2 = (Button) findViewById(R.id.UploadButton2);
        downloadButton = (Button) findViewById(R.id.DownloadButton);
        downloadedFilePathTV = (TextView) findViewById(R.id.downloadedFilePathTV);

        testUploadButton.setOnClickListener((button) -> userWantsToUploadAFile());
        uploadButton2.setOnClickListener((button) -> userWantsToUploadAFile2());
        downloadButton.setOnClickListener((button) -> testDownload());
    }

    protected void testDownload() {
        downloadAFile("0");
    }

    protected void userWantsToUploadAFile() {
        showFileChooser();
        userChoosedAFileSubscribers.add(this::uploadFile);
    }

    protected void userWantsToUploadAFile2() {
        showFileChooser();
        userChoosedAFileSubscribers.add((file) -> uploadFile2(uriToFile(file)));
    }

    public void downloadAFile(String name) {

        try {
            final File localFile = File.createTempFile("file", "");
            storageRef.child("images").child(name).getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        Toast.makeText(this, taskSnapshot.toString(), Toast.LENGTH_LONG).show();
                        downloadedFilePathTV.setText(localFile.getAbsolutePath() + "\n" + Uri.fromFile(localFile).getPath());
                    })
                    .addOnFailureListener(exception -> {
                        Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show();
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected File uriToFile(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return new File(cursor.getString(column_index));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();

        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri fileAsUriFromIntent = data.getData();
            File fileAsFileConvertedFromUri = new File(fileAsUriFromIntent.getPath());
            boolean fileConvertedFromCanRead = fileAsFileConvertedFromUri.canRead();
            boolean fileConvertedFromCanWrite = fileAsFileConvertedFromUri.canWrite();
            Log.d("onActivityResult", String.valueOf(fileConvertedFromCanRead) + String.valueOf(fileConvertedFromCanWrite));
            for (Consumer<Uri> subscriber : userChoosedAFileSubscribers) {
                subscriber.accept(data.getData());
            }
        }
    }


    protected String getFileName(Uri file) {
        return new File(file.getPath()).getName();
    }

    protected void uploadFile(Uri file) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();
        String fileName = getFileName(file);
        StorageReference riversRef = storageRef.child("images/" + fileName);
        riversRef.putFile(file)
                .addOnSuccessListener(taskSnapshot -> {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(exception -> {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("UploadFailure", exception.getMessage());
                    Log.e("UploadFailure", exception.toString());
                })
                .addOnProgressListener(taskSnapshot -> {
                    //double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    //progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                });
    }

    protected void uploadFile2(File file) {
        uploadFile(Uri.fromFile(file));
    }
}