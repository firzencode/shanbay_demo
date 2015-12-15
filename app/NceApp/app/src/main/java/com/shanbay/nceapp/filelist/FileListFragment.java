package com.shanbay.nceapp.filelist;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shanbay.nceapp.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FileListFragment extends Fragment {

    public interface IFileListFragmentListener {
        List<FileItem> getFileItemList();

        void onFileItemClick(int lessonIndex);
    }

    private ListView mFileListView;
    private FileListViewAdapter mFileListViewAdapter;
    private IFileListFragmentListener mListener;

    private AdapterView.OnItemClickListener mFileListItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FileItem item = mFileListViewAdapter.getItem(position);
            if (item.isUnit() == false) {
                mListener.onFileItemClick(item.getIndex());
            }
        }
    };

    public FileListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_list, container, false);

        mFileListView = (ListView) view.findViewById(R.id.file_list_view);
        mFileListViewAdapter = new FileListViewAdapter(getActivity(), R.layout.file_list_item, mListener.getFileItemList());
        mFileListView.setAdapter(mFileListViewAdapter);
        mFileListView.setOnItemClickListener(mFileListItemClickListener);
        return view;
    }

    public void setListener(IFileListFragmentListener listener) {
        mListener = listener;
    }


}
