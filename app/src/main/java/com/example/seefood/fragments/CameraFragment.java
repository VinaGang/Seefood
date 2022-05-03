package com.example.seefood.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
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

import com.example.seefood.R;
import com.example.seefood.models.SeeFoodMenu;
import com.example.seefood.statics.CreateMenuActivity;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import org.parceler.Parcels;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


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

        imageBtn.setOnClickListener(v -> {

            //grab the bitmap and pass to the InputImage
            Bitmap bitmap = ((BitmapDrawable) ivImage.getDrawable()).getBitmap();

            image = InputImage.fromBitmap(bitmap, 0);

            //process the image
            Task<Text> result =
                    recognizer.process(image)
                            .addOnSuccessListener(text -> {
                                //Intent to create Menu Activity
                                SeeFoodMenu menu = new SeeFoodMenu(text);
                                Intent intent = new Intent(getContext(), CreateMenuActivity.class);
                                intent.putExtra("menu", Parcels.wrap(menu));
                                startActivity(intent);
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

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Log.i(TAG, "request code is: " + requestCode);
        Log.i(TAG, "result code is: " + resultCode);
        Log.d(TAG, "RESULT_OK = " + RESULT_OK);
        if(requestCode == 1 && resultCode == RESULT_OK){
            Log.i(TAG, "It went here");
            Bitmap imageBitmap = rotateBitmapOrientation(photoFile.getAbsolutePath());
            Glide.with(getContext()).load(imageBitmap).into(ivImage);

        }
    }
    public Bitmap rotateBitmapOrientation(String photoFilePath) {
        // Create and configure BitmapFactory
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFilePath, bounds);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);
        // Read EXIF Data
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(photoFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
        // Rotate Bitmap
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        // Return result
        return rotatedBitmap;
    }
}