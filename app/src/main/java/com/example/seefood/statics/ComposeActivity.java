package com.example.seefood.statics;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.seefood.R;
import com.example.seefood.classes.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class ComposeActivity extends AppCompatActivity {

    private EditText etDescription;
    private RatingBar rbRating;
    private Post composePost;
    private FirebaseAuth mAuth;
    private float userRating;
    private String userID;
    private String description;
    private Button btnSubmit;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton fbtnCompose;
    private ImageView ivfoodImage;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Food Images");
    private Uri foodPicUri;
    private String imageURL;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("post");

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        etDescription = findViewById(R.id.etDescription);
        rbRating = findViewById(R.id.rbRating);
        btnSubmit = findViewById(R.id.btnSubmit);
        fbtnCompose = findViewById(R.id.fbtnCompose);
        ivfoodImage = findViewById(R.id.ivfoodImage);

        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                userRating = v;
            }
        });

        ActivityResultLauncher<String> imageUri = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                foodPicUri = result;
                ivfoodImage.setImageURI(foodPicUri);
            }
        });

        fbtnCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageUri.launch("image/*");
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadToFirebase(foodPicUri);

            }
        });
    }

    public void updateUI(FirebaseUser currentUser) {
        String keyID = databaseReference.push().getKey();
        databaseReference.child(keyID).setValue(composePost);
        Intent login = new Intent(this, MainActivity.class);
        startActivity(login);
    }

    private void uploadToFirebase(Uri imageUri){
        StorageReference file = storageReference.child(System.currentTimeMillis()+ "."+getFileExtension(imageUri));
        file.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){

                    file.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){

                                imageURL = task.getResult().toString();
                                userID = currentUser.getUid();
                                description = etDescription.getText().toString();
                                composePost = new Post(description, imageURL, userRating, userID);
                                updateUI(currentUser);

                                if(description == null){
                                    Toast.makeText(ComposeActivity.this, "Description cannot be empty!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            else{
                                Toast.makeText(ComposeActivity.this, "Failed to get URL.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    Toast.makeText(ComposeActivity.this, "Image uploaded successfully!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ComposeActivity.this, "Image uploaded failed! Try again.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}