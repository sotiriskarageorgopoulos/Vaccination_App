package com.example.nationalvaccinationagency;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nationalvaccinationagency.schema.AppointmentContract;
import com.example.nationalvaccinationagency.schema.AppointmentDBHelper;

import java.util.Calendar;

public class VaccinationAppointmentFragment extends Fragment implements View.OnClickListener {
    private ImageButton dateBtnPicker, timeBtnPicker;
    private EditText editTextPersonName, editTextPersonSurname, editTextSSN, editTextPersonTel, editTextPersonEmail, editTextDate, editTextTime;
    private String personName, personSurname, SSN, personTel, personEmail, date, time;
    private Button btnMakeAppointment;
    private AppointmentDBHelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vaccinationAppointmentView = inflater.inflate(R.layout.fragment_vaccination_appointment, container, false);
        helper = new AppointmentDBHelper(vaccinationAppointmentView.getContext());

        editTextPersonName = vaccinationAppointmentView.findViewById(R.id.personName);
        editTextPersonEmail = vaccinationAppointmentView.findViewById(R.id.personEmail);
        editTextPersonSurname = vaccinationAppointmentView.findViewById(R.id.personSurname);
        editTextPersonTel = vaccinationAppointmentView.findViewById(R.id.personTel);
        editTextSSN = vaccinationAppointmentView.findViewById(R.id.personSocialSecurityNumber);
        dateBtnPicker = vaccinationAppointmentView.findViewById(R.id.change_date_btn);
        timeBtnPicker = vaccinationAppointmentView.findViewById(R.id.change_time_btn);
        editTextDate = vaccinationAppointmentView.findViewById(R.id.appointment_date);
        editTextTime = vaccinationAppointmentView.findViewById(R.id.appointment_time);
        btnMakeAppointment = vaccinationAppointmentView.findViewById(R.id.btn_make_appointment);

        dateBtnPicker.setOnClickListener(this);
        timeBtnPicker.setOnClickListener(this);
        btnMakeAppointment.setOnClickListener(this);

        return vaccinationAppointmentView;
    }


    /**
     * Xειρισμός click events για τα κουμπιά που έχουν τεθεί click listeners
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == dateBtnPicker) {
            createDatePicker(v);
        } else if (v == timeBtnPicker) {
            createTimePicker(v);
        } else if (v == btnMakeAppointment) {
            long rowId = insertToDB();
            if (rowId == -1) {
                Toast.makeText(v.getContext(), "Try again...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), getString(R.string.arranged_appointment), Toast.LENGTH_SHORT).show();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_section, CompleteVaccinationAppointmentFragment.class, null)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    /**
     * Εισάγει τα δεδομένα του ραντεβού του χρήστη στη τοπική βάση δεδομένων SQLite
     * @return long Αν ο αριθμός είναι -1 σημαίνει ότι δεν ολοκληρώθηκε επιτυχώς η διαδικασία καθορισμού ραντεβού εμβολιασμού.
     *              Αν ο αριθμός είναι θετικός σημαίνει ότι έχει ολοκληρωθεί επιτυχώς, καθώς αντιστοιχεί στη νέα γραμμή του πίνακα
     *              που αντιστοιχεί η συγκεκριμένη εγγραφή
     */
    private long insertToDB() {
        SQLiteDatabase db = helper.getWritableDatabase();

        personName = editTextPersonName.getText().toString();
        personSurname = editTextPersonSurname.getText().toString();
        personEmail = editTextPersonEmail.getText().toString();
        SSN = editTextSSN.getText().toString();
        personTel = editTextPersonTel.getText().toString();
        date = editTextDate.getText().toString();
        time = editTextTime.getText().toString();

        boolean isValid = !(isEmptyString(personName) || isEmptyString(personSurname) || isEmptyString(personTel)
                || isEmptyString(personEmail) || isEmptyString(SSN) || isEmptyString(personTel) || isEmptyString(date)
                || isEmptyString(time));

        if(isValid) {
            ContentValues values = new ContentValues();
            values.put(AppointmentContract.AppointmentTable.COLUMN_NAME_NAME, personName);
            values.put(AppointmentContract.AppointmentTable.COLUMN_NAME_SURNAME, personSurname);
            values.put(AppointmentContract.AppointmentTable.COLUMN_NAME_EMAIL, personEmail);
            values.put(AppointmentContract.AppointmentTable.COLUMN_NAME_SOCIAL_SECURITY_NUMBER, SSN);
            values.put(AppointmentContract.AppointmentTable.COLUMN_NAME_TEL, personTel);
            values.put(AppointmentContract.AppointmentTable.COLUMN_NAME_APPOINTMENT_DATE, date);
            values.put(AppointmentContract.AppointmentTable.COLUMN_NAME_APPOINTMENT_TIME, time);
            long rowId = db.insert(AppointmentContract.AppointmentTable.TABLE_NAME, null, values);
            db.close();
            return rowId;
        }

        return -1;
    }

    /**
     * Ελέγχει αν ένα string είναι κενό ή null
     * @param str
     * @return boolean Αν είναι true τότε είναι κενό ή null, διαφορετικά όχι
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