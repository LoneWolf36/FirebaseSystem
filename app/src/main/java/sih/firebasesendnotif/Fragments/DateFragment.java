package sih.firebasesendnotif.Fragments;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.time.Month;
import java.time.Year;
import java.util.Calendar;

import sih.firebasesendnotif.R;

/**
 * Created by root on 21/3/18.
 */

public class DateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, (month), day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        TextView date = (TextView) getActivity().findViewById(R.id.in_date);
        date.setText(String.valueOf(i2)+"-"+String.valueOf(i1+1)+"-"+String.valueOf(i));
    }
}
