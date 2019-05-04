package paul.cipherresfeber.careerguidance.Teacher.Fragments;

import android.app.ProgressDialog;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

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

        final EditText editTextQuestionPaperName = view.findViewById(R.id.etQuestionPaperName);
        final EditText editTextTimePerQuestion = view.findViewById(R.id.etTimePerQuestion);
        final EditText editTextTeacherName = view.findViewById(R.id.etTeacherName);

        view.findViewById(R.id.btnCreateNewQuestionPaper).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog pd = new ProgressDialog(getContext());
                pd.setTitle("Please Wait");
                pd.setMessage("Creating new Question Paper");
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);

                // verify input data
                String questionPaperName = editTextQuestionPaperName.getText().toString().trim();
                String timePerQuestion = editTextTimePerQuestion.getText().toString().trim();
                String teacherName = editTextTeacherName.getText().toString().trim();

                if(questionPaperName.length() < 5 || questionPaperName.length() > 30){
                    editTextQuestionPaperName.setError("5 - 30 chars only!");
                    editTextQuestionPaperName.requestFocus();
                    return;
                }

                if(timePerQuestion.isEmpty()){
                    editTextTimePerQuestion.setError("Enter time in minutes!");
                    editTextTimePerQuestion.requestFocus();
                    return;
                }

                if(teacherName.isEmpty()){
                    editTextTeacherName.setError("Can't be empty!");
                    editTextTeacherName.requestFocus();
                    return;
                }

                pd.show();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                        .child("all_question_papers")
                        .child("uid_1234") // TODO: teachers uid
                        .child("question_paper")
                        .push();

                String questionPaperKey = reference.getKey();
                String questionCreationDate = new SimpleDateFormat("dd MM, YYYY").format(new Date());

                reference.setValue(
                  new QuestionPaper(
                          questionPaperName,
                          timePerQuestion,
                          teacherName,
                          questionCreationDate,
                          questionPaperKey
                  )
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.cancel();
                        Toast.makeText(getContext(),
                                "Question paper created!", Toast.LENGTH_SHORT).show();

                        // close the fragment
                        getActivity().getSupportFragmentManager().popBackStackImmediate();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.cancel();
                        Toast.makeText(getContext(),
                                "Failed to create question paper!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



        return view;
    }


}
