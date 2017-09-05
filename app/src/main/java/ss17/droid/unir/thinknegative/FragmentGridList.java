package ss17.droid.unir.thinknegative;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * empty
 */

public class FragmentGridList extends Fragment {

    GridView gridView;
    ArrayList<DBList> list;
    DBListAdapter adapter = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //PLACEHOLDER THINGS HERE
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        gridView = v.findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new DBListAdapter(getActivity(),R.layout.fragment_calendar_customgridview, list);
        gridView.setAdapter(adapter);



        //get data from sqlite
        Cursor cursor = FragmentHome.sqLiteHelper.getData("SELECT * FROM DBLIST ORDER BY Id DESC");
        list.clear();
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
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //DATA IS NOT BEING SENT YET!!!
                Intent next = new Intent(getActivity().getApplicationContext(),ActivityGridItem.class);
                startActivityForResult(next,0);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                CharSequence[] items = {"Open", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        if (item == 0){
                            //OPEN ON LONG PRESS; SAME AS SET ON CLICK LISTENER
                            Toast.makeText(getActivity().getApplicationContext(),"not implemented yet...",Toast.LENGTH_SHORT)
                                    .show();
                        }
                        else{
                            //DELETE
                            Cursor c = FragmentHome.sqLiteHelper.getData("SELECT id FROM DBLIST");
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

        return v;
    }


    @SuppressWarnings("deprecation")
    private void showDialogDelete(final int idDBList){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getActivity());

        dialogDelete.setTitle("WARNING");
        dialogDelete.setMessage("It can't be undone! Are you sure?");
        dialogDelete.setPositiveButton(Html.fromHtml("<font color ='#000000'>OK</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    FragmentHome.sqLiteHelper.deleteData(idDBList);
                    Snackbar snackbarDel = Snackbar.make(getActivity().findViewById(R.id.fm_cal_layout),"Deleted", Snackbar.LENGTH_SHORT);
                    snackbarDel.show();
                } catch (Exception e)
                {
                    Log.e("ERROR", e.getMessage());
                }
                updateDBList();
            }
        });

        dialogDelete.setNegativeButton(Html.fromHtml("<font color ='#000000'>CANCEL</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogDelete.show();
    }
    private void updateDBList() {
        Cursor cursor = FragmentHome.sqLiteHelper.getData("SELECT * FROM DBLIST");
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
}
