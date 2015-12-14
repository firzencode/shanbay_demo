package com.shanbay.nceapp.filelist;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shanbay.nceapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FileListFragment extends Fragment {

    private Handler mHandler;

    public FileListFragment() {
        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_list, container, false);

        return view;
    }
}
