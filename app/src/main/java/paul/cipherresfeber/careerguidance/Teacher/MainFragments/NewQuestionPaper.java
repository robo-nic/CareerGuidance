package paul.cipherresfeber.careerguidance.Teacher.MainFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import paul.cipherresfeber.careerguidance.R;
import paul.cipherresfeber.careerguidance.Teacher.Adapters.QuestionPaperAdapter;
import paul.cipherresfeber.careerguidance.Teacher.CustomClasses.QuestionPaper;

public class NewQuestionPaper extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_new_qp, container, false);

        // reference to the views
        FrameLayout frameLayout = view.findViewById(R.id.frameLayout);
        TextView textViewAddNewQuestionPaper = view.findViewById(R.id.txvAddNewQuestionPaper);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);


        // TODO: update the document ids
        DatabaseReference refQuestionPaper = FirebaseDatabase.getInstance()
                .getReference()
                .child("all_question_papers")
                .child("uid_1234")
                .child("question_paper");

        final ArrayList<QuestionPaper> questionPaperList = new ArrayList<>();
        final QuestionPaperAdapter adapter =
                new QuestionPaperAdapter(getContext(),questionPaperList, getFragmentManager());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        refQuestionPaper.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                questionPaperList.add(dataSnapshot.getValue(QuestionPaper.class));
                adapter.notifyDataSetChanged();
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

        textViewAddNewQuestionPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: show the new question addition fragment

            }
        });

        return view;
    }
}
