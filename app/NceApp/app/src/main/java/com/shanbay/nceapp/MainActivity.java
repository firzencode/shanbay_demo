package com.shanbay.nceapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.shanbay.nceapp.data.DataLesson;
import com.shanbay.nceapp.filecontent.ContentFragment;
import com.shanbay.nceapp.filelist.FileListFragment;
import com.shanbay.nceapp.filelist.FileItem;
import com.shanbay.nceapp.launcher.LauncherFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private LauncherFragment mFragmentLauncher;
    private FileListFragment mFragmentFileList;
    private ContentFragment mFragmentContent;

    private LauncherFragment.ILauncherFragmentListener mFragmentLauncherListener = new LauncherFragment.ILauncherFragmentListener() {
        @Override
        public void onPrepareDataFinish(ArrayList<DataLesson> dataList) {
            mDataLessonList.addAll(dataList);

            for (int i = 0; i < mDataLessonList.size(); i++) {
                if (i % 8 == 0) {
                    mFileItemList.add(new FileItem(true, i / 8, null));
                }
                FileItem lessonItem = new FileItem(false, i, mDataLessonList.get(i));
                mFileItemList.add(lessonItem);
            }
            openFragmentFileList();
        }
    };

    public FileListFragment.IFileListFragmentListener mFragmentFileListListener = new FileListFragment.IFileListFragmentListener() {
        @Override
        public List<FileItem> getFileItemList() {
            return mFileItemList;
        }


        @Override
        public void onFileItemClick(int lessonIndex) {
            openFragmentContent(lessonIndex);
        }
    };

    private ContentFragment.IContentFragmentListener mFragmentContentListener = new ContentFragment.IContentFragmentListener() {
        @Override
        public void onCloseFragment() {
            closeFragmentContent();
        }
    };

    private ArrayList<FileItem> mFileItemList;
    private ArrayList<DataLesson> mDataLessonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFileItemList = new ArrayList<FileItem>();
        mDataLessonList = new ArrayList<DataLesson>();

        /* --- DEBUG --- */
//        for (int i = 0; i < 48; i++) {
//            boolean isUnit = (i % 7 == 0);
//            mFileItemList.add(new FileItem(isUnit, "title", "sub title", i));
//        }

        /* --- DEBUG END --- */

        openFragmentLauncher();
    }

    /**
     * Open Launcher Fragment
     */
    private void openFragmentLauncher() {
        FragmentManager fm = getFragmentManager();
        if (mFragmentLauncher == null) {
            mFragmentLauncher = new LauncherFragment();
            mFragmentLauncher.setListener(mFragmentLauncherListener);
        }
        if (mFragmentLauncher.isAdded() == false) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.fade_in, 0);
            //ft.replace(R.id.main_content, mFragmentLauncher);
            ft.add(R.id.main_content, mFragmentLauncher);
            ft.commit();
        }
    }

    /**
     * Open File List Fragment
     */
    private void openFragmentFileList() {
        FragmentManager fm = getFragmentManager();
        if (mFragmentFileList == null) {
            mFragmentFileList = new FileListFragment();
            mFragmentFileList.setListener(mFragmentFileListListener);
        }
        if (mFragmentFileList.isAdded() == false) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            if (mFragmentLauncher.isAdded()) {
                ft.remove(mFragmentLauncher);
            }
            ft.add(R.id.main_content, mFragmentFileList);
            ft.commit();
        }
    }

    /**
     * Open Content Fragment
     */
    private void openFragmentContent(int lessonIndex) {
        FragmentManager fm = getFragmentManager();
        if (mFragmentContent == null) {
            mFragmentContent = new ContentFragment();
            mFragmentContent.setListener(mFragmentContentListener);
            // set file item index;
        }
        if (mFragmentContent.isAdded() == false) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_right, 0);
            ft.add(R.id.main_content, mFragmentContent);
            ft.commit();
        }
    }

    private void closeFragmentContent() {
        FragmentManager fm = getFragmentManager();
        if (mFragmentFileList == null) {
            mFragmentFileList = new FileListFragment();
            mFragmentFileList.setListener(mFragmentFileListListener);
        }
        if (mFragmentContent.isAdded()) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(0, R.anim.slide_out_right);
            ft.remove(mFragmentContent);
            ft.commit();
        }
    }
}
