package com.example.seefood;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    //inputImage for ML Kit
    InputImage image;

    //textview for result
    TextView tvResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //Retrieve Button
        Button imageBtn = findViewById(R.id.imageBtn);

        //Retrieve the textView for result
        tvResultText = findViewById(R.id.tvResultText);

        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        //create a sample image here
        ImageView ivSampleImage = findViewById(R.id.ivSampleImage);

        Glide.with(this)
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