package com.example.seefood.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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


public class CameraFragment extends Fragment {

    private static final String TAG = "TestActivity";

    //inputImage for ML Kit
    InputImage image;

    //textview for result
    TextView tvResultText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Retrieve Button
        Button imageBtn = view.findViewById(R.id.imageBtn);

        //Retrieve the textView for result
        tvResultText = view.findViewById(R.id.tvResultText);

        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        //create a sample image here
        ImageView ivSampleImage = view.findViewById(R.id.ivSampleImage);

/*        Glide.with(getContext())
                .asBitmap()
                .load("http://www.phocalillc.com/wp-content/uploads/2021/09/pho-cali-new-menu-11-2-2.jpg")
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ivSampleImage.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });*/

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //grab the bitmap and pass to the InputImage
                Bitmap bitmap = ((BitmapDrawable) ivSampleImage.getDrawable()).getBitmap();

                image = InputImage.fromBitmap(bitmap, 0);

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
        });
    }

    private void storeText(Text text) {

        //Create a StringBuilder to store the text
        StringBuilder stringBuilder = new StringBuilder();

        //append each block in the StringBuilder
        for(Text.TextBlock block: text.getTextBlocks()){
            stringBuilder.append(block.getText().toString()).append("\n");
        }

        //set the text to the textview
        tvResultText.setText(stringBuilder.toString());

    }
}