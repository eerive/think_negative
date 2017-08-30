package ss17.droid.unir.thinknegative;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

// https://github.com/apl-devs/AppIntro
import com.github.paolorotolo.appintro.AppIntro2;


public final class ActivityIntroSlides extends AppIntro2 {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Adding fragment slides
        addSlide(FragmentIntroSlide.newInstance(R.layout.fragment_intro1));
        addSlide(FragmentIntroSlide.newInstance(R.layout.fragment_intro2));
        addSlide(FragmentIntroSlide.newInstance(R.layout.fragment_intro3));
        addSlide(FragmentIntroSlide.newInstance(R.layout.fragment_intro4));

        showStatusBar(true);
        setDepthAnimation();
        showSkipButton(false);

        //Mehr permissions? Im Manifest hinzufügen und hier abfragen :)
        askForPermissions(new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
        },3);

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        loadMainActivity();
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