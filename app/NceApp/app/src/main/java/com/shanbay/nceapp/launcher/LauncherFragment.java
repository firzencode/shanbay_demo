package com.shanbay.nceapp.launcher;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shanbay.nceapp.data.DataReader;
import com.shanbay.nceapp.R;
import com.shanbay.nceapp.data.DataLesson;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class LauncherFragment extends Fragment {

    private ILauncherFragmentListener mListener;

    public interface ILauncherFragmentListener {
        void onDataLoaded(ArrayList<DataLesson> dataList, HashMap<String, Integer> wordMap);

        void onCloseLauncher();
    }

    public LauncherFragment() {
    }

    public void setListener(ILauncherFragmentListener listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_launcher, container, false);

        doPrepareData();
        return view;
    }

    /**
     * start to prepare data
     */
    private void doPrepareData() {
        PrepareDataTask task = new PrepareDataTask();
        task.execute();
    }

    private class PrepareDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HashMap<String, Integer> wordMap = DataReader.readWordAssets(getActivity());
            ArrayList<DataLesson> dataList = DataReader.readTextAssets(getActivity());
            if (mListener != null) {
                mListener.onDataLoaded(dataList, wordMap);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (mListener != null) {
                mListener.onCloseLauncher();
            }
        }
    }
}
