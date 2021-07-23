package com.example.miwok;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    private int mColorResourceId;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View listItemView=convertView;
       if(listItemView==null)
       {
           listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
       }
       Word currentWord=getItem(position);
        TextView miwokTextView=(TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getmMiwokTranslation());

        TextView defaultTextView=(TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getmDefaultTranslation());

        ImageView imageView=(ImageView) listItemView.findViewById(R.id.image);
        if(currentWord.hasImage()){
        imageView.setImageResource(currentWord.getmImageResourceId());
        imageView.setVisibility(View.VISIBLE);}
        else
        {
            imageView.setVisibility(View.GONE);
        }
        View textContainer=listItemView.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),mColorResourceId);
        textContainer.setBackgroundColor(color);



        return listItemView;
    }
    public WordAdapter(Activity context, ArrayList<Word> words,int ColorResourceId){
    super(context,0,words);
    mColorResourceId=ColorResourceId;
    }





}
