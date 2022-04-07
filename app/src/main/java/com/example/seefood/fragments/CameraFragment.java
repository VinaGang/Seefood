package com.example.seefood.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.io.IOException;


public class CameraFragment extends Fragment {

    private static final String TAG = "TestActivity";

    //inputImage for ML Kit
    InputImage image;

    //textview for result
    TextView tvResultText;

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
        if(resultCode == 200){

            Log.i(TAG, "It went here");
            Uri imageUri = data.getData();
            try {
                image = InputImage.fromFilePath(getContext(), imageUri);

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
            } catch (IOException e) {
                e.printStackTrace();
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

                // start the image capture Intent
                startActivityForResult(intent,
                        200);

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

        if(stringBuilder.toString().isEmpty())
            tvResultText.setText("Empty string");

        //set the text to the textview
        tvResultText.setText(stringBuilder.toString());

    }
}