package com.example.nationalvaccinationagency;

import android.content.Intent;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class FullScreenImageActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        showFullScreenImage();
    }

    /**
     * Εμφανίζει την εικόνα σε full screen
     */
    public void showFullScreenImage() {
        imageView = findViewById(R.id.full_screen_image);
        Intent i = getIntent();
        int imagePath = i.getExtras().getInt("imageResId");
        imageView.setImageResource(imagePath);
    }
}