package com.example.seefood.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.seefood.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.File;
import java.io.IOException;


public class CameraFragment extends Fragment {

    private static final String TAG = "TestActivity";

    //inputImage for ML Kit
    InputImage image;

    //textview for result
    TextView tvResultText;

    File photoFile;
    private String photoFileName = "photo.jpg";

    TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){

            if(resultCode == RESULT_OK){

                Log.i(TAG, "It went here");
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                image = InputImage.fromBitmap(takenImage, 0);

                //process the image
                Task<Text> result =
                        recognizer.process(image)
                                .addOnSuccessListener(new OnSuccessListener<Text>() {
                                    @Override
                                    public void onSuccess(Text text) {

                                        Log.i(TAG, "Firebase ML successfully detected text!");

                                        storeText(text);

                                        for(Text.TextBlock block: text.getTextBlocks()){
                                            Log.i(TAG, block.getText().toString());
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Log.e(TAG, "Unsuccessful");
                                    }});

            }

        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Retrieve Button
        Button imageBtn = view.findViewById(R.id.imageBtn),
                camera = view.findViewById(R.id.camera);

        //Retrieve the textView for result
        tvResultText = view.findViewById(R.id.tvResultText);

        //create a sample image here
        ImageView ivSampleImage = view.findViewById(R.id.ivSampleImage);

        Glide.with(getContext())
                .asBitmap()
                .load("https://images.squarespace-cdn.com/content/v1/5cf4bd06f614ef00014ea1df/1617134060935-EP65IP6VQ9I6I8J702UN/codepath-1x1_solid-dark.png?format=500w")
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ivSampleImage.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        ActivityResultLauncher<String> cameraLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        // Handle the returned Uri
                    }
                });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                photoFile = getPhotoFileUri(photoFileName);

                Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);


                // start the image capture Intent
                startActivityForResult(intent,
                        1);

            }
        });



        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //grab the bitmap and pass to the InputImage
                Bitmap bitmap = ((BitmapDrawable) ivSampleImage.getDrawable()).getBitmap();

                image = InputImage.fromBitmap(bitmap, 0);

            }
        });
    }

    private void storeText(Text text) {
        Log.i(TAG, "It went here too");
        //Create a StringBuilder to store the text
        StringBuilder stringBuilder = new StringBuilder();

        //append each block in the StringBuilder
        for(Text.TextBlock block: text.getTextBlocks()){
            stringBuilder.append(block.getText().toString()).append("\n");
        }

        //set the text to the textview
        tvResultText.setText(stringBuilder.toString());

    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }
}