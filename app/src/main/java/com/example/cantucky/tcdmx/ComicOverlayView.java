package com.example.cantucky.tcdmx;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by harryHaller on 04/04/16.
 */
public class ComicOverlayView extends RelativeLayout {

    public ComicOverlayView(Context context) {
        this(context, null);
    }


    public ComicOverlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ComicOverlayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflateLayout(context);

    }


    // Inflates the Custom View Layout
    private void inflateLayout(Context context) {

        final LayoutInflater inflater = LayoutInflater.from(context);

        // Generates the layout for the view
        inflater.inflate(R.layout.comic_layout, this, true);
    }


    // Sets Book title in View
    public void setComicTitle(String comicTitle) {
        TextView tv = (TextView) findViewById(R.id.comic_title);
        tv.setText(comicTitle);
    }


    // Sets Book Author in View
    public void setComicYear(String comicYear) {
        TextView tv = (TextView) findViewById(R.id.comic_year);
        tv.setText(comicYear);
    }

}