package ss17.droid.unir.thinknegative;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * empty
 */

public class ActivityGridList extends AppCompatActivity {

    GridView gridView;
    ArrayList<DBList> list;
    DBListAdapter adapter = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_list);

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new DBListAdapter(this,R.layout.fragment_calendar_customgridview, list);
        gridView.setAdapter(adapter);

        if(getSupportActionBar() != null){ //To counter Nullpo
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //get date of field clicked on
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        Date d = (Date) b.get(CaldroidFragmentView.DATE_EXTRA);

        String date = formatDate(d);

        Cursor cursor = FragmentHome.sqLiteHelper.getData("SELECT * FROM DBLIST WHERE title = '" + date + "' ORDER BY Id DESC");
        list.clear();
        if (cursor.getCount()!=0) {
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);
                byte[] image = cursor.getBlob(3);
                double mood = cursor.getDouble(4);
                list.add(new DBList(id,title,content,image, mood));
            }
            adapter.notifyDataSetChanged();

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Cursor c = FragmentHome.sqLiteHelper.getData("SELECT id FROM DBLIST ORDER BY id DESC");
                    ArrayList<Integer> arrID = new ArrayList<Integer>();
                    while (c.moveToNext()){
                        arrID.add(c.getInt(0));
                    }
                    int pickedID = arrID.get(position);
                    //TODO: DEBUG TOAST
                    Toast.makeText(getApplicationContext(), "ID " + pickedID + " picked\n" + "Position " + position, Toast.LENGTH_SHORT).show();

                    openActivityWithSelectedContent(position);

                }
            });

            gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                    CharSequence[] items = {"Open", "Delete"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActivityGridList.this);

                    dialog.setTitle("Choose an action");
                    dialog.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int item) {
                            if (item == 0){
                                Cursor c = FragmentHome.sqLiteHelper.getData("SELECT id FROM DBLIST");
                                ArrayList<Integer> arrID = new ArrayList<Integer>();
                                while (c.moveToNext()){
                                    arrID.add(c.getInt(0));
                                }
                                openActivityWithSelectedContent(position);
                            }
                            else{
                                //DELETE
                                Cursor c = FragmentHome.sqLiteHelper.getData("SELECT id FROM DBLIST ORDER BY Id DESC");
                                ArrayList<Integer> arrID = new ArrayList<Integer>();
                                while (c.moveToNext()){
                                    arrID.add(c.getInt(0));
                                }
                                showDialogDelete(arrID.get(position));
                            }
                        }
                    });
                    dialog.show();
                    return true;
                }
            });


        } else {
            Toast.makeText(this, "Entry does not exist", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //prepare date from intent, so that it can be compared with database
    private String formatDate(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd. MMMM yy ", Locale.GERMANY);
        return sdf.format(d);
    }

    private void openActivityWithSelectedContent(final int position) {
        Intent pushDataToActivity = new Intent(this, ActivityGridItem.class);
        Bundle dataBundle = new Bundle();
        DBList clickedObject = list.get(position);

        dataBundle.putString("result_date_title",clickedObject.getTitle());
        dataBundle.putString("result_content",clickedObject.getContent());
        dataBundle.putByteArray("result_imageview",clickedObject.getImage());

        pushDataToActivity.putExtras(dataBundle);

        startActivity(pushDataToActivity);
    }


    @SuppressWarnings("deprecation")
    private void showDialogDelete(final int idDBList){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ActivityGridList.this);

        dialogDelete.setTitle("WARNING");
        dialogDelete.setMessage("It can't be undone! Are you sure?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    FragmentHome.sqLiteHelper.deleteData(idDBList);
                    Snackbar snackbarDel = Snackbar.make(findViewById(R.id.fm_cal_layout),"Deleted", Snackbar.LENGTH_SHORT);
                    snackbarDel.show();
                } catch (Exception e)
                {
                    Log.e("ERROR", e.getMessage());
                }
                updateDBList();
            }
        });
        dialogDelete.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogDelete.show();
    }


    private void updateDBList() {
        Cursor cursor = FragmentHome.sqLiteHelper.getData("SELECT * FROM DBLIST ORDER BY id DESC");
        list.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            double mood = cursor.getDouble(4);

            list.add(new DBList(id,title,content,image,mood));
        }
        adapter.notifyDataSetChanged();
    }

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
