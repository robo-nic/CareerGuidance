package paul.cipherresfeber.careerguidance.Student.MainFragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

import paul.cipherresfeber.careerguidance.CustomClasses.StudentAnswer;
import paul.cipherresfeber.careerguidance.R;

public class Dashboard extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_dashboard, container, false);


        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Please Wait");
        pd.setMessage("Fetching data");
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.show();

        final TextView textViewPreferredCareerChoice = view.findViewById(R.id.txvPreferredCareerChoice);
        final RadarChart radarChart = view.findViewById(R.id.radarChart);
        final BarChart lineChart  = view.findViewById(R.id.barChart);

        final ArrayList<StudentAnswer> answers = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("answer_sheet")
                .child("uid_student_1234") // todo uid
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                pd.cancel();

                if(answers.size() == 0){
                    Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                    return;
                }

                // else plot graphs
                String labels[] = {
                        "ACCOUNTING",
                        "ARCHITECTURE",
                        "BIOLOGY",
                        "BUSINESS",
                        "CIVIL",
                        "COMPUTER SCIENCE",
                        "CRIMINOLOGY",
                        "ELECTRICAL",
                        "FASHION",
                        "GEOGRAPHY",
                        "GENERAL KNOWLEDGE",
                        "GRAPHIC DESIGN",
                        "LAW",
                        "MATHEMATICS",
                        "MUSIC",
                        "PHYSICS"
                };

                String sLabels[] = {
                        "Acc.",
                        "Arc.",
                        "Bio.",
                        "Buss.",
                        "Civ.",
                        "Cse",
                        "Crime.",
                        "Ele.",
                        "Fas.",
                        "Geo.",
                        "Gk.",
                        "Graph.",
                        "Law",
                        "Maths.",
                        "Mus.",
                        "Phy."
                };

                ArrayList<RadarEntry> list = getDataPoints(answers, labels);

                RadarDataSet dataSet1 = new RadarDataSet(list, "Career Choice");
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

                // finding the subject with max value
                int maxVal = Integer.MIN_VALUE;
                int index = 0;
                for(int i=0; i<list.size(); i++){
                    if(maxVal < list.get(i).getValue()){
                        maxVal = (int) list.get(i).getValue();
                        index = i;
                    }
                }

                ArrayList<BarEntry> barEntries = new ArrayList<>();
                for(int i=0; i<list.size(); i++){
                    barEntries.add(new BarEntry(i,list.get(i).getValue()));
                }

                // finally plotting the bar graph
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
                BarDataSet lineDataSet = new BarDataSet(barEntries, "Preferred Career Choice");

                // customizing the line
                lineDataSet.setColor(Color.argb(100,255,0,0));
                lineDataSet.setValueTextSize(0);

                lineChart.setData(new BarData(lineDataSet));
                lineChart.animateY(500);
                lineChart.invalidate();


                textViewPreferredCareerChoice.setText(labels[index]);

            }
        }, 5*1000); // run after 5 seconds

        return view;
    }

    private ArrayList<RadarEntry> getDataPoints(ArrayList<StudentAnswer> answers, String[] labels){


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
