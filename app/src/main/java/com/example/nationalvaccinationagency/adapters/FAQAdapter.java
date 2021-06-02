package com.example.nationalvaccinationagency.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.example.nationalvaccinationagency.R;

import java.util.ArrayList;

public class FAQAdapter extends BaseExpandableListAdapter {
    private Context context;
    ArrayList<String> questionsList;
    ArrayList<String> answerList;

    public FAQAdapter(Context context,ArrayList<String> questionsList,ArrayList<String> answerList) {
        this.context = context;
        this.questionsList = questionsList;
        this.answerList = answerList;
    }

    @Override
    public int getGroupCount() {
        return this.questionsList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.questionsList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.answerList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Δημιουργεί ένα expandable view για τις ερωτήσεις
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String question = (String) getGroup(groupPosition);
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.faq_list_group,null);
        }
        TextView questionView = convertView.findViewById(R.id.question);
        questionView.setTypeface(null, Typeface.BOLD);
        questionView.setTextColor(convertView.getResources().getColor(R.color.purple_700));
        questionView.setText(question);
        return questionView;
    }

    /**
     * Δημιουργεί ένα view για κάθε απάντηση που αντιστοιχεί σε μία συγκεκριμένη ερώτηση
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String answer = (String) getChild(groupPosition,childPosition);
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.faq_list_item,null);
        }
        TextView answerView = convertView.findViewById(R.id.answer);
        answerView.setTextColor(convertView.getResources().getColor(R.color.purple_500));
        answerView.setText(answer);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
