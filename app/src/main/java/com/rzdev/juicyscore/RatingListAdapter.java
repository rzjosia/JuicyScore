package com.rzdev.juicyscore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rzdev.juicyscore.data.Rating;

public class RatingListAdapter extends RecyclerView.Adapter<RatingListAdapter.RatingViewHolder> {
    private Cursor mCursor;
    private Context mContext;

    public RatingListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @NonNull
    @Override
    public RatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fragment_rating, parent, false);
        return new RatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position) || position <= 0) {
            return;
        }

        @SuppressLint("Range") String restaurantName = mCursor.getString(mCursor.getColumnIndex(
                Rating.RatingEntry.COLUMN_RESTAURANT_NAME));

        @SuppressLint("Range") long id = mCursor.getLong(mCursor.getColumnIndex(Rating.RatingEntry._ID));

        holder.restaurantNameTextView.setText(restaurantName);

        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        Log.e("ItemCount", String.valueOf(mCursor.getCount()));
        return mCursor.getCount();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    class RatingViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantNameTextView;
        TextView datetimeTextView;
        TextView scoreTextView;

        public RatingViewHolder(View itemView) {
            super(itemView);
            restaurantNameTextView = (TextView) itemView.findViewById(R.id.fragment_rating_restaurant_name);
            datetimeTextView = (TextView) itemView.findViewById(R.id.fragment_rating_datetime);
            scoreTextView = (TextView) itemView.findViewById(R.id.fragment_rating_score);
        }
    }
}
