package com.example.seefood.models;

import android.graphics.Point;
import android.os.AsyncTask;
import android.util.Log;

import com.google.mlkit.vision.text.Text;

import org.parceler.Parcel;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Parcel
public class SeeFoodMenu_Copy {
    public static final String TAG = "Menu Class";
    List<List<String>> menu;
    private static final String GOOGLE_SEARCH_API_KEY = "AIzaSyBMaLpJUJuHzLpwu-1-oUXj2jhIiCg-f0M";
    private static final String HEAD_SEARCH_REQUEST_URL = "https://customsearch.googleapis.com/customsearch/v1?imgSize=MEDIUM&searchType=image&key=" + GOOGLE_SEARCH_API_KEY + "&q=";
    private static final String TAIL_SEARCH_REQUEST_URL = "HTTP/1.1";
    public static final int MARGIN_X_ERROR = 120;
    public static final int MARGIN_Y_ERROR = 12;

    private String getFullRequestURL(String searchKey){
        return HEAD_SEARCH_REQUEST_URL + searchKey + TAIL_SEARCH_REQUEST_URL;
    }
    public SeeFoodMenu_Copy(Text result){
        menu = getMenu(result);
    }
    public SeeFoodMenu_Copy(List<List<String>> menu){
      this.menu = menu;
    }

    public SeeFoodMenu_Copy(){
    }

    public List<List<String>> getMenu(){
        return menu;
    }

    private static boolean isNumber(String s){
        int lastC;

        try{
            lastC = Integer.parseInt(s.substring(s.length()-2));
            return true;
        }catch (NumberFormatException  nfe){
            nfe.printStackTrace();
        }catch (StringIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return false;
    }

    private static List<List<String>> getMenu(Text result) {

        Log.d(TAG, "---------------------------------------");
        String resultText = result.getText();

        List<String> names = new ArrayList<>();
        List<String> prices = new ArrayList<>();

        //Find the price position
        List<Integer> priceYs = new ArrayList<>();
        int avgPriceX = 0;
        int totalPriceX = 0;
        for (Text.TextBlock block : result.getTextBlocks()) {
            for (Text.Line line : block.getLines()) {
                Point[] lineCornerPoints = line.getCornerPoints();
                String textLine = line.getText();

//                Log.d(TAG, "LANGUAGE: " + line.getRecognizedLanguage());
                if(isNumber(textLine)){
                    prices.add(textLine);
                    priceYs.add(lineCornerPoints[1].y);
                    totalPriceX += lineCornerPoints[1].x;
                }
            }
        }

        avgPriceX = totalPriceX/prices.size();
        Log.d(TAG, "Average Price X: " + avgPriceX);
        //get all positions sets
        for (Text.TextBlock block : result.getTextBlocks()) {
            for (Text.Line line : block.getLines()) {
                Point[] lineCornerPoints = line.getCornerPoints();
                String textLine = line.getText();
                Log.d(TAG, textLine + ": " +lineCornerPoints[0] + ", " + lineCornerPoints[1] + ", " + lineCornerPoints[2]);
//                Log.d(TAG, "In line Y: " + lineCornerPoints[1].y );
                if(isInlines(lineCornerPoints[1].y, priceYs, MARGIN_Y_ERROR) ){
                    Log.d(TAG, "CHECK X: " + textLine + " ::  " + lineCornerPoints[0].x + "??" + avgPriceX);
                    if(!isPrecise(lineCornerPoints[0].x, avgPriceX, MARGIN_X_ERROR)) {
                        names.add(textLine);
                    }
                }
            }
        }

        for (int i = 0; i < names.size(); i++){
            names.set(i, refineName(names.get(i)));
        }
        List<List<String>> items = new ArrayList<>();
        items.add(names);
        items.add(prices);

        Log.d(TAG, items.toString());

        return items;
    }
    private static String refineName(String name){
        String result = "";
        for(int i=0; i<name.length(); i++){
            char c = name.charAt(i);
            if(c != '.' && c != '&' && !Character.isDigit(c)){
                result = result + c;
            }
        }
        return result;
    }
    private static  boolean isInlines(int x, List<Integer> desList, int margin){
        for (Integer i: desList
             ) {
            if(isPrecise(x, i, margin)){
                return true;
            }
        }
        return  false;
    }
    private static boolean isPrecise(int x, int des, int margin){
        for(int i= des - margin; i <= des+margin; i++){
            if(x == i) return true;
        }
        return false;
    }

    public void searchPicture(String searchKey) {
        String searchURL = getFullRequestURL(searchKey);

    }


}
