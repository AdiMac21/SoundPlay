package com.example.adrian.soundapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private SoundPool sp;
    private Button soundpool;
    private Button mediaplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        soundpool = (Button) findViewById(R.id.soundpool_bt);
        mediaplay = (Button) findViewById(R.id.mediaplayer_bt);
        soundpool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paySound();
            }
        });

        mediaplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });
    }

    private void paySound() {
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        if (sp != null) {
            final int streamID = sp.load(MainActivity.this, R.raw.sms, 1);
            sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    float volume = 0.5f;
                    sp.play(streamID, volume, volume, 0, 0, 1);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sp.stop(streamID);
                            sp.unload(streamID);
                            sp.release();
                            sp = null;
                        }
                    }, 2000);
                }
            });
        }
    }

    private void play() {
        MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.sms);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                mp = null;
            }
        });
        mp.start();
    }
}
