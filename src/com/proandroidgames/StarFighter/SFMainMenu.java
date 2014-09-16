package com.proandroidgames.StarFighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SFMainMenu extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SFEngine.musicThread = new Thread(){
            @Override
            public void run() {
                SFEngine.context = getApplicationContext();
                Intent bgmusic = new Intent(SFMainMenu.this, SFMusic.class);
                startService(bgmusic);
            }
        };
        SFEngine.musicThread.start();

        final SFEngine engine = new SFEngine();

        ImageButton startButton = (ImageButton)findViewById(R.id.startButton);
        ImageButton exitButton = (ImageButton)findViewById(R.id.exitButton);

        startButton.getBackground().setAlpha(SFEngine.MENU_BUTTON_ALPHA);
        startButton.setHapticFeedbackEnabled(SFEngine.HAPTIC_BUTTON_FEEDBACK);

        exitButton.getBackground().setAlpha(SFEngine.MENU_BUTTON_ALPHA);
        exitButton.setHapticFeedbackEnabled(SFEngine.HAPTIC_BUTTON_FEEDBACK);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SFMainMenu.this.startActivity(new Intent(getApplicationContext(), SFGame.class));
                /*
                start game
                 */
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean clean = false;
                clean = engine.onExit(v);
                if (clean){
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                }

            }
        });
    }

}