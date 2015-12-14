package com.shanbay.nceapp.launcher;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shanbay.nceapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LauncherFragment extends Fragment {

    private ILauncherFragmentListener mListener;

    public interface ILauncherFragmentListener {
        void onPrepareDataFinish();
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
            // TODO prepare data, now just wait a moment
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mListener != null) {
                mListener.onPrepareDataFinish();
            }
        }
    }
}
