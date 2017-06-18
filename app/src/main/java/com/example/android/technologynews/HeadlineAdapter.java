package com.example.android.technologynews;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class HeadlineAdapter extends RecyclerView.Adapter<HeadlineAdapter.ViewHolder> {

    private ArrayList<Headline> headlines;
    private Headline currentHeadline;
    private Context context;

    // store a reference to listItemClickListener
    private static ListItemClickListener onClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView articleTitleTextView;

        public ViewHolder(View view){
            super(view);
            articleTitleTextView = (TextView) view.findViewById(R.id.article_title_text_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }
    }

    public HeadlineAdapter(Context context, ArrayList<Headline> headline,
                           ListItemClickListener listener) {
        this.context = context;
        this.headlines = headline;
        onClickListener = listener;
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
        currentHeadline = headlines.get(position);
        holder.articleTitleTextView.setText(currentHeadline.getHeadline());
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }

    /**
     * Clear the adapter
     */
    public void clear(){
        int size = this.headlines.size();
        this.headlines.clear();
        notifyItemRangeRemoved(0, size);
    }

    /**
     * adds all headlines to the adapter
     */
    public void addAll(List<Headline> headlines){
        this.headlines.addAll(headlines);
        notifyDataSetChanged();
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public Headline getItem (int position){
        return headlines.get(position);
    }
}
