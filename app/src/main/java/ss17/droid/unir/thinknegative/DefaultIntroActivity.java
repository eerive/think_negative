package ss17.droid.unir.thinknegative;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

// TODO: insert github link for this repository
import com.github.paolorotolo.appintro.AppIntro;


public final class DefaultIntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Adding fragment slides
        addSlide(IntroSlideFragment.newInstance(R.layout.intro1));
        addSlide(IntroSlideFragment.newInstance(R.layout.intro2));
        addSlide(IntroSlideFragment.newInstance(R.layout.intro3));
        addSlide(IntroSlideFragment.newInstance(R.layout.intro4));

        showStatusBar(true);
        setDepthAnimation();

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        loadMainActivity();
        finish();
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, StartScreenActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        loadMainActivity();
        Toast.makeText(getApplicationContext(), getString(R.string.skip), Toast.LENGTH_SHORT).show();
        finish();
    }

    public void getStarted(View v){
        loadMainActivity();
        finish();
    }
}