package com.example.recruitai;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

public class FinalAnalysis extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;
    DatabaseReference mDatabaseReference;
    DatabaseReference det;
    String JobID;
    String userId;

    HashMap<String, Float> emotionMap = new HashMap<>();

    PieChart pieChart;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_analysis);

//        firebaseAuth= FirebaseAuth.getInstance();
//        user=firebaseAuth.getCurrentUser();
//        uid=user.getUid();
//
//        if (getIntent() != null) {
//            userId = getIntent().getStringExtra("UserID");
//        }
//
//        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Jobs").child(uid).child("Juser").child(userId);

        pieChart = findViewById(R.id.pie_chart);

        pieChartSetup();
        pieChart.spin(1500, 0, 180f, Easing.EasingOption.EaseInOutQuad);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void pieChartSetup() {

        //Data from Database
        ArrayList<PieEntry> allEmotionList = new ArrayList<>();


        emotionMap.put("Angry", (float) 9.82605);
        emotionMap.put("Disgusted", (float) 21.168673);
        emotionMap.put("Fearful", (float) 16.434626);
        emotionMap.put("Happy", (float) 12.15301);
        emotionMap.put("Neutral", (float) 19.672407);
        emotionMap.put("Sad", (float) 6.626781);
        emotionMap.put("Surprised", (float) 14.118446);

        emotionMap.forEach((emotion, value) -> allEmotionList.add(new PieEntry(value, emotion)));

        //Setting the Data to PieDataSet
        PieDataSet pieDataSet = new PieDataSet(allEmotionList, "");

        //Color Scheme
        final int[] MY_COLORS = {
                //Neutral
                Color.parseColor("#93F902"),
                //Disgusted
                Color.parseColor("#f9e502"),
                //Fearful
                Color.parseColor("#960902"),
                //Happy
                Color.parseColor("#088A23"),
                //Angry
                Color.parseColor("#FD2502"),
                //Sad
                Color.parseColor("#F902BC"),
                ///Surprised
                Color.parseColor("#02F9D3")
        };

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : MY_COLORS) colors.add(c);
        pieDataSet.setColors(colors);

//        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData pieData = new PieData(pieDataSet);

        //Inside PieChart Text Size and Color
        pieData.setValueTextSize(18);
        pieData.setValueTextColor(Color.parseColor("#FFFFFF"));


        pieChart.setData(pieData);
        pieChart.invalidate();
        pieChart.setTouchEnabled(false);

        pieChart.setCenterText("Audio Emotions");
        pieChart.setCenterTextSize(20);

        pieChart.setContentDescription("");
        pieChart.getDescription().setEnabled(false);

        pieChart.setDrawEntryLabels(false);
        pieChart.setEntryLabelTextSize(18);


//        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleRadius(75);


        Legend legend = pieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(12);
        legend.setFormSize(20);
        legend.setFormToTextSpace(2);
    }
}