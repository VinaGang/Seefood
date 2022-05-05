package com.example.seefood.fragments;


import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;
import com.canhub.cropper.CropImageView;

import com.example.seefood.R;
import com.example.seefood.models.SeeFoodMenu_Copy;
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
    private ActivityResultLauncher<CropImageContractOptions> cropImage;
    private ActivityResultLauncher<Intent> saveMenu;
    //inputImage for ML Kit
    InputImage image;

    //textview for result
    TextView selectImageText;

    //image
    ImageView ivImage, lookArrowCamera, cameraIcon;

    Button imageBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        saveMenu = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK){
                        Log.i(TAG, "User saved the menu");

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.flContainer, new SuccessFragment())
                                .commit();
                    }
                });

        cropImage = registerForActivityResult(new CropImageContract(), result -> {
            if(result.isSuccessful()){

                imageBtn.setVisibility(View.VISIBLE);
                selectImageText.setText("Selected picture is:");
                lookArrowCamera.setVisibility(View.GONE);
                cameraIcon.setVisibility(View.GONE);

                Glide.with(getContext()).load(result.getUriContent()).into(ivImage);
            }
        });

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
        imageBtn = view.findViewById(R.id.imageBtn);

        //Retrieve the textView for result
        selectImageText = view.findViewById(R.id.selectImageText);

        lookArrowCamera = view.findViewById(R.id.lookArrowCamera);
        Glide.with(getActivity()).load(R.drawable.arrow).into(lookArrowCamera);

        cameraIcon = view.findViewById(R.id.cameraIcon);
        Glide.with(getActivity()).load(R.drawable.camera_gif).into(cameraIcon);

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
                                SeeFoodMenu_Copy menu = new SeeFoodMenu_Copy(text); //Already have list of Pictures
                                Log.d(TAG, menu.getMenuItemsList().toString());
                                Intent intent = new Intent(getContext(), CreateMenuActivity.class);
                                intent.putExtra("menu", Parcels.wrap(menu));

                                saveMenu.launch(intent);
                                //startActivity(intent);
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

        CropImageContractOptions options = new CropImageContractOptions(null, new CropImageOptions())
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setOutputCompressQuality(100)
                .setCropMenuCropButtonIcon(R.drawable.ic_baseline_check_24)
                .setGuidelines(CropImageView.Guidelines.ON);

        cropImage.launch(options);

        return true;
    }

}