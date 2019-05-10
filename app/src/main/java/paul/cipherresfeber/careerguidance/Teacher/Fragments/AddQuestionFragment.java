package paul.cipherresfeber.careerguidance.Teacher.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import paul.cipherresfeber.careerguidance.Constants.Extra;
import paul.cipherresfeber.careerguidance.Constants.Teacher;
import paul.cipherresfeber.careerguidance.R;
import paul.cipherresfeber.careerguidance.Teacher.Adapters.QuestionAdapter;
import paul.cipherresfeber.careerguidance.CustomClasses.Question;
import paul.cipherresfeber.careerguidance.Teacher.TeacherActivity;

public class AddQuestionFragment extends Fragment {

    ArrayList<Question> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_question, container, false);

        // fetch already added questions from the questionnaireKey and show to the Recycler View
        final String questionnaireKey = getArguments().getString(Teacher.QUESTIONNAIRE_KEY);

        final DatabaseReference questionRef = FirebaseDatabase.getInstance().getReference()
                .child("questions")
                .child(questionnaireKey);

        list = new ArrayList<>();
        final QuestionAdapter adapter = new QuestionAdapter(getContext(),list,getFragmentManager());

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        final TextView textViewAvailableQuestions = view.findViewById(R.id.txvAvailableQuestions);
        Button buttonPublishQuestionPaper = view.findViewById(R.id.btnPublishQuestionPaper);

        questionRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                list.add(dataSnapshot.getValue(Question.class));
                adapter.notifyDataSetChanged();

                textViewAvailableQuestions.setText("Available Questions: " + list.size());

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

        // reference to the question addition views
        Button buttonAddQuestion = view.findViewById(R.id.btnAddQuestion);

        final Spinner spinnerCategory = view.findViewById(R.id.spinnerCategory);
        final Spinner spinnerCorrectOption = view.findViewById(R.id.spinnerCorrectOption);

        final EditText editTextQuestionTitle = view.findViewById(R.id.etQuestionTitle);
        final EditText editTextOption1 = view.findViewById(R.id.etOption1);
        final EditText editTextOption2 = view.findViewById(R.id.etOption2);
        final EditText editTextOption3 = view.findViewById(R.id.etOption3);
        final EditText editTextOption4 = view.findViewById(R.id.etOption4);

        // set prompt for the spinner views
        spinnerCategory.setPrompt("Question Category");
        spinnerCorrectOption.setPrompt("Correct Options");

        buttonAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog pd = new ProgressDialog(getContext());
                pd.setTitle("Please Wait");
                pd.setMessage("Uploading Question Data");
                pd.setCanceledOnTouchOutside(false);
                pd.setCancelable(false);

                // get values from the views
                String questionTitle = editTextQuestionTitle.getText().toString().trim();
                String option1 = editTextOption1.getText().toString().trim();
                String option2 = editTextOption2.getText().toString().trim();
                String option3 = editTextOption3.getText().toString().trim();
                String option4 = editTextOption4.getText().toString().trim();

                String category = spinnerCategory.getSelectedItem().toString();
                String correctOption = spinnerCorrectOption.getSelectedItem().toString();

                // verifying entered details
                if(questionTitle.length() < 5){
                    editTextQuestionTitle.setError("At least 5 chars");
                    editTextQuestionTitle.requestFocus();
                    return;
                }

                if(option1.isEmpty()){
                    editTextOption1.setError("Can't be empty");
                    editTextOption1.requestFocus();
                    return;
                }

                if(option2.isEmpty()){
                    editTextOption2.setError("Can't be empty");
                    editTextOption2.requestFocus();
                    return;
                }

                if(option3.isEmpty()){
                    editTextOption3.setError("Can't be empty");
                    editTextOption3.requestFocus();
                    return;
                }

                if(option4.isEmpty()){
                    editTextOption4.setError("Can't be empty");
                    editTextOption4.requestFocus();
                    return;
                }


                if(correctOption.equals(Teacher.SPINNER_PROMPT_CORRECT_OPTION)){
                    Toast.makeText(getContext(),
                            "Choose a correct option", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(category.equals(Teacher.SPINNER_PROMPT_QUESTION_CATEGORY)){
                    Toast.makeText(getContext(),
                            "Choose a question Category", Toast.LENGTH_SHORT).show();
                    return;
                }

                pd.show();

                questionRef.push().setValue(new Question(
                        questionTitle,
                        option1,
                        option2,
                        option3,
                        option4,
                        category,
                        correctOption
                )).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.cancel();
                        Toast.makeText(getContext(),
                                "Upload Success!", Toast.LENGTH_SHORT).show();

                        // finally reset all the values from the view
                        editTextQuestionTitle.setText("");
                        editTextOption1.setText("");
                        editTextOption2.setText("");
                        editTextOption3.setText("");
                        editTextOption4.setText("");
                        spinnerCategory.setSelection(0);
                        spinnerCorrectOption.setSelection(0);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.cancel();
                        Toast.makeText(getContext(),
                                "Question could not be uploaded!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        // publish the question paper so that it becomes available to the students
        buttonPublishQuestionPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = "Your question paper will be available across all student's devices. " +
                        "And you won't be able to edit any questions either. " +
                        "Are you sure you want to continue?";

                new AlertDialog.Builder(getContext())
                        .setCancelable(false)
                        .setTitle("Publish Question Paper?")
                        .setMessage(message)
                        .setIcon(R.drawable.ic_publish)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                publishQuestionPaper(questionnaireKey);
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

            }
        });

        return view;
    }

    // method for updating question paper data to make it public
    private void publishQuestionPaper(String key){

        // initialize progressDialog
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Please Wait");
        pd.setMessage("Publishing Question Paper");
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.show();

        DatabaseReference questionPaperRef = FirebaseDatabase.getInstance().getReference()
                .child("all_question_papers")
                .child(key);

        questionPaperRef.child("totalNumOfQuestions").setValue(String.valueOf(list.size()));
        questionPaperRef.child("isCompleted").setValue(Extra.YES)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.cancel();
                        Toast.makeText(getContext(),
                                "Published!", Toast.LENGTH_SHORT).show();

                        // finally close the fragment
                        startActivity(new Intent(getContext(), TeacherActivity.class));
                        getActivity().finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.cancel();
                        Toast.makeText(getContext(),
                                "Could not publish", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
