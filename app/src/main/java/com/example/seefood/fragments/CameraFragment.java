package com.example.seefood.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.text.SimpleDateFormat;
import java.util.Date;


public class CameraFragment extends Fragment {

    private static final String TAG = "CameraFragment";

    //inputImage for ML Kit
    InputImage image;

    //textview for result
    TextView tvResultText;

    //image
    ImageView ivImage;

    File photoFile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        //Retrieve Toolbar
        Toolbar toolbar = view.findViewById(R.id.tbCamera);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        //Retrieve Button
        Button imageBtn = view.findViewById(R.id.imageBtn);

        //Retrieve the textView for result
        tvResultText = view.findViewById(R.id.tvResultText);

        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        //create a sample image here
        ivImage = view.findViewById(R.id.ivSampleImage);

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

        imageBtn.setOnClickListener(v -> {

            //grab the bitmap and pass to the InputImage
            Bitmap bitmap = ((BitmapDrawable) ivImage.getDrawable()).getBitmap();

            image = InputImage.fromBitmap(bitmap, 0);

            //process the image
            Task<Text> result =
                    recognizer.process(image)
                            .addOnSuccessListener(text -> {

                                Log.i(TAG, "Firebase ML successfully processed image.");
                                storeText(text);

                            })
                            .addOnFailureListener(e -> Log.e(TAG, "Unsuccessful"));
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.camera_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        takePicture();

        return true;
    }

    private void storeText(Text text) {

        //Create a StringBuilder to store the text
        StringBuilder stringBuilder = new StringBuilder();

        //append each block in the StringBuilder
        for(Text.TextBlock block: text.getTextBlocks()){
            stringBuilder.append(block.getText()).append("\n");
        }

        Log.i(TAG, "the text is: " + stringBuilder);

        if(stringBuilder.length() == 0){
            tvResultText.setText("The image does not have text");
        }
        else {
            //set the text to the textview
            tvResultText.setText(stringBuilder.toString());
        }
    }

    public void takePicture(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri("photo.jpg");

        Uri fileProvider = FileProvider.getUriForFile(getActivity(), "com.example.android.seefood", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        if(intent.resolveActivity(getActivity().getPackageManager()) != null){

            Log.i(TAG, "It went here at start");
            startActivityForResult(intent, 1);
        }

    }

    private File getPhotoFileUri(String fileName) {

        File mediaStorageDir = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "SeeFood");
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Log.i(TAG, "request code is: " + requestCode);
        Log.i(TAG, "result code is: " + resultCode);

        if(requestCode == 1 && resultCode == RESULT_OK){

            Log.i(TAG, "It went here");
            Bitmap imageBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            Glide.with(getContext()).load(imageBitmap).into(ivImage);

        }
    }

}