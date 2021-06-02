package com.example.nationalvaccinationagency;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nationalvaccinationagency.adapters.StatisticsRecViewAdapter;
import com.example.nationalvaccinationagency.model.APIClient;
import com.example.nationalvaccinationagency.model.Statistics;
import com.example.nationalvaccinationagency.model.StatisticsByDay;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticsFragment extends Fragment implements View.OnClickListener {
    private StatisticsService statisticsService;
    private List<Statistics> statistics = new ArrayList<>();
    private static Integer totalVaccinationsUntilLastDay = 0;
    private EditText edtStartDate, edtEndDate;
    private ImageButton btnStartDate, btnEndDate;
    private Button sumbitBtn;
    private RecyclerView statisticsRecView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View statisticsView = inflater.inflate(R.layout.fragment_statistics, container, false);
        statisticsService = APIClient.getClient().create(StatisticsService.class);
        edtStartDate = statisticsView.findViewById(R.id.startDate);
        edtEndDate = statisticsView.findViewById(R.id.endDate);
        btnStartDate = statisticsView.findViewById(R.id.startDateBtn);
        btnEndDate = statisticsView.findViewById(R.id.endDateBtn);
        sumbitBtn = statisticsView.findViewById(R.id.submit_statistic_btn);

        btnStartDate.setOnClickListener(this);
        btnEndDate.setOnClickListener(this);
        sumbitBtn.setOnClickListener(this);

        String today = getDate();
        fetchStatistics("2020-12-28", today);

        return statisticsView;
    }

    /**
     * Μετατρέπει την σημερινή ημερομηνία σε string, προκειμένου να μπορεί να χρησιμοποιηθεί
     * αργότερα ως παράμετρο για κλήση του API.
     * @return
     */
    private String getDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        return strDate;
    }

    /**
     * Πραγματοποιεί κλήση στο API για να λάβει τα στατιστικά δεδομένα
     * @param dateFrom Ημερομηνία από
     * @param dateTo Ημερομηνία έως
     */
    private void fetchStatistics(String dateFrom, String dateTo) {
        String authToken = "Token 63c03103a1305a29d9da05cb11202bea82324368";
        Call<List<Statistics>> call = statisticsService.getStatitisticsResources(authToken, dateFrom, dateTo);
        call.enqueue(new Callback<List<Statistics>>() {
            @Override
            public void onResponse(Call<List<Statistics>> call, Response<List<Statistics>> response) {
                statistics = response.body();
                groupStatitistcsByDate(statistics, dateTo);
            }

            @Override
            public void onFailure(Call<List<Statistics>> call, Throwable t) {
                call.cancel();
            }
        });
    }

    /**
     * Ομαδοποιεί τα στατιστικά στοιχεία από κάθε περιφερειακή ενότητα ανά ημέρα
     * @param statistics Η λίστα με όλα τα στατιστικά
     * @param dateTo Ημερομηνία έως
     */
    private void groupStatitistcsByDate(List<Statistics> statistics, String dateTo) {
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<StatisticsByDay> statisticsByDays = new ArrayList<>();
        String today = getDate();

        for (Statistics s : statistics) {
            if (!dates.contains(s.referenceDate)) dates.add(s.referenceDate);
        }

        Collections.sort(dates);

        for (String date : dates) {
            int sumDose1ByDay = 0;
            int sumDose2ByDay = 0;
            int sumTotalDosesByDay = 0;
            StatisticsByDay statisticOfDay = new StatisticsByDay();
            for (Statistics s : statistics) {
                if (date.equals(s.referenceDate)) {
                    sumDose1ByDay += s.totalDose1;
                    sumDose2ByDay += s.totalDose2;
                    sumTotalDosesByDay += s.totalVaccinations;
                }
            }
            statisticOfDay.setTotalDose1(sumDose1ByDay);
            statisticOfDay.setTotalDose2(sumDose2ByDay);
            statisticOfDay.setDate(date);
            statisticOfDay.setTotalVaccinations(sumTotalDosesByDay);
            statisticsByDays.add(statisticOfDay);
        }

        if (dateTo.equals(today)) {
            totalVaccinationsUntilLastDay = statisticsByDays.get(statisticsByDays.size() - 1).getTotalVaccinations();
        }

        for(StatisticsByDay s:statisticsByDays) {
            s.setTotalVaccinationsUntilLastDay(totalVaccinationsUntilLastDay);
        }

        statisticsRecView = getView().findViewById(R.id.statisticsRecView);
        StatisticsRecViewAdapter statisticsRecViewAdapter= new StatisticsRecViewAdapter(getContext());
        statisticsRecViewAdapter.setStatistics(statisticsByDays);
        statisticsRecView.setAdapter(statisticsRecViewAdapter);
        statisticsRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        Log.i("dates list size", String.valueOf(dates.size()));
        Log.i("statisticOfDay", String.valueOf(statisticsByDays.size()));
        Log.i("tvUntilLastDay", String.valueOf(totalVaccinationsUntilLastDay));
    }

    /**
     * Χειρίζεται τα click events των κουμπιών που έχουν τεθεί click listeners
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == btnStartDate) {
            createStartDatePicker(v);
        } else if (v == btnEndDate) {
            createEndDatePicker(v);
        } else if (v == sumbitBtn) {
            searchStatistics();
        }
    }

    /**
     * Αναζητά στατιστικά από το API
     */
    private void searchStatistics() {
        fetchStatistics(edtStartDate.getText().toString(), edtEndDate.getText().toString());
    }

    /**
     * Δημιουργεί ένα ημερολογίο για τον καθορισμό ημερομηνίας από
     * @param v
     */
    private void createStartDatePicker(View v) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        @SuppressLint("ResourceType")
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
                    edtStartDate.setText(year + "-" + (month + 1) + "-" + day);
                } else {
                    edtStartDate.setText(year + "-0" + (month + 1) + "-" + day);
                }
            }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    /**
     * Δημιουργεί ένα ημερολογίο για τον καθορισμό ημερομηνίας έως
     * @param v
     */
    private void createEndDatePicker(View v) {
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
                    edtEndDate.setText(year + "-" + (month + 1) + "-" + day);
                } else {
                    edtEndDate.setText(year + "-0" + (month + 1) + "-" + day);
                }
            }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

}