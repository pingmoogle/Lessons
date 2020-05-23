package com.example.edgelibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONAdapter extends BaseAdapter {
    private final String imageURLBase = "https://covers.openlibrary.org/b/id/";
    Context mC;
    LayoutInflater mLI;
    JSONArray mJAr;

    public JSONAdapter(Context c, LayoutInflater inf){
        mC = c;
        mLI = inf;
        mJAr = new JSONArray();
    }

    @Override
    public int getCount() {
        return mJAr.length();
    }

    @Override
    public Object getItem(int position) {
        return mJAr.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = mLI.inflate(R.layout.row_book, null);
            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView)convertView.findViewById(R.id.img_thumbnail);
            holder.titleTextView = (TextView)convertView.findViewById(R.id.text_title);
            holder.authorTextView = (TextView)convertView.findViewById(R.id.text_author);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        JSONObject jsob = (JSONObject) getItem(position);
        if(jsob.has("cover_i")){
            String imageID = jsob.optString("cover_i");
            String imageURL = imageURLBase + imageID + "-L.jpg";
            Picasso.with(mC).load(imageURL).placeholder(R.mipmap.ic_launcher).into(holder.thumbnailImageView);
        }else{
            holder.thumbnailImageView.setImageResource(R.mipmap.ic_launcher);
        }
        String bookTitle = "";
        String authorName = "";
        if(jsob.has("title")){
            bookTitle = jsob.optString("title");
        }
        if(jsob.has("author_name")){
            authorName = jsob.optJSONArray("author_name").optString(0);
        }
        holder.titleTextView.setText(bookTitle);
        holder.authorTextView.setText(authorName);

        return convertView;
    }
    private static class ViewHolder{
        public ImageView thumbnailImageView;
        public TextView titleTextView;
        public TextView authorTextView;
    }

    public void updateData(JSONArray jsar){
        mJAr = jsar;
        notifyDataSetChanged();
    }
}
