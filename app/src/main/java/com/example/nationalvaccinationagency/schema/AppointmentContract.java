package com.example.nationalvaccinationagency.schema;

import android.provider.BaseColumns;

public class AppointmentContract {
    private AppointmentContract () {}

    public class AppointmentTable implements BaseColumns {
        public static final String TABLE_NAME = "Appointment";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_SURNAME = "surname";
        public static final String COLUMN_NAME_SOCIAL_SECURITY_NUMBER = "social_security_number";
        public static final String COLUMN_NAME_TEL = "tel";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_APPOINTMENT_DATE = "appointment_date";
        public static final String COLUMN_NAME_APPOINTMENT_TIME = "appointment_time";
    }
}
