package com.example.seefood.models;

import android.graphics.Point;
import android.util.Log;

import com.google.mlkit.vision.text.Text;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Parcel
public class SeeFoodMenu {
    public static final String TAG = "Menu Class";
    List<List<String>> menu;
    public SeeFoodMenu(Text result){
        menu = getMenu(result);
    }

    public SeeFoodMenu(){
    }

    public List<List<String>> getMenu(){
        return menu;
    }
    private static List<List<String>> getMenu(Text result) {

        Log.d(TAG, "---------------------------------------");
        String resultText = result.getText();

        List<Integer> pos = new ArrayList<>();

        for (Text.TextBlock block : result.getTextBlocks()) {
            for (Text.Line line : block.getLines()) {
                Point[] lineCornerPoints = line.getCornerPoints();
                if(pos.size() == 0) {
                    pos.add(lineCornerPoints[0].x);
                }else {
                    //if not in list [15, 12]
                    if(!isInPositionsList(lineCornerPoints[0].x, pos, 1)){
                        pos.add(lineCornerPoints[0].x);
                    }
                }
            }
        }

        Log.d(TAG, pos.toString());
        Collections.sort(pos);
        Log.d(TAG, pos.toString());
        List<List<String>> items= new ArrayList<>();
        for(int i =0; i<pos.size(); i++){
            items.add(new ArrayList<>());
        }
        //Get all categories
        for (Text.TextBlock block : result.getTextBlocks()) {
            //get line
            for (Text.Line line : block.getLines()) {
                String lineText = line.getText();
                Point[] lineCornerPoints = line.getCornerPoints();
                for(int j=0; j<pos.size(); j++){

                    if(isPrecise(lineCornerPoints[0].x, pos.get(j), 1)){
                        items.get(j).add(lineText.substring(0, lineText.length()>40?40:lineText.length()));
                        break;
                    }
                }
            }
        }


        //sort the items by size
        Comparator<List<String>> stringLengthComparator = new Comparator<List<String>>()
        {
            @Override
            public int compare(List<String> o1, List<String> o2)
            {
                return Integer.compare(o2.size(), o1.size());
            }
        };

        Collections.sort(items, stringLengthComparator);
        for(int i= items.size() - 1; i>=3 ; i--) {
            items.remove(i);
        }
        //This is how to use it:
//        for(int i=0; i<items.get(2).size()-1; i++){
//            //items.get(0) is food names or descriptions
//            //items.get(1) is descriptions or food names
//            //items.get(0) is prices
//            Log.d(TAG, items.get(0).get(i) + "\t\t\t" + items.get(2).get(i));
//            Log.d(TAG, items.get(1).get(i));
//        }

        return items;
    }


    private static boolean isInPositionsList(int x, List<Integer> positions, int margin){
        for (Integer des: positions
        ) {
            if(isPrecise(x, des, margin) == true) return true;
        }
        return false;
    }
    private static boolean isPrecise(int x, int des, int margin){
        for(int i= des - margin; i <= des+margin; i++){
            if(x == i) return true;
        }
        return false;
    }
}