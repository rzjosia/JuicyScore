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

import java.text.SimpleDateFormat;
import java.util.Locale;

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

        @SuppressLint("Range") float decorationRate =  mCursor.getFloat(mCursor.getColumnIndex(
                Rating.RatingEntry.COLUMN_DECORATION_RATE));

        @SuppressLint("Range") float foodRate =  mCursor.getFloat(mCursor.getColumnIndex(
                Rating.RatingEntry.COLUMN_FOOD_RATE));

        @SuppressLint("Range") float serviceRate =  mCursor.getFloat(mCursor.getColumnIndex(
                Rating.RatingEntry.COLUMN_SERVICE_RATE));

        @SuppressLint("Range") long id = mCursor.getLong(mCursor.getColumnIndex(
                Rating.RatingEntry._ID));

        @SuppressLint("Range") float datetime = mCursor.getFloat(mCursor.getColumnIndex(
                Rating.RatingEntry.COLUMN_DATETIME));

        String dateFormat = "dd/MM/yyyy à kk:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.FRANCE);

        float score = (serviceRate + foodRate + decorationRate) / 3;
        holder.restaurantNameTextView.setText(restaurantName);
        holder.datetimeTextView.setText(simpleDateFormat.format(datetime));
        holder.scoreTextView.setText(String.valueOf(score));

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
