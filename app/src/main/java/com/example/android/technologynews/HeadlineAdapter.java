package com.example.android.technologynews;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HeadlineAdapter extends ArrayAdapter{

    public HeadlineAdapter(Activity context, ArrayList<Headline> headline) {
        super(context, 0, headline);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate
                    (R.layout.list_item, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Headline currentHeadline = (Headline) getItem(position);
        viewHolder.articleTitleTextView.setText(currentHeadline.getHeadline());

        return convertView;
    }

    class ViewHolder {
        private TextView articleTitleTextView;

        public ViewHolder(View view){
            this.articleTitleTextView = (TextView) view.findViewById(R.id.article_title_text_view);
        }
    }
}
