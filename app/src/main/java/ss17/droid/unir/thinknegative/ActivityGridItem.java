package ss17.droid.unir.thinknegative;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityGridItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_item);

        TextView textTitleDate = (TextView) findViewById(R.id.result_date_title);
        TextView textContent = (TextView) findViewById(R.id.result_content);
        ImageView resultImv = (ImageView) findViewById(R.id.result_imageview);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b!=null){
            String resultDateTitle = (String) b.get("result_date_title");
            String resultContent = (String) b.get("result_content");
            textTitleDate.setText(resultDateTitle);
            textContent.setText(resultContent);
        }

        if(intent.hasExtra("result_imageview")){
            Bitmap bmp = BitmapFactory.decodeByteArray(intent.getByteArrayExtra("result_imageview"), 0, intent.getByteArrayExtra("result_imageview").length);
            resultImv.setImageBitmap(bmp);
        }


        if(getSupportActionBar() != null){ //To counter Nullpo
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
}

