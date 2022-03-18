package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private VideoSliderAdapter myAdapter;
    ArrayList<String> videoPaths;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.videoViewPager);
        videoPaths = new ArrayList<>();
        videoPaths.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
        videoPaths.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");
        videoPaths.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4");
        videoPaths.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4");
        videoPaths.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4");
        // add paths for video simllarly

        myAdapter = new VideoSliderAdapter(getApplicationContext(), videoPaths,
                MainActivity.this);
        viewPager.setAdapter(myAdapter);
        viewPager.setPadding(10, 0, 10, 0);
    }
}