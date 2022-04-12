package com.example.seefood.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.LongDef;
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
import com.example.seefood.GoogleSearch;
import com.example.seefood.R;
import com.example.seefood.SerpApiSearchException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


public class CameraFragment extends Fragment {

    private static final String TAG = "TestActivity";
    static final int PICK_PHOTO_CODE  = 2;

    //Ikemen Kuma
    Button btUploadImage;
    Bitmap selectedImage;
    //Ikemen Kuma Cha nai
    //inputImage for ML Kit
    InputImage image;
    ImageView ivSampleImage;
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

        if ((data != null) && requestCode == PICK_PHOTO_CODE) {
            Uri photoUri = data.getData();
            // Load the image located at photoUri into selectedImage
            selectedImage = loadFromUri(photoUri);
            //load to image
            image = InputImage.fromBitmap(selectedImage, 0);
            Glide.with(getContext()).load(selectedImage).disallowHardwareConfig().into(ivSampleImage);
            Task<Text> result =
                    recognizer.process(image)
                            .addOnSuccessListener(new OnSuccessListener<Text>() {
                                @Override
                                public void onSuccess(Text text) {

                                    Log.i(TAG, "Firebase ML successfully detected text!");

//                                    storeText(text);

                                    for(Text.TextBlock block: text.getTextBlocks()){
                                        Log.i(TAG, block.getText().toString());
                                        String searchKey = "Pho Dac Biet";
//                                        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
//                                        intent.putExtra(SearchManager.QUERY, searchKey);
//                                        startActivity(intent);
                                        search(searchKey);
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Log.e(TAG, "Unsuccessful");
                                }});

//            Glide.with(getActivity()).load(photoUri).into(ivSampleImage);
        }

        if(requestCode == 1){

            if(resultCode == RESULT_OK){

                Log.i(TAG, "It went here");
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                image = InputImage.fromBitmap(takenImage, 0);
                //IKEMEN
                Glide.with(getContext()).load(takenImage).disallowHardwareConfig().into(ivSampleImage);
                //IKEMEN CHA NAI
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

    private void search(String searchKey) {
        String URL = "https://serpapi.com/search.json?google_domain=google.com";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("q", searchKey);
        params.put("tbm", "isch");
        params.put("ijn", "1");
        params.put("api_key", "5869f12410008dd7dfe38ba1f1d683579d4303e0b21649270f3257f53532a0d8");
        Log.d(TAG, "SerAPI: " + params);
        client.get(URL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                try {
                  JSONArray images = responseBody.getJSONArray("images_results");
//                    Log.d(TAG, "SerAPI: " + images);
                    for(int i = 0; i <3; i++){
                        Log.d(TAG, "SerAPI: " + images.getJSONObject(i).getString("original"));

                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers,  Throwable error, JSONObject responseBody) {
                Log.d(TAG, "SerAPI: " + responseBody);
                Log.d(TAG, "SerAPI: " + String.valueOf(statusCode) + " " + error);
            }
        });
//        // parameters
//        Map<String, String> parameter = new HashMap<>();
//        parameter.put("q", searchKey);
//        parameter.put("api_key", GoogleSearch.SERP_API_KEY_NAME);
////        parameter.put("tbm", "isch");
////        parameter.put("ijn", 1);
////        parameter.put("chips", searchKey);
//        // Create search
//        GoogleSearch search = new GoogleSearch(parameter);
//
//        try {
//            // Execute search
//            JsonObject data = search.getJson();
//            Log.d(TAG, "SerAPI: " + data);
//        } catch (SerpApiSearchException e) {
//            System.out.println("oops exception detected!");
//            e.printStackTrace();
//        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Ikemen Kuma
        ivSampleImage = view.findViewById(R.id.ivSampleImage);
        btUploadImage = view.findViewById(R.id.btUploadImage);
        btUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickPhoto(view);
            }
        });

        //Ikemen Kuma Cha Nai
        //Retrieve Button
        Button imageBtn = view.findViewById(R.id.imageBtn),
                camera = view.findViewById(R.id.camera);

        //Retrieve the textView for result
        tvResultText = view.findViewById(R.id.tvResultText);


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

                Log.d(TAG, "imageBtn Onclick");
                //grab the bitmap and pass to the InputImage
                Bitmap bitmap = ((BitmapDrawable) ivSampleImage.getDrawable()).getBitmap();
                image = InputImage.fromBitmap(bitmap, 0);
                //IKEMEN
//                image = InputImage.fromBitmap(selectedImage, 0);
                //IKEMEN CHA NAI
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

    //IKEMEN
    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            // check version of Android on device
            if(Build.VERSION.SDK_INT > 27){
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(getContext().getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    // Trigger gallery selection for a photo
    public void onPickPhoto(View view) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // Bring up gallery to select a photo
        startActivityForResult(intent, PICK_PHOTO_CODE);
    }

    //END IKEMEN
}