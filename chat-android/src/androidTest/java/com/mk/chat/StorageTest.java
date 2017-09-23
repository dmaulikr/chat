package com.mk.chat;

import android.net.Uri;
import android.os.Environment;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class StorageTest {

    @Test
    public void testUpload() throws Exception {

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        File filePath = new File(String.valueOf(Environment.getExternalStorageDirectory()) + "/" + "Download/" + "IT.pdf");
        assertEquals(filePath.canRead(), true);
        Uri file = Uri.fromFile(filePath);
        StorageReference riversRef = storageRef.child("images/IT.pdf");

        riversRef.putFile(file)
                .addOnSuccessListener(taskSnapshot -> {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Log.d("OnUploadSuccess", downloadUrl.toString());
                })
                .addOnFailureListener(exception -> Log.d("OnUploadFailure", exception.toString()));
    }
}
