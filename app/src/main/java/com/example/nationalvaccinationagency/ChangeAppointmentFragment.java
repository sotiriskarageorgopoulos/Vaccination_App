package com.example.nationalvaccinationagency;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nationalvaccinationagency.schema.AppointmentContract;
import com.example.nationalvaccinationagency.schema.AppointmentDBHelper;

import java.util.Calendar;

public class ChangeAppointmentFragment extends Fragment implements View.OnClickListener {
    private ImageButton dateBtnPicker, timeBtnPicker;
    private EditText editTextTime, editTextDate;
    private Button changeBtn;
    private AppointmentDBHelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View changeAppointmentFragment = inflater.inflate(R.layout.fragment_change_appointment, container, false);

        editTextDate = changeAppointmentFragment.findViewById(R.id.edt_appointment_date);
        editTextTime = changeAppointmentFragment.findViewById(R.id.edt_appointment_time);
        dateBtnPicker = changeAppointmentFragment.findViewById(R.id.choose_date_btn);
        timeBtnPicker = changeAppointmentFragment.findViewById(R.id.choose_time_btn);
        changeBtn = changeAppointmentFragment.findViewById(R.id.btn_update_appointment);

        dateBtnPicker.setOnClickListener(this);
        timeBtnPicker.setOnClickListener(this);
        changeBtn.setOnClickListener(this);

        return changeAppointmentFragment;
    }

    /**
     * Χειρίζεται τα click event των κουμπιών του συγκεκριμένου fragment
     * @param v Είναι τα κουμπιά που τους έχει οριστεί click listener
     */
    @Override
    public void onClick(View v) {
        if (v == dateBtnPicker) {
            createDatePicker(v);
        } else if (v == timeBtnPicker) {
            createTimePicker(v);
        } else if (v == changeBtn) {
            changeAppointment(v);
        }
    }

    /**
     * Περιλαμβάνει τη λειτουργικότητα για την αλλαγή ενός ραντεβού εμβολιασμού
     * @param v
     */
    private void changeAppointment(View v) {
        helper = new AppointmentDBHelper(v.getContext());

        SQLiteDatabase db = helper.getWritableDatabase();

        String date = editTextDate.getText().toString();
        String time = editTextTime.getText().toString();

        boolean isValid = !(isEmptyString(date) || isEmptyString(time));
        if(isValid) {
            ContentValues cv = new ContentValues();
            cv.put(AppointmentContract.AppointmentTable.COLUMN_NAME_APPOINTMENT_DATE, date);
            cv.put(AppointmentContract.AppointmentTable.COLUMN_NAME_APPOINTMENT_TIME, time);

            String selection = AppointmentContract.AppointmentTable.COLUMN_NAME_EMAIL + "\tLIKE ?";
            String email = getArguments().getString("email");
            String[] args = {email};
            db.update(AppointmentContract.AppointmentTable.TABLE_NAME, cv, selection, args);
            db.close();

            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_section, CompleteVaccinationAppointmentFragment.class, null)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            Toast.makeText(getContext(),"Inputs were not completed...",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Ελέγχει αν ένα string είναι κενό ή null
     * @param str To δοθέν string
     * @return boolean Αν είναι κενό ή null επιστρέφει true, διαφορετικά false
     */
    private boolean isEmptyString(String str) {
        return str == null || str.equals("") || str.trim().equals("");
    }

    /**
     * Δημιουργεί ένα ημερολογίο για τον καθορισμό ημερομηνίας ραντεβού
     * @param v
     */
    private void createDatePicker(View v) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String day = new String();
                if (dayOfMonth > 9) {
                    day = String.valueOf(dayOfMonth);
                } else if (dayOfMonth <= 9) {
                    day = "0" + dayOfMonth;
                }
                if (month > 8) {
                    editTextDate.setText(day + "-" + (month + 1) + "-" + year);
                } else {
                    editTextDate.setText(day + "-0" + (month + 1) + "-" + year);
                }
            }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    /**
     * Δημιουργεί ένα ρολόι για τον καθορισμό ώρας ραντεβού
     * @param v
     */
    private void createTimePicker(View v) {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour = new String();
                if (hourOfDay > 9) {
                    hour = String.valueOf(hourOfDay);
                } else if (hourOfDay <= 9) {
                    hour = "0" + hourOfDay;
                }
                if (minute < 9) {
                    editTextTime.setText(hour + ":0" + minute);
                } else {
                    editTextTime.setText(hour + ":" + minute);
                }
            }
        }, mHour, mMinute, false);

        timePickerDialog.show();
    }
}