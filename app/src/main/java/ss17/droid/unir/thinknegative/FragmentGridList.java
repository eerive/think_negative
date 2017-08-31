package ss17.droid.unir.thinknegative;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
        Cursor cursor = FragmentHome.sqLiteHelper.getData("SELECT * FROM DBLIST");
        list.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new DBList(id,title,content,image));
        }
        adapter.notifyDataSetChanged();

        return v;
    }
}
