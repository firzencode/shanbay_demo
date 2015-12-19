package com.shanbay.nceapp.launcher;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shanbay.nceapp.DataReader;
import com.shanbay.nceapp.R;
import com.shanbay.nceapp.data.DataLesson;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LauncherFragment extends Fragment {

    private ILauncherFragmentListener mListener;

    public interface ILauncherFragmentListener {
        void onPrepareDataFinish(ArrayList<DataLesson> list);
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

    private class PrepareDataTask extends AsyncTask<Void, Void, ArrayList<DataLesson>> {

        @Override
        protected ArrayList<DataLesson> doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return DataReader.readAssets(getActivity());
        }

        @Override
        protected void onPostExecute(ArrayList<DataLesson> result) {
            if (mListener != null) {
                mListener.onPrepareDataFinish(result);
            }
        }
    }
}
