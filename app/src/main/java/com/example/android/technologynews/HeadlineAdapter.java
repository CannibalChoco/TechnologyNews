package com.example.android.technologynews;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HeadlineAdapter extends RecyclerView.Adapter<HeadlineAdapter.ViewHolder>{

    private ArrayList<Headline> headlines;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView articleTitleTextView;

        public ViewHolder(View view){
            super(view);
            this.articleTitleTextView = (TextView) view.findViewById(R.id.article_title_text_view);
        }
    }

    public HeadlineAdapter(ArrayList<Headline> headline) {
        this.headlines = headline;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HeadlineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Headline currentHeadline = headlines.get(position);
        holder.articleTitleTextView.setText(currentHeadline.getHeadline());

    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }

    public void clear(){
        int size = this.headlines.size();
        this.headlines.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List<Headline> headlines){
        this.headlines.addAll(headlines);
        notifyDataSetChanged();
    }
}
