package com.example.android.technologynews;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HeadlineAdapter extends RecyclerView.Adapter<HeadlineAdapter.ViewHolder> {

    private static final String LOG_TAG = HeadlineAdapter.class.getName();

    private ArrayList<Headline> headlines;
    private Headline currentHeadline;

    // store a reference to listItemClickListener
    private static ListItemClickListener onClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView articleTitleTextView;
        public TextView publicationTimeTextView;
        public TextView sectionTextView;

        public ViewHolder(View view) {
            super(view);
            articleTitleTextView = (TextView) view.findViewById(R.id.article_title_text_view);
            publicationTimeTextView = (TextView) view.findViewById(R.id.publications_date_text_view);
            sectionTextView = (TextView) view.findViewById(R.id.section_name_text_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }
    }

    public HeadlineAdapter(ArrayList<Headline> headline,
                           ListItemClickListener listener) {
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
        String date = formatDate(currentHeadline.getTime());
        holder.articleTitleTextView.setText(currentHeadline.getHeadline());
        holder.sectionTextView.setText(currentHeadline.getSectionName());
        holder.publicationTimeTextView.setText(date);
    }

    /**
     * Formats ISO 8601 to MMM dd, yyyy hh:mm
     *
     * @param sourceDate the date extracted from data set
     * @return formatted date and time
     */
    private String formatDate(String sourceDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'");
        Date newDate = null;
        try {
            newDate = format.parse(sourceDate);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "parsing inputDate: " + sourceDate, e);
        }
        format = new SimpleDateFormat("MMM dd, yyyy hh:mm");

        return format.format(newDate);
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }

    /**
     * Clear the adapter
     */
    public void clear() {
        int size = this.headlines.size();
        this.headlines.clear();
        notifyItemRangeRemoved(0, size);
    }

    /**
     * adds all headlines to the adapter
     */
    public void addAll(List<Headline> headlines) {
        this.headlines.addAll(headlines);
        notifyDataSetChanged();
    }

    public Headline getItem(int position) {
        return headlines.get(position);
    }
}
