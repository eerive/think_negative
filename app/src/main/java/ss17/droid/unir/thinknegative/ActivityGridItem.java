package ss17.droid.unir.thinknegative;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityGridItem extends AppCompatActivity {

    TextView textTitleDate;
    TextView textContent;
    ImageView resultImv;
    ImageView moodImv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_item);

        textTitleDate = (TextView) findViewById(R.id.result_date_title);
        textContent = (TextView) findViewById(R.id.result_content);
        resultImv = (ImageView) findViewById(R.id.result_imageview);
        moodImv = (ImageView) findViewById(R.id.mood_imageview);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b!=null){
            String resultDateTitle = (String) b.get("result_date_title");
            String resultContent = (String) b.get("result_content");
            Double resultMood = (double) b.get("result_mood");
            textTitleDate.setText(resultDateTitle);
            textContent.setText(resultContent);
            findMoodForEntry(resultMood);
        }

        if(intent.hasExtra("result_imageview")){
            Bitmap bmp = BitmapFactory.decodeByteArray(intent.getByteArrayExtra("result_imageview"), 0, intent.getByteArrayExtra("result_imageview").length);
            resultImv.setImageBitmap(bmp);
        }


        if(getSupportActionBar() != null){ //To counter Nullpo
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void findMoodForEntry(double mood) {
        int myMood = convertIntToMood((int)mood);
        moodImv.setImageResource(myMood);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public int convertIntToMood(int moodId){
        int myMood;
        switch (moodId){
            case 1: myMood = R.drawable.ic_kuh_web; break;
            case 2: myMood = R.drawable.ic_atompilz_web; break;
            case 3: myMood = R.drawable.ic_pinguin_web; break;
            case 4: myMood = R.drawable.ic_haufen_web; break;
            case 5: myMood = R.drawable.ic_unicorn_web; break;
            case 6: myMood = R.drawable.ic_bat_web; break;
            default: myMood = 0;
        }
        return myMood;
    }

}

