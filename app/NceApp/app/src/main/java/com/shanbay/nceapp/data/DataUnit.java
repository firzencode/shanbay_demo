package com.shanbay.nceapp.data;

import java.util.ArrayList;

/**
 * Created by firzencode on 15/12/17.
 */
public class DataUnit {
    private ArrayList<Integer> mLessonIndexList;
    private String mUnitTitle;
    private int mUnitIndex;

    public DataUnit(int unitIndex, String unitTitle) {
        mUnitIndex = unitIndex;
        mUnitTitle = unitTitle;
        mLessonIndexList = new ArrayList<Integer>();
    }

    public int getIndex() {
        return mUnitIndex;
    }

    public String getTttle() {
        return mUnitTitle;
    }

    public ArrayList<Integer> getLessonIndexList() {
        return mLessonIndexList;
    }

    @Override
    public String toString() {
        return "Unit : index = " + mUnitIndex + " title = " + mUnitTitle;
    }
}
