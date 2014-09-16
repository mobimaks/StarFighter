package com.proandroidgames.StarFighter;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class SFMusic extends Service {

    public static boolean isRunning = false;
    private MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setMusicOptions(this, SFEngine.LOOP_BACKGROUND_MUSIC, SFEngine.R_VOLUME, SFEngine.L_VOLUME, SFEngine.SPLASH_SCREEN_MUSIC);
    }

    private void setMusicOptions(Context context, boolean isLooped, int rVolume, int lVolume, int soundFile){
        player = MediaPlayer.create(context, soundFile);
        player.setLooping(isLooped);
        player.setVolume(lVolume, rVolume);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            player.start();
            isRunning = true;
        } catch (Exception e){
            isRunning = false;
            player.stop();
        }
        return 1;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        player.stop();
        player.release();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        player.stop();
        super.onLowMemory();
    }
}
