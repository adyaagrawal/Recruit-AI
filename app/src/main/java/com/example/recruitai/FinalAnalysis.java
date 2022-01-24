package com.example.recruitai;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recruitai.Model.UserClass;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class FinalAnalysis extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;
    DatabaseReference mUserDatabaseReference;
    DatabaseReference det;
    String JobID;
    String userId;

    UserClass currentUserData;

    HashMap<String, String> emotionMap = new HashMap<>();

    PieChart pieChart;

    TextView nameView, phoneNumberView, resumeScoreView, confidenceScoreView, transcriptView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_analysis);

        nameView = findViewById(R.id.nameView);
        phoneNumberView = findViewById(R.id.phoneView);
        resumeScoreView = findViewById(R.id.ResumeScoreView);
        confidenceScoreView = findViewById(R.id.ConfidenceScoreViewView);

        pieChart = findViewById(R.id.pie_chart);
        transcriptView = findViewById(R.id.transcriptView);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        if (getIntent() != null) {
            userId = getIntent().getStringExtra("UserID");
        }

        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Jobs").child(uid).child("Juser").child(userId);
        mUserDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                currentUserData = snapshot.getValue(UserClass.class);
                assert currentUserData != null;
                nameView.setText(currentUserData.getName());
                phoneNumberView.setText(currentUserData.getPhone());
                resumeScoreView.setText(String.valueOf(currentUserData.getResume_score()));
                confidenceScoreView.setText(currentUserData.getConfidence_score());
                transcriptView.setText(currentUserData.getAudio_text());

                emotionMap.putAll(currentUserData.getEmotions());
                pieChartSetup();
                Log.d("FinalAnalysisEmotion", currentUserData.getEmotions().toString());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        pieChart.spin(1500, 0, 180f, Easing.EasingOption.EaseInOutQuad);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void pieChartSetup() {

        //Data from Database
        ArrayList<PieEntry> allEmotionList = new ArrayList<>();

        emotionMap.forEach((emotion, value) -> allEmotionList.add(new PieEntry(Float.parseFloat(value), emotion)));

        //Setting the Data to PieDataSet
        PieDataSet pieDataSet = new PieDataSet(allEmotionList, "");

        //Color Scheme
        final int[] MY_COLORS = {
                //Disgusted
                Color.parseColor("#f9e502"),
                //Happy
                Color.parseColor("#088A23"),
                //Sad
                Color.parseColor("#F902BC"),
                //Neutral
                Color.parseColor("#93F902"),
                //Angry
                Color.parseColor("#FD2502"),
                //Fearful
                Color.parseColor("#960902"),
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