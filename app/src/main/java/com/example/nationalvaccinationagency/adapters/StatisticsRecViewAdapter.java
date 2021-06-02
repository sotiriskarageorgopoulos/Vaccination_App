package com.example.nationalvaccinationagency.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nationalvaccinationagency.R;
import com.example.nationalvaccinationagency.model.StatisticsByDay;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class StatisticsRecViewAdapter extends RecyclerView.Adapter<StatisticsRecViewAdapter.ViewHolder> {
    private ArrayList<StatisticsByDay> statisticsByDays = new ArrayList<>();
    private Context context;

    public StatisticsRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.row_of_table_statistics,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(getItemCount() != 0) {
            NumberFormat nf = NumberFormat.getInstance(new Locale("da", "DK"));
            holder.date.setText(statisticsByDays.get(position).getDate().substring(0,10));
            holder.vaccinesTxt.setText(nf.format(statisticsByDays.get(position).getTotalVaccinations()));
            holder.dose1VaccinesTxt.setText(nf.format(statisticsByDays.get(position).getTotalDose1()));
            holder.dose2VaccinesTxt.setText(nf.format(statisticsByDays.get(position).getTotalDose2()));
            holder.totalVaccinesTxt.setText(nf.format(statisticsByDays.get(position).getTotalVaccinationsUntilLastDay()));
        }
    }

    @Override
    public int getItemCount() {
        return this.statisticsByDays.size();
    }

    public void setStatistics(ArrayList<StatisticsByDay> statisticsByDays) {
        this.statisticsByDays = statisticsByDays;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView vaccinesTxt;
        private TextView dose1VaccinesTxt;
        private TextView dose2VaccinesTxt;
        private TextView totalVaccinesTxt;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.dose1VaccinesTxt = itemView.findViewById(R.id.statistics_dose1);
            this.vaccinesTxt = itemView.findViewById(R.id.statistics_vaccines);
            this.dose2VaccinesTxt = itemView.findViewById(R.id.statistics_dose2);
            this.totalVaccinesTxt = itemView.findViewById(R.id.statistics_until_last_day);
            this.date = itemView.findViewById(R.id.statistics_date);
        }

    }
}
