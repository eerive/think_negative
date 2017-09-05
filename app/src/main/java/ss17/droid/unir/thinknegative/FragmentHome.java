package ss17.droid.unir.thinknegative;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {


    private FloatingActionButton fabUnicorn;
    private FloatingActionButton fabBat;
    private FloatingActionButton fabExplosion;
    private FloatingActionButton fabCow;
    private FloatingActionButton fabShit;
    private FloatingActionButton fabPenguin;

    private FloatingActionMenu mFAM;
    //je nachdem, welches Bild ausgewählt ist, wird die Variable gesetzt
    private double moodSelected = 0;
    private EditText edtContent;
    private ImageView mImageView;
    private TextView view_date;

    private static final int REQUEST_GALLERY_CODE = 999;
    private static final int CAMERA_REQUEST = 1888;

    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    public static SQLiteHelper sqLiteHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_home, container, false);

        initFAB(v);
        initValues(v);

        sqLiteHelper = new SQLiteHelper(getActivity().getApplicationContext(), "ListDB.sqlite",null,1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS DBLIST(Id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, content VARCHAR, image BLOB, mood DOUBLE)");

        setDate();
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceBundle) {
        super.onActivityCreated(savedInstanceBundle);
    }

    private void saveContentToDB(View v){
        String checkContent = edtContent.getText().toString().trim();
        if(moodSelected ==0 || checkContent.isEmpty()){
            Snackbar snackbar = Snackbar.make(v.findViewById(R.id.fm_home_layout),"Please add some content.", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else {

            try {
                sqLiteHelper.insertData(
                        view_date.getText().toString(),
                        edtContent.getText().toString().trim(),
                        imageViewToByte(mImageView),
                        moodSelected
                );
                Snackbar snackbar = Snackbar.make(v.findViewById(R.id.fm_home_layout),"Added", Snackbar.LENGTH_SHORT);
                snackbar.show();
                edtContent.setText("");
                mImageView.setImageResource(R.drawable.base_transparent_square);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initFAB(final View v) {
        mFAM = v.findViewById(R.id.menu);
        mFAM.setClosedOnTouchOutside(true);
        fabUnicorn = v.findViewById(R.id.menu_mood_unicorn);
        fabUnicorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodSelected = 5;
                saveContentToDB(v);
                mFAM.close(true);
            }
        });
        ;

        fabBat = v.findViewById(R.id.menu_mood_angry);
        fabBat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodSelected = 6;
                saveContentToDB(v);
                mFAM.close(true);
            }
        });

        fabCow = v.findViewById(R.id.menu_mood_cow);
        fabCow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodSelected = 1;
                saveContentToDB(v);
                mFAM.close(true);
            }
        });

        fabExplosion = v.findViewById(R.id.menu_mood_explode);
        fabExplosion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodSelected = 2;
                saveContentToDB(v);
                mFAM.close(true);
            }
        });

        fabPenguin = v.findViewById(R.id.menu_mood_sad);
        fabPenguin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodSelected = 3;
                saveContentToDB(v);
                mFAM.close(true);
            }
        });

        fabShit = v.findViewById(R.id.menu_mood_shit);
        fabShit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodSelected = 4;
                saveContentToDB(v);
                mFAM.close(true);
            }
        });

    }

    private void setDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd. MMMM yy", Locale.GERMANY);
        String dateString = sdf.format(c.getTime());
        view_date.setText(dateString);
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode == REQUEST_GALLERY_CODE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_GALLERY_CODE);
            }
            else {
                Toast.makeText(getActivity().getApplicationContext(),"No Permissions",Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == CAMERA_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                assert bmp != null; //to stop nullable
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

                mImageView.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    private void initValues(View v) {
        mImageView = v.findViewById(R.id.fotoView);
        view_date = v.findViewById(R.id.view_today);
        edtContent = v.findViewById(R.id.input_content);

    }


    /**
     *
     mUnicornButton = v.findViewById(R.id.button_unicorn);
     mUnicornButton.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v) {
    if(moodSelected == 0){
    v.setBackgroundResource(R.color.MetallicSeaweed);
    moodSelected = 5;
    } else {
    setTransparentBackground();
    moodSelected = 0;
    onClick(v);
    }

    }
    });
     mBaseballbatButton = v.findViewById(R.id.button_bat);
     mBaseballbatButton.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v) {
    if(moodSelected == 0){
    v.setBackgroundResource(R.color.MetallicSeaweed);
    moodSelected = 6;
    } else {
    setTransparentBackground();
    moodSelected = 0;
    onClick(v);
    }

    }
    });
     mCowButton = v.findViewById(R.id.button_cow);
     mCowButton.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v) {
    if(moodSelected == 0){
    v.setBackgroundResource(R.color.MetallicSeaweed);
    moodSelected = 1;
    } else {
    setTransparentBackground();
    moodSelected = 0;
    onClick(v);
    }

    }
    });
     mExplosionButton = v.findViewById(R.id.button_explosion);
     mExplosionButton.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v) {
    if (moodSelected == 0) {
    v.setBackgroundResource(R.color.MetallicSeaweed);
    moodSelected = 2;
    } else {
    setTransparentBackground();
    moodSelected = 0;
    onClick(v);
    }
    }
    });
     mShitButton = v.findViewById(R.id.button_shit);
     mShitButton.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v) {
    if(moodSelected == 0){
    v.setBackgroundResource(R.color.MetallicSeaweed);
    moodSelected = 4;
    } else {
    setTransparentBackground();
    moodSelected = 0;
    onClick(v);
    }

    }
    });
     mPenguinButton = v.findViewById(R.id.button_penguin);
     mPenguinButton.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v) {
    if(moodSelected == 0){
    v.setBackgroundResource(R.color.MetallicSeaweed);
    moodSelected = 3;
    } else {
    setTransparentBackground();
    moodSelected = 0;
    onClick(v);
    }

    }
    });

     */
}
