package ss17.droid.unir.thinknegative;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class StartScreenActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_mask);

        //Toolbar
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //
        //---------------END TOOLBAR----------------------------------------------------------------
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
        //---------------END INTRO------------------------------------------------------------------
        //incomplete ActionBar
        //tut:https://www.youtube.com/watch?v=AS92bq3XxkA
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //--------------END ACTIONBAR---------------------------------------------------------------
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(mToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);

    }
}
