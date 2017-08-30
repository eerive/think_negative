package ss17.droid.unir.thinknegative;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class StartScreenActivity extends AppCompatActivity implements OnClickListener {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    private ImageButton mUnicornButton;
    private ImageButton mBaseballbatButton;
    private ImageButton mExplosionButton;
    private ImageButton mCowButton;
    private ImageButton mShitButton;
    private ImageButton mPenguinButton;

    private EditText mInput;

    private ImageView mImageView;

    //Datenbank-Kram
    private ArrayList<Entry> entries;
    private NegativeDatabase negativeDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_mask);

        //Datenbank starten
        iniDB();

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
                        mDrawerLayout.closeDrawers();
                        break;
                    case(R.id.nav_imprint):
                        Intent i1 = new Intent(getApplicationContext(),ImprintScreenActivity.class);
                        startActivity(i1);
                        break;
                    case(R.id.to_calendar):
                        Toast.makeText(getApplicationContext(),"NOT IMPLEMENTED YET",Toast.LENGTH_SHORT)
                                .show();
                        break;
                }
                return true;
            }
        });

        initUI();

    }

    private void initUI() {
        mUnicornButton = (ImageButton) findViewById(R.id.button_unicorn);
        mUnicornButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mBaseballbatButton = (ImageButton) findViewById(R.id.button_bat);
        mBaseballbatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mCowButton = (ImageButton) findViewById(R.id.button_cow);
        mCowButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mExplosionButton = (ImageButton) findViewById(R.id.button_explosion);
        mExplosionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mShitButton = (ImageButton) findViewById(R.id.button_shit);
        mShitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mPenguinButton = (ImageButton) findViewById(R.id.button_penguin);
        mPenguinButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mImageView = (ImageView) findViewById(R.id.fotoView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }

    //Datenbank initial
    private void iniDB() {
        negativeDatabase = new NegativeDatabase(this);
        negativeDatabase.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Datenbank-Verbindung schließen
        negativeDatabase.close();
    }

}

