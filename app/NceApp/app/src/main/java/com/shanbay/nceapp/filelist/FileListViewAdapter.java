package com.shanbay.nceapp.filelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shanbay.nceapp.R;

import java.util.List;

public class FileListViewAdapter extends ArrayAdapter<FileItem> {
    private class ItemHolder {
        TextView mTvIndex;
        TextView mTvTitle;
        TextView mTvSubTitle;
        TextView mTvUnitTitle;
        View mLayoutUnit;
        View mLayoutLesson;
    }

    private int mResourceId;
    private LayoutInflater mInflater;

    public FileListViewAdapter(Context context, int resource, List<FileItem> objects) {
        super(context, resource, objects);
        mResourceId = resource;
        mInflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder holder;
        if (null == convertView) {
            holder = new ItemHolder();
            convertView = mInflater.inflate(mResourceId, null);
            holder.mLayoutUnit = convertView.findViewById(R.id.file_item_layout_unit);
            holder.mLayoutLesson = convertView.findViewById(R.id.file_item_layout_lesson);
            holder.mTvIndex = (TextView) convertView.findViewById(R.id.file_item_lesson_index);
            holder.mTvTitle = (TextView) convertView.findViewById(R.id.file_item_lesson_title);
            holder.mTvSubTitle = (TextView) convertView.findViewById(R.id.file_item_lesson_subtitle);
            holder.mTvUnitTitle = (TextView) convertView.findViewById(R.id.file_item_unit_title);
            convertView.setTag(holder);
        } else {
            holder = (ItemHolder) convertView.getTag();
        }

        FileItem item = getItem(position);

        if (item.isUnit()) {
            holder.mLayoutUnit.setVisibility(View.VISIBLE);
            holder.mLayoutLesson.setVisibility(View.GONE);

            holder.mTvUnitTitle.setText(item.getTitle());
        } else {
            holder.mLayoutUnit.setVisibility(View.GONE);
            holder.mLayoutLesson.setVisibility(View.VISIBLE);

            holder.mTvTitle.setText(item.getTitle());
            holder.mTvSubTitle.setText(item.getSubTitle());

            holder.mTvIndex.setText(String.valueOf(item.getIndex()));
        }

        return convertView;
    }
}
