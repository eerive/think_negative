package ss17.droid.unir.thinknegative;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


public class ActivitySplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int SPLASH_DISPLAY_DELAY = 1000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActivitySplashScreen.this, ActivitySelectorAndStartScreen.class);
                ActivitySplashScreen.this.startActivity(intent);
                ActivitySplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_DELAY);

    }
}
