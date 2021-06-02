package com.example.nationalvaccinationagency;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.example.nationalvaccinationagency.schema.AppointmentContract;
import com.example.nationalvaccinationagency.schema.AppointmentDBHelper;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.File;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private MaterialToolbar mt;

    /**
    * Επιλέγει το fragment, ανάλογα με το item που έκανε click ο χρήστης
    * @param item Το item που έγινε click
    * @return boolean Αν είναι true σημαίνει οτι δεν εκτελούνται άλλα callbacks
    * */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.multimedia_nav_item:
                selectFragment(GalleryFragment.class);
                break;
            case R.id.home_nav_item:
                selectFragment(MainFragment.class);
                break;
            case R.id.faq_nav_item:
                selectFragment(FAQFragment.class);
                break;
            case R.id.appointment_nav_item:
                chooseAppointmentFragment();
                break;
            case R.id.statistics_nav_item:
                selectFragment(StatisticsFragment.class);
                break;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createMainScreen(savedInstanceState);
        mt = findViewById(R.id.topAppBar);
        mt.setOnMenuItemClickListener(this);
    }

    /**
     * Ορίζει το fragment που θα είναι αρχική οθόνη
     * @param savedInstanceState
     */
    private void createMainScreen(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_section, MainFragment.class, null)
                    .commit();
        }
    }

    /**
     * Επιλέγει το fragment που θα εμφανιστεί ανάλογα με την επιλογή του χρήστη στο navbar
     * @param fragment
     */
    private void selectFragment(Class fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_section, fragment, null)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Ελέγχει αν η βάση δεδομένων υπάρχει τοπικά στη συσκευή
     * @param context Aντικείμενο που σου δίνει πρόσβαση σε διάφορα resources και κλάσεις του Android Enviroment
     * @param dbName Όνομα της βάσης δεδομένων
     * @return boolean Αν είναι true σημαίνει ότι υφίσταται βάση δεδομένων, διαφορετικά όχι
     */
    private boolean existsDB(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    /**
     * Επιλέγει το κατάλληλο fragment που θα εμφανίσει στον χρήστη.
     * Πιο συγκεκριμένα, αν δεν έχει κλειστεί ραντεβού θα εμφανίζεται
     * το fragment με τη φόρμα για να συμπληρώσει τα στοιχεία του ο
     * χρήστης.
     * Αν έχει κλειστεί ραντεβού τότε θα εμφανίζονται τα στοιχεία του
     * ραντεβού εμβολιασμού του.
     */
    private void chooseAppointmentFragment() {
        boolean doesExistDB = existsDB(this, AppointmentDBHelper.DATABASE_NAME);
        if(doesExistDB) {
            AppointmentDBHelper helper = new AppointmentDBHelper(this);
            SQLiteDatabase db = helper.getReadableDatabase();
            SQLiteStatement sqLiteStatement = db.compileStatement("SELECT COUNT(*) FROM "+AppointmentContract.AppointmentTable.TABLE_NAME);
            long count = sqLiteStatement.simpleQueryForLong();
            sqLiteStatement.close();
            if(count == 1) {
                selectFragment(CompleteVaccinationAppointmentFragment.class);
            }
            else if(count == 0) {
                selectFragment(VaccinationAppointmentFragment.class);
            }
        }
        else {
            selectFragment(VaccinationAppointmentFragment.class);
        }
    }
}