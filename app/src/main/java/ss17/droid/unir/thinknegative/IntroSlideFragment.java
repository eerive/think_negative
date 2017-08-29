package ss17.droid.unir.thinknegative;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


//https://www.simplifiedcoding.net/intro-screen-slider-android-tutorial/
public class IntroSlideFragment extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";

    public static IntroSlideFragment newInstance(int layoutResId) {
        IntroSlideFragment introSlideFragment = new IntroSlideFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        introSlideFragment.setArguments(args);

        return introSlideFragment;
    }

    private int layoutResId;

    public IntroSlideFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID))
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutResId, container, false);
    }

}