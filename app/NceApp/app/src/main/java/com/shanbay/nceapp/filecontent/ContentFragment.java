package com.shanbay.nceapp.filecontent;


import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shanbay.nceapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {

    public interface IContentFragmentListener {
        void onCloseFragment();
    }

    private IContentFragmentListener mListener;

    public ContentFragment() {

    }

    public void setListener(IContentFragmentListener listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    mListener.onCloseFragment();
                    return true;
                }
                return false;
            }
        });
    }
}
