package ss17.droid.unir.thinknegative;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {

    private ImageButton mUnicornButton;
    private ImageButton mBaseballbatButton;
    private ImageButton mExplosionButton;
    private ImageButton mCowButton;
    private ImageButton mShitButton;
    private ImageButton mPenguinButton;

    private EditText mInput;

    private ImageView mImageView;

    private static final int CAMERA_REQUEST = 1888;

    public FragmentHome() {
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

        Button photoButton = v.findViewById(R.id.add_foto);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == CAMERA_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

                mImageView.setImageBitmap(bitmap);
            }
        }
    }

    private void initUI(View v) {

        mUnicornButton = v.findViewById(R.id.button_unicorn);
        mUnicornButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mBaseballbatButton = v.findViewById(R.id.button_bat);
        mBaseballbatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mCowButton = v.findViewById(R.id.button_cow);
        mCowButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mExplosionButton = v.findViewById(R.id.button_explosion);
        mExplosionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mShitButton = v.findViewById(R.id.button_shit);
        mShitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mPenguinButton = v.findViewById(R.id.button_penguin);
        mPenguinButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        mImageView = v.findViewById(R.id.fotoView);
    }

}
