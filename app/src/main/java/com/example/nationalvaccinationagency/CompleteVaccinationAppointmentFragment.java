package com.example.nationalvaccinationagency;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nationalvaccinationagency.schema.AppointmentContract;
import com.example.nationalvaccinationagency.schema.AppointmentDBHelper;


public class CompleteVaccinationAppointmentFragment extends Fragment implements View.OnClickListener {
    private TextView nameTextView, surnameTextView, dateTextView, timeTextView;
    private String email;
    private Button btnChange, btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View completeVaccinationAppointmentView = inflater.inflate(R.layout.fragment_complete_vaccination_appointment, container, false);

        retrieveUserData(completeVaccinationAppointmentView);

        btnChange = completeVaccinationAppointmentView.findViewById(R.id.btn_change_appointment);
        btnCancel = completeVaccinationAppointmentView.findViewById(R.id.btn_cancel_appointment);

        btnChange.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        completeVaccinationAppointmentView.setFocusableInTouchMode(true);
        completeVaccinationAppointmentView.requestFocus();
        completeVaccinationAppointmentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                }
                return false;
            }
        });
        return completeVaccinationAppointmentView;
    }

    /**
     * Ανακτά τα δεδομένα από τη τοπική βάση SQLite
     * @param v
     */
    private void retrieveUserData(View v) {
        AppointmentDBHelper helper = new AppointmentDBHelper(v.getContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {
                AppointmentContract.AppointmentTable.COLUMN_NAME_NAME,
                AppointmentContract.AppointmentTable.COLUMN_NAME_SURNAME,
                AppointmentContract.AppointmentTable.COLUMN_NAME_APPOINTMENT_DATE,
                AppointmentContract.AppointmentTable.COLUMN_NAME_APPOINTMENT_TIME,
                AppointmentContract.AppointmentTable.COLUMN_NAME_EMAIL
        };

        nameTextView = v.findViewById(R.id.vaccinatedPersonName);
        surnameTextView = v.findViewById(R.id.vaccinatedPersonSurname);
        dateTextView = v.findViewById(R.id.date_appointment_text);
        timeTextView = v.findViewById(R.id.time_appointment_text);

        Cursor cursor = db.query(AppointmentContract.AppointmentTable.TABLE_NAME, projection, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String surname = cursor.getString(1);
            String date = cursor.getString(2);
            String time = cursor.getString(3);
            email = cursor.getString(4);

            nameTextView.setText(name);
            surnameTextView.setText(surname);
            dateTextView.setText(getString(R.string.complete_vaccination_appointment_text_date) + "\t" + date);
            timeTextView.setText(getString(R.string.complete_vaccination_appointment_text_time) + "\t" + time);
        }
        db.close();
    }

    /**
     * Χειρίζεται τα click event στα κουμπιά ακύρωσης και αλλαγής ραντεβού εμβολιασμού
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == btnCancel) {
            deleteAppointment(v);
        } else if (v == btnChange) {
            changeAppointment();
        }
    }

    /**
     * Ανακατευθύνει τον χρήστη στο ChangeAppointmentFragment fragment, που
     * αφορά την αλλαγή ημερομηνίας και ώρας του ραντεβού εμβολιασμού
     */
    private void changeAppointment() {
        Bundle args = new Bundle();
        args.putString("email", email);

        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_section, ChangeAppointmentFragment.class, args)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Περιλαμβάνει τη λειτουργικότητα για τη διαγραφή ενός ραντεβού εμβολιασμού
     * @param v
     */
    private void deleteAppointment(View v) {
        AppointmentDBHelper helper = new AppointmentDBHelper(v.getContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = AppointmentContract.AppointmentTable.COLUMN_NAME_EMAIL + "\tLIKE ?";
        String[] args = {email};
        db.delete(AppointmentContract.AppointmentTable.TABLE_NAME, selection, args);
        db.close();
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_section, MainFragment.class, null)
                .addToBackStack(null)
                .commit();
        Toast.makeText(v.getContext(), getString(R.string.delete_appointment), Toast.LENGTH_SHORT).show();
    }
}