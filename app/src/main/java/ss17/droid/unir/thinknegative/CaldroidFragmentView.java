package ss17.droid.unir.thinknegative;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.roomorama.caldroid.WeekdayArrayAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Daniela on 08.09.2017.
 * source: https://github.com/roomorama/Caldroid
 */

public class CaldroidFragmentView extends Fragment {

    public static final String DATE_EXTRA = "date";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String today = getCurrDate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calender_overview, container, false);

        CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        args.putInt( CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY );
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        caldroidFragment.setMaxDateFromString(getCurrDate(), "ddmmyy");
        caldroidFragment.refreshView();
        caldroidFragment.setArguments(args);

        //caldroidFragment.getWeekdayGridView().setAdapter();

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_caldroid, caldroidFragment).commit();

        caldroidFragment.setCaldroidListener(listener);


        return v;
    }


    //add abstract listener to caldroid - does nothing yet
    final CaldroidListener listener = new CaldroidListener() {

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        @Override
        public void onSelectDate(Date date, View view) {
            Intent intent = new Intent(getActivity().getApplicationContext(), ActivityGridList.class);
            intent.putExtra(DATE_EXTRA, date);
            startActivity(intent);
        }

        @Override
        public void onChangeMonth(int month, int year) {
            String text = "month: " + month + " year: " + year;
            /*Toast.makeText(getActivity().getApplicationContext(), text,
                    Toast.LENGTH_SHORT).show();*/
        }

        @Override
        public void onLongClickDate(Date date, View view) {
           /* Toast.makeText(getActivity().getApplicationContext(),
                    "Long click " + formatter.format(date),
                    Toast.LENGTH_SHORT).show();*/
        }

        @Override
        public void onCaldroidViewCreated() {
            /*Toast.makeText(getActivity().getApplicationContext(),
                    "Caldroid view is created",
                    Toast.LENGTH_SHORT).show();*/
        }

    };

    private String getCurrDate(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        GregorianCalendar date = new GregorianCalendar(year, month, day);
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMANY);
        String dateString = df.format(date.getTime());
        return dateString;
    }



}