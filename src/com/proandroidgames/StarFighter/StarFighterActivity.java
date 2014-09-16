package com.proandroidgames.StarFighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StarFighterActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        SFEngine.context = this;
        new Handler().postDelayed(new Thread(){
            @Override
            public void run() {
                if (Thread.interrupted())
                    return;
                Intent mainMenu = new Intent(StarFighterActivity.this, SFMainMenu.class);
                StarFighterActivity.this.startActivity(mainMenu);
                StarFighterActivity.this.finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        }, SFEngine.GAME_THREAD_DELAY);
    }
}