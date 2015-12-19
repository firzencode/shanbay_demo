package com.shanbay.nceapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.shanbay.nceapp.data.DataLesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataReader {

    public static ArrayList<DataLesson> readAssets(Context context) {
        ArrayList<DataLesson> mDataList = null;
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open("nce4.txt");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufReader = new BufferedReader(reader);
            ArrayList<StringBuilder> mTextList = readData(bufReader);
            is.close();

            mDataList = new ArrayList<DataLesson>();
            for (int i = 0; i < mTextList.size(); i++) {
                StringBuilder lessonText = mTextList.get(i);
                DataLesson data = readLesson(i, lessonText);
                mDataList.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("TEST", "Read Done");

        return mDataList;
    }

    private static ArrayList<StringBuilder> readData(BufferedReader reader) throws IOException {
        StringBuilder allText = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            allText.append(line + "\n");
        }
        Pattern pLesson = Pattern.compile("Lesson \\d+");
        Matcher mLesson = pLesson.matcher(allText);
        int startIndex = 0;
        int endIndex = 0;
        mLesson.find();
        startIndex = mLesson.start();
        ArrayList<StringBuilder> lessonList = new ArrayList<StringBuilder>();
        while (mLesson.find()) {
            endIndex = mLesson.start();
            // split first "Lesson xx" and second "Lesson xx"
            StringBuilder lessonText = new StringBuilder(allText.subSequence(startIndex, endIndex));
            //Log.d("TEST", lessonText.toString());
            lessonList.add(lessonText);
            startIndex = endIndex;
        }
        StringBuilder lessonText = new StringBuilder((allText.subSequence(startIndex, allText.length())));
        lessonList.add(lessonText);
        //Log.d("TEST", lessonText.toString());

        return lessonList;
    }

    private static DataLesson readLesson(int index, StringBuilder lessonText) {
        DataLesson lesson = new DataLesson(index);
        readLessonTitle(lessonText, lesson);
        readLessonText(lessonText, lesson);
        readLessonWords(lessonText, lesson);
        readLessonTrans(lessonText, lesson);
        return lesson;
    }

    private static void readLessonTitle(StringBuilder lessonText, DataLesson dataLesson) {
        Pattern patTitle = Pattern.compile("Lesson \\d+");
        Matcher matTitle = patTitle.matcher(lessonText);
        matTitle.find();
        int startIndex = matTitle.end();

        Pattern patTitleEnd = Pattern.compile("First listen and");
        Matcher matTitleEnd = patTitleEnd.matcher(lessonText);
        matTitleEnd.find();
        int endIndex = matTitleEnd.start();

        StringBuilder title = new StringBuilder(lessonText.subSequence(startIndex, endIndex));
        //Log.d("TEST", "title = " + title);

        String titleInLine = title.toString().replaceAll("\\s+", " ");
        //Log.d("TEST", "title = " + titleInLine.trim());

        Pattern patSubTitle = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher matSubTitle = patSubTitle.matcher(titleInLine);
        matSubTitle.find();

        int subTitleStartIndex = matSubTitle.start();
        String mainTitle = titleInLine.substring(0, subTitleStartIndex).trim();
        String subTitle = titleInLine.substring(subTitleStartIndex).trim();

        //Log.d("TEST", "Title = " + mainTitle);
        //Log.d("TEST", "Sub Title = " + subTitle);
        dataLesson.setTitle(mainTitle, subTitle);
    }

    private static void readLessonText(StringBuilder lessonText, DataLesson dataLesson) {
        Pattern patStart = Pattern.compile("First listen and");
        Matcher matStart = patStart.matcher(lessonText);
        matStart.find();
        int startIndex = matStart.start();

        Pattern patEnd = Pattern.compile("New words and");
        Matcher matEnd = patEnd.matcher(lessonText);
        matEnd.find();
        int endIndex = matEnd.start();

        String text = lessonText.substring(startIndex, endIndex).trim();
        dataLesson.setText(text);
//        Log.d("TEST", text);
    }

    private static void readLessonWords(StringBuilder lessonText, DataLesson dataLesson) {
        Pattern patStart = Pattern.compile("New words and.*\\s");
        Matcher matStart = patStart.matcher(lessonText);
        matStart.find();
        int startIndex = matStart.end();

        Pattern patEnd = Pattern.compile("参考译文");
        Matcher matEnd = patEnd.matcher(lessonText);
        matEnd.find();
        int endIndex = matEnd.start();

        String wordText = lessonText.substring(startIndex, endIndex);
//        Log.d("TEST", "WORD TEXT:\n" + wordText);

        Pattern patType = Pattern.compile(".+\\..*\n");
        Matcher matType = patType.matcher(wordText);
        matType.find();
        int wordStart = 0;
        do {
            String word = wordText.substring(wordStart, matType.start()).trim();
            String translation = matType.group();
            wordStart = matType.end();
            //Log.d("TEST", "W = " + word + "T = " + translation);
            dataLesson.addWord(word, translation);
        } while (matType.find());
    }

    private static void readLessonTrans(StringBuilder lessonText, DataLesson dataLesson) {
        Pattern pattern = Pattern.compile("参考译文");
        Matcher matcher = pattern.matcher(lessonText);
        matcher.find();
        String trans = lessonText.substring(matcher.start(), lessonText.length());
        dataLesson.setTranslation(trans);
        //Log.d("TEST", "======Trans:======\n" + trans);
    }
}
