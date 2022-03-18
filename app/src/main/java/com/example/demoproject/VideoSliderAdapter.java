package com.example.demoproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;

public class VideoSliderAdapter extends RecyclerView.Adapter<VideoSliderAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> pathsList;
    private AppCompatActivity activity;

    private boolean isFullScreen = false;
    public static boolean isFullLock = false;


    public VideoSliderAdapter(Context context, ArrayList<String> pathsList, AppCompatActivity activity) {
        this.context = context;
        this.pathsList = pathsList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_card_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.thumbnailImage.setImageBitmap(ThumbnailUtils.createVideoThumbnail(pathsList.get(position),
                MediaStore.Video.Thumbnails.FULL_SCREEN_KIND));


        // get data
        Uri videoUri = Uri.parse(pathsList.get(position));

        holder.bt_fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFullScreen) {
                    holder.bt_fullscreen.setImageDrawable(
                            ContextCompat.getDrawable(context, R.drawable.ic_baseline_fullscreen_exit)
                    );
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                } else {
                    holder.bt_fullscreen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_fullscreen));
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                isFullScreen = !isFullScreen;
            }
        });



        holder.bt_lockscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFullLock) {
                    holder.bt_lockscreen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_lock_open));
                } else {
                    holder.bt_lockscreen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_lock));
                }

                isFullLock = !isFullLock;
                lockScreen(isFullLock, holder.sec_mid, holder.sec_bottom);

            }
        });


        holder.playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.playPauseBtn.setVisibility(View.GONE);
//                        holder.thumbnailImage.setVisibility(View.GONE);
                holder.playerView.setVisibility(View.VISIBLE);
                holder.simpleExoPlayer = new SimpleExoPlayer.Builder(context)
                        .setSeekBackIncrementMs(5000)
                        .setSeekForwardIncrementMs(5000)
                        .build();
                holder.playerView.setPlayer(holder.simpleExoPlayer);
                holder.playerView.setKeepScreenOn(true);
                holder.simpleExoPlayer.addListener(new Player.Listener() {
                    @Override
                    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                        if (playbackState == Player.STATE_BUFFERING) {
                            holder.progressBar.setVisibility(View.VISIBLE);
                        } else if (playbackState == Player.STATE_READY) {
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    }
                });

                MediaItem mediaItem = MediaItem.fromUri(videoUri);
                holder.simpleExoPlayer.setMediaItem(mediaItem);
                holder.simpleExoPlayer.prepare();
                holder.simpleExoPlayer.play();
            }
        });

    }

    private void lockScreen(boolean isFullLock, LinearLayout sec_mid, LinearLayout sec_bottom) {
        if (isFullLock) {
            sec_mid.setVisibility(View.INVISIBLE);
            sec_bottom.setVisibility(View.INVISIBLE);
        } else {
            sec_mid.setVisibility(View.VISIBLE);
            sec_bottom.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return pathsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        PlayerView playerView;
        ImageView thumbnailImage;
        ImageView playPauseBtn;
        ImageView bt_fullscreen, bt_lockscreen;
        SimpleExoPlayer simpleExoPlayer;
        ProgressBar progressBar;
        LinearLayout sec_mid, sec_bottom;

        public ViewHolder(@NonNull View view) {
            super(view);
            playerView = view.findViewById(R.id.statusSliderVideo);
            thumbnailImage = view.findViewById(R.id.statusSliderThumbnailImage);
            playPauseBtn = view.findViewById(R.id.playPauseBtn);
            progressBar = view.findViewById(R.id.progress_bar);
            bt_fullscreen = view.findViewById(R.id.bt_fullscreen);
            bt_lockscreen = view.findViewById(R.id.exo_lock);
            sec_mid = view.findViewById(R.id.sec_controlvid1);
            sec_bottom = view.findViewById(R.id.sec_controlvid2);
        }
    }
}
