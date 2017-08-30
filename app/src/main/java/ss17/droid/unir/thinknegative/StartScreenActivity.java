package ss17.droid.unir.thinknegative;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class StartScreenActivity extends AppCompatActivity implements OnClickListener {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

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

                    startActivity(new Intent(StartScreenActivity.this,DefaultIntroActivity.class));
                    SharedPreferences.Editor e = sharedPreferences.edit();
                    e.putBoolean(Configuration.FLAG,false);
                    e.apply();
                }
            }
        });
        t.start();
        //---------------END INTRO------------------------------------------------------------------
        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        //
        //---------------END TOOLBAR----------------------------------------------------------------
        //incomplete ActionBar
        //tut:https://www.youtube.com/watch?v=AS92bq3XxkA
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true);
        }
        //--------------END ACTIONBAR---------------------------------------------------------------
        //NavigationView handler
        NavigationView nv = (NavigationView)findViewById(R.id.navigator);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case(R.id.home):
                        mDrawerLayout.closeDrawers(); //TODO: If not on homescreen, switch to homescreen
                        break;
                    case(R.id.nav_imprint):
                        Intent nav_imprint_intent = new Intent(getApplicationContext(),
                                ImprintScreenActivity.class);
                        startActivity(nav_imprint_intent);
                        break;
                    case(R.id.to_calendar):
                        Toast.makeText(getApplicationContext(),"NOT IMPLEMENTED YET",Toast.LENGTH_SHORT)
                                .show();
                        break;
                }
                return true;
            }
        });
        //--------------END NAVVIEW HANDLER---------------------------------------------------------
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }
}

