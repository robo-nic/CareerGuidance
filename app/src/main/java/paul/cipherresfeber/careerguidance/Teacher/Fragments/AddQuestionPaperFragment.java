package paul.cipherresfeber.careerguidance.Teacher.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import paul.cipherresfeber.careerguidance.R;
import paul.cipherresfeber.careerguidance.Teacher.CustomClasses.QuestionPaper;

public class AddQuestionPaperFragment extends Fragment {

    public AddQuestionPaperFragment(){
        // empty constructor required
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_question_paper, container, false);

        final EditText name = view.findViewById(R.id.name);
        final EditText time = view.findViewById(R.id.time);
        final EditText teacherName = view.findViewById(R.id.teacherName);

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                        .child("all_question_papers")
                        .child("uid_1234")
                        .child("question_paper")
                        .push();

                String questionPaperKey = reference.getKey();

                reference.setValue(
                  new QuestionPaper(
                          name.getText().toString().trim(),
                          time.getText().toString().trim(),
                          teacherName.getText().toString().trim(),
                          questionPaperKey
                  )
                );

            }
        });



        return view;
    }


}
