package ss17.droid.unir.thinknegative;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


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

    //je nachdem, welches Bild ausgewählt ist, wird die Variable gesetzt
    private int imageSelected = 0;
    private boolean isImageSelected = false;


    EditText edtContent;
    Button btnAdd;

    private ImageView mImageView;
    private TextView view_date;


    private final int REQUEST_GALLERY_CODE = 999;
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


        initUI(v);
        sqLiteHelper = new SQLiteHelper(getActivity().getApplicationContext(), "ListDB.sqlite",null,1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS DBLIST(Id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, content VARCHAR, image BLOB)");

        setDate();
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkContent = edtContent.getText().toString().trim();
                if(checkContent.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(),"Please fill everything", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        sqLiteHelper.insertData(
                                view_date.getText().toString(),
                                edtContent.getText().toString().trim(),
                                imageViewToByte(mImageView)
                        );
                        Snackbar snackbar = Snackbar.make(v.findViewById(R.id.fm_home_layout),"Added", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        edtContent.setText("");
                        mImageView.setImageResource(R.drawable.image_add_wallpaper);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });



        return v;
    }

    private void setDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd. MMMM yy");
        String dateString = sdf.format(c.getTime());
        view_date.setText(dateString);
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
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

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

                mImageView.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    private void initUI(View v) {

        mUnicornButton = v.findViewById(R.id.button_unicorn);
        mUnicornButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(imageSelected == 0){
                    v.setBackgroundResource(R.color.MetallicSeaweed);
                    imageSelected = 5;
                } else {
                    setTransparentBackground();
                    imageSelected = 0;
                    onClick(v);
                }

            }
        });
        mBaseballbatButton = v.findViewById(R.id.button_bat);
        mBaseballbatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(imageSelected == 0){
                    v.setBackgroundResource(R.color.MetallicSeaweed);
                    imageSelected = 6;
                } else {
                    setTransparentBackground();
                    imageSelected = 0;
                    onClick(v);
                }

            }
        });
        mCowButton = v.findViewById(R.id.button_cow);
        mCowButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(imageSelected == 0){
                    v.setBackgroundResource(R.color.MetallicSeaweed);
                    imageSelected = 1;
                } else {
                    setTransparentBackground();
                    imageSelected = 0;
                    onClick(v);
                }

            }
        });
        mExplosionButton = v.findViewById(R.id.button_explosion);
        mExplosionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (imageSelected == 0) {
                    v.setBackgroundResource(R.color.MetallicSeaweed);
                    imageSelected = 2;
                } else {
                    setTransparentBackground();
                    imageSelected = 0;
                    onClick(v);
                }
            }
        });
        mShitButton = v.findViewById(R.id.button_shit);
        mShitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(imageSelected == 0){
                    v.setBackgroundResource(R.color.MetallicSeaweed);
                    imageSelected = 4;
                } else {
                    setTransparentBackground();
                    imageSelected = 0;
                    onClick(v);
                }

            }
        });
        mPenguinButton = v.findViewById(R.id.button_penguin);
        mPenguinButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(imageSelected == 0){
                    v.setBackgroundResource(R.color.MetallicSeaweed);
                    imageSelected = 3;
                } else {
                    setTransparentBackground();
                    imageSelected = 0;
                    onClick(v);
                }

            }
        });



        mImageView = v.findViewById(R.id.fotoView);
        view_date = v.findViewById(R.id.view_today);
        edtContent = v.findViewById(R.id.input_content);
        btnAdd = v.findViewById(R.id.sendButton);

    }

    private void setTransparentBackground() {
        mUnicornButton.setBackgroundColor(Color.TRANSPARENT);
        mBaseballbatButton.setBackgroundColor(Color.TRANSPARENT);
        mCowButton.setBackgroundColor(Color.TRANSPARENT);
        mExplosionButton.setBackgroundColor(Color.TRANSPARENT);
        mPenguinButton.setBackgroundColor(Color.TRANSPARENT);
        mShitButton.setBackgroundColor(Color.TRANSPARENT);
    }

}
