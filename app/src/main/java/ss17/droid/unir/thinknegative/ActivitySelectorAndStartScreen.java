package ss17.droid.unir.thinknegative;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * <empty>
 */
public class ActivitySelectorAndStartScreen extends AppCompatActivity  {

    private boolean doubleBackToExit = false;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        startFirstTimeIntro();

        //========INIT IDs==========================================================================
        mToolbar =              (Toolbar) findViewById(R.id.nav_action);
        mDrawerLayout =         (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView nv =     (NavigationView)findViewById(R.id.navigator);


        //========INIT COMPONENTS===================================================================
        initActionAndToolBar();
        initStartScreen();
        initDrawerContent(nv);
    }

    private void startFirstTimeIntro() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(Configuration.FLAG, Context.MODE_PRIVATE);
                if(sharedPreferences.getBoolean(Configuration.FLAG,true)){

                    startActivity(new Intent(ActivitySelectorAndStartScreen.this,ActivityIntroSlides.class));
                    SharedPreferences.Editor e = sharedPreferences.edit();
                    e.putBoolean(Configuration.FLAG,false);
                    e.apply();
                }
            }
        });
        t.start();
    }




    private void initActionAndToolBar() {
        //ActionBar + Toolbar
        //tut:https://www.youtube.com/watch?v=AS92bq3XxkA
        setSupportActionBar(mToolbar);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true); //TODO: needed? could be removed
        }
    }


    private void initDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void initStartScreen(){
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fragment = new FragmentHome();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    private void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;

        switch(menuItem.getItemId()){
            case R.id.nav_imprint:
                fragmentClass = FragmentImprint.class;
                break;
            case R.id.nav_home:
                fragmentClass = FragmentHome.class;
                break;
            case R.id.nav_calendar:
                fragmentClass = CaldroidFragmentView.class;
                break;
            default:
                fragmentClass = FragmentHome.class; //This can be changed?
        }
        //Create new instance
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }

        //Override currently loaded Fragment (and close Drawer when selected)
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        mDrawerLayout.closeDrawers();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Use for Hamburger Button
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        if(doubleBackToExit){
            super.onBackPressed();
            return;
        }
        this.doubleBackToExit = true;
        Toast.makeText(this,"Press again to exit.",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExit=false;
            }
        },2000); //Disable double press exit after 2 seconds
    }
}

