package com.example.egiticimatematikoyunlari;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class SoundService extends Service {
    MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        player=MediaPlayer.create(this,R.raw.music);
        player.setLooping(true);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return Service.START_NOT_STICKY;
    }


    public void onDestroy() {
        if (player.isPlaying()){
            player.stop();
        }
        super.onDestroy();

    }

}
