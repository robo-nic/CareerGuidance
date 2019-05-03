package paul.cipherresfeber.careerguidance.Teacher.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import paul.cipherresfeber.careerguidance.R;
import paul.cipherresfeber.careerguidance.Teacher.Adapters.QuestionAdapter;
import paul.cipherresfeber.careerguidance.Teacher.CustomClasses.Question;

public class AddQuestionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_question, container, false);

        // fetch already added questions from the questionnaireKey and show to the Recycler View
        String questionnaireKey = getArguments().getString("QuestionnaireKey");

        DatabaseReference questionRef = FirebaseDatabase.getInstance().getReference()
                .child("questions")
                .child(questionnaireKey);

        final ArrayList<Question> list = new ArrayList<>();
        final QuestionAdapter adapter = new QuestionAdapter(getContext(),list,getFragmentManager());

        questionRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                list.add(dataSnapshot.getValue(Question.class));
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


        // now following code handles addition of new question to the current selected question paper





        return view;
    }
}
