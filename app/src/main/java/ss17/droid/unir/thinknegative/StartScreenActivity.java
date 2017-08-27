package ss17.droid.unir.thinknegative;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_mask);


        // Init intro slide screen, only the first launch
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(Configuration.FLAG, Context.MODE_PRIVATE);
                    if(sharedPreferences.getBoolean(Configuration.FLAG,true)){

                        startActivity(new Intent(StartScreenActivity.this,DefaultIntro.class));
                        SharedPreferences.Editor e = sharedPreferences.edit();
                        e.putBoolean(Configuration.FLAG,false);
                        e.apply();
                    }
            }
        });
        t.start();

        //


    }

}
