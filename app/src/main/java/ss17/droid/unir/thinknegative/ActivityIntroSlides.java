package ss17.droid.unir.thinknegative;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

// TODO: insert github link for this repository
import com.github.paolorotolo.appintro.AppIntro;


public final class ActivityIntroSlides extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Adding fragment slides
        addSlide(FragmentIntroSlide.newInstance(R.layout.fragment_intro1));
        addSlide(FragmentIntroSlide.newInstance(R.layout.fragment_intro2));
        addSlide(FragmentIntroSlide.newInstance(R.layout.fragment_intro3));
        addSlide(FragmentIntroSlide.newInstance(R.layout.fragment_intro4));

        showStatusBar(true);
        setDepthAnimation();

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        loadMainActivity();
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        loadMainActivity();
        Toast.makeText(getApplicationContext(), getString(R.string.skip), Toast.LENGTH_SHORT).show();
        finish();
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, ActivityStartScreen.class);
        startActivity(intent);
    }

    public void getStarted(View v){
        //TODO: remove or add something more...
        loadMainActivity();
        finish();
    }
}