package paul.cipherresfeber.careerguidance.Teacher.MainFragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import paul.cipherresfeber.careerguidance.R;
import paul.cipherresfeber.careerguidance.Teacher.Adapters.QuestionPaperAdapter;
import paul.cipherresfeber.careerguidance.Teacher.CustomClasses.QuestionPaper;
import paul.cipherresfeber.careerguidance.Teacher.Fragments.AddQuestionPaperFragment;

public class NewQuestionPaper extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_new_qp, container, false);

        // reference to the views
        TextView textViewAddNewQuestionPaper = view.findViewById(R.id.txvAddNewQuestionPaper);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        DatabaseReference refQuestionPaper = FirebaseDatabase.getInstance()
                .getReference()
                .child("all_question_papers")
                .child("uid_1234") // TODO: replace with teacher uid
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

                // open a new fragment for adding new question papers
                AddQuestionPaperFragment fragment = new AddQuestionPaperFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_right);
                transaction.addToBackStack(null);
                transaction.add(R.id.frameLayout, fragment, "AddQuestionPaper").commit();


            }
        });

        return view;
    }

}
