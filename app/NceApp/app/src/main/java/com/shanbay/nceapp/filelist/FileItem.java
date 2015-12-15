package com.shanbay.nceapp.filelist;


/**
 * This class is used to represent an item in file list.
 * It may be a Unit Item, or Lesson Item.
 */
public class FileItem {

    private boolean mIsUnit;
    private String mTitle;
    private String mSubTitle;
    private int mLessonIndex;

    /**
     * Create a file item.
     *
     * @param isUnit      True is Unit Item, false is Lesson Item
     * @param title       The item's title.
     * @param subTitle    The item's subtitle, only validated in Lesson Item
     * @param lessonIndex The item's index, only validated in Lesson Item
     */
    public FileItem(boolean isUnit, String title, String subTitle, int lessonIndex) {
        mIsUnit = isUnit;
        mTitle = title;
        mSubTitle = subTitle;
        mLessonIndex = lessonIndex;
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
     * Get the item title.
     *
     * @return The title.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Get the item subtitle.
     *
     * @return The subtitle, only validated in Lesson Item
     */
    public String getSubTitle() {
        return mSubTitle;
    }

    /**
     * Get the item index.
     *
     * @return The index, only validated in Lesson Item
     */
    public int getIndex() {
        return mLessonIndex;
    }

}
