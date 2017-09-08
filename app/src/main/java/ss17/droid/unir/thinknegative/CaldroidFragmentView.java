package ss17.droid.unir.thinknegative;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roomorama.caldroid.CaldroidFragment;

/**
 * Created by Daniela on 08.09.2017.
 * source: https://github.com/roomorama/Caldroid
 */

public class CaldroidFragmentView extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calender_overview, container, false);

        CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        args.putInt( CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY );
        caldroidFragment.setArguments(args);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_caldroid, caldroidFragment).commit();

        return v;
    }
}
