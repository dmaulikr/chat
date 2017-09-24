package database;

import android.app.ProgressDialog;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by mladen on 9/23/17.
 */

public class FilesStore {

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private void uploadFile(Uri file) {

        StorageReference riversRef = storageRef.child("images/");
        riversRef.putFile(file)
                .addOnSuccessListener(taskSnapshot -> {

                })
                .addOnFailureListener(exception -> {

                })
                .addOnProgressListener(taskSnapshot -> {

                });
    }

}
