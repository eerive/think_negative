package ss17.droid.unir.thinknegative;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * empty
 */

public class FragmentCalendar extends Fragment {

    private CalendarView mCalendarView;
    private TextView dateDisplay;

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

        mCalendarView = v.findViewById(R.id.calendar_test);
        dateDisplay = v.findViewById(R.id.date_display);
        dateDisplay.setText("Date: ");

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                dateDisplay.setText("Date: "+i2+"/"+i1+"/"+i);
            }
        });

        return v;
    }
}
