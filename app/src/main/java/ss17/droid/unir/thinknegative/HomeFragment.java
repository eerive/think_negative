package ss17.droid.unir.thinknegative;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ImageButton mUnicornButton;
    private ImageButton mBaseballbatButton;
    private ImageButton mExplosionButton;
    private ImageButton mCowButton;
    private ImageButton mShitButton;
    private ImageButton mPenguinButton;

    private EditText mInput;

    private ImageView mImageView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        initUI(v);



        return v;
    }


    private void initUI(View v) {

        mUnicornButton = (ImageButton)v.findViewById(R.id.button_unicorn);
        mUnicornButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mBaseballbatButton = (ImageButton)v.findViewById(R.id.button_bat);
        mBaseballbatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mCowButton = (ImageButton)v.findViewById(R.id.button_cow);
        mCowButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mExplosionButton = (ImageButton)v.findViewById(R.id.button_explosion);
        mExplosionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mShitButton = (ImageButton)v.findViewById(R.id.button_shit);
        mShitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mPenguinButton = (ImageButton)v.findViewById(R.id.button_penguin);
        mPenguinButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mImageView = (ImageView)v.findViewById(R.id.fotoView);
    }

}
