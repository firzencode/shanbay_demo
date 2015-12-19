package com.shanbay.nceapp.filelist;


import com.shanbay.nceapp.data.DataLesson;

/**
 * This class is used to represent an item in file list.
 * It may be a Unit Item, or Lesson Item.
 */
public class FileItem {

    private boolean mIsUnit;
    private int mIndex;
    private DataLesson mData;

    public FileItem(boolean isUnit, int index, DataLesson data) {
        mIsUnit = isUnit;
        mIndex = index;
        mData = data;
    }

    /**
     * Check the item is Unit Item or Lesson Item
     *
     * @return True is Unit Item, false is Lesson Item
     */
    public boolean isUnit() {
        return mIsUnit;
    }

    /**
     * Get the item index.
     *
     * @return The index, only validated in Lesson Item
     */
    public int getIndex() {
        return mIndex;
    }

    public DataLesson getData() {
        return mData;
    }

}
