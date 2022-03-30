package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
        videoPaths.add("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4");
        videoPaths.add("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4");
        videoPaths.add("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4");
        videoPaths.add("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4");
        videoPaths.add("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4");
        videoPaths.add("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4");
        videoPaths.add("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4");
        videoPaths.add("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4");
        // add paths for video simllarly

        myAdapter = new VideoSliderAdapter(getApplicationContext(), videoPaths,
                MainActivity.this);
        viewPager.setAdapter(myAdapter);
        viewPager.setPadding(10, 0, 10, 0);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d("ahihi","onPageSelected = " + position);
                RecyclerView rv = (RecyclerView) viewPager.getChildAt(0);
                VideoSliderAdapter.ViewHolder viewHolder = (VideoSliderAdapter.ViewHolder) rv.findViewHolderForAdapterPosition(position);
                if(!viewHolder.isPlaying){
                    viewHolder.playPauseBtn.performClick();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }
}