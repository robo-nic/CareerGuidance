package paul.cipherresfeber.careerguidance.Teacher.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import paul.cipherresfeber.careerguidance.Constants.Teacher;
import paul.cipherresfeber.careerguidance.R;
import paul.cipherresfeber.careerguidance.Teacher.Adapters.QuestionAdapter;
import paul.cipherresfeber.careerguidance.CustomClasses.Question;

public class ViewQuestionPaper extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_question_paper, container, false);

        final int eachQuestionTime;

        // reference to the views
        TextView textViewQuestionPaperName = view.findViewById(R.id.txvQuestionPaperName);
        final TextView textViewTeacherName = view.findViewById(R.id.txvTeacherName);
        TextView textViewCreationDate = view.findViewById(R.id.txvCreationDate);
        final TextView textViewTotalTime = view.findViewById(R.id.txvTotalTime);

        Bundle bundle = getArguments();
        String questionKey = bundle.getString(Teacher.QUESTIONNAIRE_KEY);
        String questionPaperName = bundle.getString(Teacher.QUESTION_PAPER_NAME);
        String teacherName = bundle.getString(Teacher.TEACHER_NAME);
        String timePerQuestion = bundle.getString(Teacher.TIME_PER_QUESTION);
        String date = bundle.getString(Teacher.DATE);

        textViewQuestionPaperName.setText(questionPaperName);
        textViewTeacherName.setText(teacherName);
        textViewCreationDate.setText(date);

        // get the time per question
        eachQuestionTime = Integer.parseInt(timePerQuestion);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final ArrayList<Question> list = new ArrayList<>();
        final QuestionAdapter adapter = new QuestionAdapter(getContext(),list,getFragmentManager());
        recyclerView.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference()
                .child("questions")
                .child(questionKey)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        list.add(dataSnapshot.getValue(Question.class));
                        adapter.notifyDataSetChanged();

                        String value =
                                (eachQuestionTime*list.size()) + " minutes (" + list.size() + "x" + eachQuestionTime + " min)";

                        // show the total time to the user
                        textViewTotalTime.setText(value);

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




        return view;
    }
}
