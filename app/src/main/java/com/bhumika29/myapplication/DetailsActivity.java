package com.bhumika29.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Natasha on 5/6/2016.
 */
public class DetailsActivity extends AppCompatActivity {
    private TextView titleTextView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_item_detail_layout);
        String postComments = getIntent().getStringExtra("postComments");
        String image = getIntent().getStringExtra("image");

        imageView = (ImageView) findViewById(R.id.grid_item_image);
        Picasso.with(this).load(image).into(imageView);

        titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(postComments);
    }
}