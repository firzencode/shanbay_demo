package com.shanbay.nceapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.shanbay.nceapp.filelist.FileListFragment;
import com.shanbay.nceapp.launcher.LauncherFragment;

public class MainActivity extends Activity implements LauncherFragment.ILauncherFragmentListener {

    private LauncherFragment mFragmentLauncher;
    private FileListFragment mFragmentFileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openFragmentLauncher();
    }

    /**
     * Open Launcher Fragment
     */
    private void openFragmentLauncher() {
        FragmentManager fm = getFragmentManager();
        if (mFragmentLauncher == null) {
            mFragmentLauncher = new LauncherFragment();
            mFragmentLauncher.setListener(this);
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
     * Open Detail Fragment
     */
    private void openFragmentDetail() {

    }

    /* ---------- ILauncherFragmentListener ---------- */

    @Override
    public void onPrepareDataFinish() {
        openFragmentFileList();
    }


}
