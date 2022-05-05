package com.example.seefood.APIs;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PrivateKey;

public class GoogleImageSearchAPI {
    private static final String GOOGLE_SEARCH_API_KEY = "AIzaSyBMaLpJUJuHzLpwu-1-oUXj2jhIiCg-f0M";
    private static final String HEAD_SEARCH_REQUEST_URL = "https://customsearch.googleapis.com/customsearch/v1?imgSize=MEDIUM&searchType=image&key=" + GOOGLE_SEARCH_API_KEY + "&q=";
    private static final String TAIL_SEARCH_REQUEST_URL = "HTTP/1.1";
    private static final String TAG = "GoogleImageSearchAPI";

    private String myUrl;
    private String picURL;

    public GoogleImageSearchAPI(String searchKey){
        myUrl = HEAD_SEARCH_REQUEST_URL + searchKey + TAIL_SEARCH_REQUEST_URL;
    }
    public String getPicURL(){
        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
        picURL = myAsyncTasks.doInBackground();
        return  picURL;
    }
    public class MyAsyncTasks extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            Log.d(TAG, "On Process");
            // display a progress dialog to show the user what is happening

        }

        @Override
        protected String doInBackground(String... strings) {
            // Fetch data from the API in the background.

            String result = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(myUrl);
                    //open a URL coonnection

                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStreamReader isw = new InputStreamReader(in);

                    int data = isw.read();

                    while (data != -1) {
                        result += (char) data;
                        data = isw.read();

                    }

                    // return the data to onPostExecute method
                    return result;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            picURL = result;
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            // show results
        }
    }

}
