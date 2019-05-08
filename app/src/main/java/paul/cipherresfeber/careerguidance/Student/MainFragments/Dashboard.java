package paul.cipherresfeber.careerguidance.Student.MainFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.RadarChart;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import paul.cipherresfeber.careerguidance.CustomClasses.StudentAnswer;
import paul.cipherresfeber.careerguidance.R;

public class Dashboard extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_dashboard, container, false);

        TextView textViewPreferredCareerChoice = view.findViewById(R.id.txvPreferredCareerChoice);
        RadarChart radarChart = view.findViewById(R.id.radarChart);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // todo: fetch stats of user from database -- display the graph and table
        // todo: show preferred career choice

        return view;
    }
}
