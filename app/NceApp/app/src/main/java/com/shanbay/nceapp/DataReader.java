package com.shanbay.nceapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataReader {

    public static void readAssets(Context context) {
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open("nce4.txt");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufReader = new BufferedReader(reader);
            // DEBUG START
            int count = 0;
            while (bufReader.readLine() != null) {
                count++;
            }
            Log.d("TEST", "count = " + count);
            // DEBUG END
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
