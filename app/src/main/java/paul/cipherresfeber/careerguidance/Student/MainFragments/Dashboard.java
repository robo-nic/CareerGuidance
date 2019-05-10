package paul.cipherresfeber.careerguidance.Student.MainFragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import paul.cipherresfeber.careerguidance.Constants.Extra;
import paul.cipherresfeber.careerguidance.CustomClasses.StudentAnswer;
import paul.cipherresfeber.careerguidance.R;

import static paul.cipherresfeber.careerguidance.Constants.Extra.labels;
import static paul.cipherresfeber.careerguidance.Constants.Extra.sLabels;

public class Dashboard extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_student_dashboard, container, false);

        final TextView textViewPreferredCareerChoice = view.findViewById(R.id.txvPreferredCareerChoice);
        final ArrayList<StudentAnswer> answers = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("answer_sheet")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("first_test");

       reference.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               answers.add(dataSnapshot.getValue(StudentAnswer.class));
           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

       final ProgressDialog pd = new ProgressDialog(getContext());
       pd.setTitle("Please Wait");
       pd.setMessage("Fetching data");
       pd.setCanceledOnTouchOutside(false);
       pd.setCancelable(false);

       // count down 5 seconds and check interval in every 1/1000th of a sec
       new CountDownTimer(5000,1){
           @Override
           public void onTick(long millisUntilFinished) {

               // wait a little before showing the progress dialog
               if(5000 - millisUntilFinished > 300){
                   pd.show();
               }

               if(!answers.isEmpty()){
                   pd.cancel();
                   showGraphs(view, answers, textViewPreferredCareerChoice);
                   cancel();
               }
           }
           @Override
           public void onFinish() {
               Toast.makeText(getContext(),
                       "No Data Available", Toast.LENGTH_SHORT).show();
               pd.cancel();
           }
       }.start();

        return view;
    }

    // utility method for plotting the graphs
    private void showGraphs(View view, ArrayList<StudentAnswer> answers, TextView textViewPreferredCareerChoice){
        final RadarChart radarChart = view.findViewById(R.id.radarChart);
        final BarChart lineChart  = view.findViewById(R.id.barChart);

        if(answers.size() == 0){
            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
            return;
        }


        // plotting the radar chart
        ArrayList<RadarEntry> list = getDataPoints(answers);
        RadarDataSet dataSet1 = new RadarDataSet(list, "Marks Distribution");
        dataSet1.setColor(Color.RED);
        dataSet1.setLineWidth(0f);
        dataSet1.setDrawFilled(true);
        dataSet1.setFillColor(Color.argb(50,255,0,0));
        RadarData radarData = new RadarData();
        radarData.addDataSet(dataSet1);
        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(sLabels));
        Description description = new Description();
        description.setText("");
        radarChart.setDescription(description);
        radarChart.setData(radarData);
        radarChart.animateY(500);
        radarChart.invalidate();


        // plotting the bar chart
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for(int i=0; i<list.size(); i++){
            barEntries.add(new BarEntry(i,list.get(i).getValue()));
        }
        // customizing the graph
        Description d = new Description();
        description.setText("");
        lineChart.setDescription(d);
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(sLabels));
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.setPinchZoom(true);
        lineChart.setScaleMinima(2,1);
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(true);
        lineChart.setBorderWidth(2);
        // data points
        BarDataSet lineDataSet = new BarDataSet(barEntries, "Marks Distribution");
        // customizing the line
        lineDataSet.setColor(Color.argb(100,255,0,0));
        lineDataSet.setValueTextSize(0);
        lineChart.setData(new BarData(lineDataSet));
        lineChart.animateY(500);
        lineChart.invalidate();

        // finding the subject with max value
        int maxVal = Integer.MIN_VALUE;
        for(int i=0; i<list.size(); i++){
            if(maxVal < list.get(i).getValue()){
                maxVal = (int) list.get(i).getValue();
            }
        }

        if(maxVal == 0){
            // then the student is good for nothing
            textViewPreferredCareerChoice.setText("GOOD FOR NOTHING");
        } else{
            // otherwise show all the topics the student is good at
            StringBuilder subjects = new StringBuilder("");

            for(int i=0; i<list.size(); i++){
                if(maxVal == list.get(i).getValue()){
                    subjects.append(labels[i]).append("\n");
                }
            }

            // finally display all the topics
            textViewPreferredCareerChoice.setText(subjects.toString().trim());
        }

    }

    // utility method for preparing the marks data to be plotted as graph
    private ArrayList<RadarEntry> getDataPoints(ArrayList<StudentAnswer> answers){


        int[] marks = new int[labels.length];
        for(int i=0; i<labels.length; i++){
            marks[i] = 0;
        }

        for(int i=0; i<labels.length; i++){
            for(int j=0; j<answers.size(); j++){
                StudentAnswer sa = answers.get(j);
                if(labels[i].equals(sa.getCategory())){
                    if(sa.getCorrectOption().equals(sa.getUserOption())){
                        // if ans is correct
                        marks[i]++;
                    }
                }
            }
        }

        ArrayList<RadarEntry> list = new ArrayList<>();

        for(int i=0;i<marks.length;i++){
            list.add(new RadarEntry(marks[i]));
        }

        return list;
    }

}
