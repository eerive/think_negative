package ss17.droid.unir.thinknegative;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;


public class StartScreenActivity extends AppCompatActivity  {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;



    //Datenbank-Kram
    private ArrayList<Entry> entries;
    private NegativeDatabase negativeDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);


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
        //getSupportActionBar().setTitle("Think Negative");
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
        //Create Fragment
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fragment = new HomeFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
        //----------------END FRAGMENT--------------------------------------------------------------
        NavigationView nv = (NavigationView)findViewById(R.id.navigator);
        setupDrawerContent(nv);
        /*
        //NavigationView handler
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case(R.id.home):
                        mDrawerLayout.closeDrawers();
                        break;
                    case(R.id.nav_imprint):
                        Toast.makeText(getApplicationContext(),"TEST",Toast.LENGTH_SHORT).show();
                        break;
                    case(R.id.to_calendar):
                        Toast.makeText(getApplicationContext(),"NOT IMPLEMENTED YET",Toast.LENGTH_SHORT)
                                .show();
                        break;
                }
                return true;
            }
        });
        */
        //-------------------END NAVVIEW------------------------------------------------------------


    }


    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass = null;

        switch(menuItem.getItemId()){
            case R.id.nav_imprint:
                fragmentClass = ImprintScreenFragment.class;
                break;
            case R.id.nav_home:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_calendar:
                fragmentClass = CalendarScreenFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        mDrawerLayout.closeDrawers();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


}

