package paul.cipherresfeber.careerguidance.Student.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HeaderViewListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import paul.cipherresfeber.careerguidance.Constants.Extra;
import paul.cipherresfeber.careerguidance.Constants.Teacher;
import paul.cipherresfeber.careerguidance.CustomClasses.Question;
import paul.cipherresfeber.careerguidance.CustomClasses.QuestionPaper;
import paul.cipherresfeber.careerguidance.CustomClasses.StudentAnswer;
import paul.cipherresfeber.careerguidance.R;
import paul.cipherresfeber.careerguidance.Student.Adapters.QuestionAdapter;

public class AttemptQuestionPaper extends Fragment {

    private int currentQuestion;
    private String totalNumberOfQuestions;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attempt_question_paper, container, false);

        currentQuestion = 1;

        final ArrayList<Question> questionList = new ArrayList<>();

        // reference to the views
        TextView textViewQuestionPaperName = view.findViewById(R.id.txvQuestionPaperName);
        final TextView textViewTeacherName = view.findViewById(R.id.txvTeacherName);
        TextView textViewCreationDate = view.findViewById(R.id.txvCreationDate);
        final TextView textViewTimeLeft = view.findViewById(R.id.txvTimeLeft);
        final TextView textViewQuestionNumber = view.findViewById(R.id.txvQuestionNumber);

        final Bundle bundle = getArguments();
        final String questionKey = bundle.getString(Teacher.QUESTIONNAIRE_KEY);
        String questionPaperName = bundle.getString(Teacher.QUESTION_PAPER_NAME);
        String teacherName = bundle.getString(Teacher.TEACHER_NAME);
        String timePerQuestion = bundle.getString(Teacher.TIME_PER_QUESTION);
        String date = bundle.getString(Teacher.DATE);
        totalNumberOfQuestions = bundle.getString(Teacher.TOTAL_NUMBER_OF_QUESTIONS);

        // list for saving answer -- creating n vacant spaces
        final ArrayList<StudentAnswer> answers = new ArrayList<>();
        for(int i=0; i<Integer.parseInt(totalNumberOfQuestions); i++){
            answers.add(new StudentAnswer());
        }

        final Button buttonOption1 = view.findViewById(R.id.btnOption1);
        final Button buttonOption2 = view.findViewById(R.id.btnOption2);
        final Button buttonOption3 = view.findViewById(R.id.btnOption3);
        final Button buttonOption4 = view.findViewById(R.id.btnOption4);

        buttonOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = currentQuestion - 1;
                answers.get(p)
                        .setValue(questionList.get(p).getCategory(),
                                questionList.get(p).getCorrectOption(),
                                Extra.OPTION_1,Extra.YES);

                buttonOption1.setBackgroundResource(R.drawable.rounded_green_background_button);
                buttonOption2.setBackgroundResource(R.drawable.rounded_red_button_background);
                buttonOption3.setBackgroundResource(R.drawable.rounded_red_button_background);
                buttonOption4.setBackgroundResource(R.drawable.rounded_red_button_background);

            }
        });

        buttonOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = currentQuestion - 1;
                answers.get(p)
                        .setValue(questionList.get(p).getCategory(),
                                questionList.get(p).getCorrectOption(),
                                Extra.OPTION_2,Extra.YES);

                buttonOption2.setBackgroundResource(R.drawable.rounded_green_background_button);
                buttonOption1.setBackgroundResource(R.drawable.rounded_red_button_background);
                buttonOption3.setBackgroundResource(R.drawable.rounded_red_button_background);
                buttonOption4.setBackgroundResource(R.drawable.rounded_red_button_background);

            }
        });

        buttonOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = currentQuestion - 1;
                answers.get(p)
                        .setValue(questionList.get(p).getCategory(),
                                questionList.get(p).getCorrectOption(),
                                Extra.OPTION_3,Extra.YES);

                buttonOption3.setBackgroundResource(R.drawable.rounded_green_background_button);
                buttonOption2.setBackgroundResource(R.drawable.rounded_red_button_background);
                buttonOption1.setBackgroundResource(R.drawable.rounded_red_button_background);
                buttonOption4.setBackgroundResource(R.drawable.rounded_red_button_background);

            }
        });

        buttonOption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = currentQuestion - 1;
                answers.get(p)
                        .setValue(questionList.get(p).getCategory(),
                                questionList.get(p).getCorrectOption(),
                                Extra.OPTION_4,Extra.YES);

                buttonOption4.setBackgroundResource(R.drawable.rounded_green_background_button);
                buttonOption2.setBackgroundResource(R.drawable.rounded_red_button_background);
                buttonOption3.setBackgroundResource(R.drawable.rounded_red_button_background);
                buttonOption1.setBackgroundResource(R.drawable.rounded_red_button_background);

            }
        });

        textViewQuestionPaperName.setText(questionPaperName);
        textViewTeacherName.setText(teacherName);
        textViewCreationDate.setText(date);

        // get total allotted time in minutes
        int totalTime = Integer.parseInt(timePerQuestion) * Integer.parseInt(totalNumberOfQuestions);
        long totalTimeInMillis = totalTime * 60 * 1000 + (15*1000); // extra 15 seconds

        textViewQuestionNumber.setText("1 / " + totalNumberOfQuestions);

        final QuestionAdapter adapter = new QuestionAdapter(getContext(), questionList, getFragmentManager());

        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        // disable scrolling by sliding ----- stealing all touches
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        final Button buttonRight = view.findViewById(R.id.btnRight);
        final Button buttonLeft = view.findViewById(R.id.btnLeft);
        buttonLeft.setEnabled(false);

        buttonRight.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {


                // following block is executed when the test ends
                if(currentQuestion == Integer.parseInt(totalNumberOfQuestions)){
                    String message = "Are you sure you want to finish the test?";

                    new AlertDialog.Builder(getContext())
                            .setCancelable(false)
                            .setTitle("Finish Test?")
                            .setMessage(message)
                            .setIcon(R.drawable.ic_correct)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                    final ProgressDialog pd = new ProgressDialog(getContext());
                                    pd.setTitle("Please Wait");
                                    pd.setMessage("Submitting Answer Sheet");
                                    pd.setCanceledOnTouchOutside(false);
                                    pd.setCancelable(false);
                                    pd.show();

                                    // upload the entire answer sheet to the database
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                                            .child("answer_sheet")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("first_test");

                                    ref.setValue(answers)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    pd.cancel();

                                                    String message = "For analysis of this test, view the 'Dashboard'.";

                                                    new AlertDialog.Builder(getContext())
                                                            .setCancelable(false)
                                                            .setTitle("Congrats! Test Completed")
                                                            .setMessage(message)
                                                            .setIcon(R.drawable.ic_correct)
                                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                                    // close the fragment
                                                                    getFragmentManager().popBackStackImmediate();
                                                                }})
                                                            .show();

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    pd.cancel();
                                                    Toast.makeText(getContext(),
                                                            "Something went wrong!", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }})
                            .setNegativeButton(android.R.string.no, null)
                            .show();

                    return;
                }

                int nextPos = ((LinearLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager()))
                        .findFirstVisibleItemPosition() + 1;

                if(answers.get(nextPos).getAnswered().equals(Extra.YES)){
                    // the question has been answered already
                    String option = answers.get(nextPos).getUserOption();

                    if(option.equals(Extra.OPTION_1)){
                        buttonOption1.setBackgroundResource(R.drawable.rounded_green_background_button);
                        buttonOption2.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption3.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption4.setBackgroundResource(R.drawable.rounded_red_button_background);
                    } else if(option.equals(Extra.OPTION_2)){
                        buttonOption1.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption2.setBackgroundResource(R.drawable.rounded_green_background_button);
                        buttonOption3.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption4.setBackgroundResource(R.drawable.rounded_red_button_background);
                    } else if(option.equals(Extra.OPTION_3)){
                        buttonOption1.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption2.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption3.setBackgroundResource(R.drawable.rounded_green_background_button);
                        buttonOption4.setBackgroundResource(R.drawable.rounded_red_button_background);
                    } else if(option.equals(Extra.OPTION_4)){
                        buttonOption1.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption2.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption3.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption4.setBackgroundResource(R.drawable.rounded_green_background_button);
                    }

                } else{
                    buttonOption1.setBackgroundResource(R.drawable.rounded_red_button_background);
                    buttonOption2.setBackgroundResource(R.drawable.rounded_red_button_background);
                    buttonOption3.setBackgroundResource(R.drawable.rounded_red_button_background);
                    buttonOption4.setBackgroundResource(R.drawable.rounded_red_button_background);
                }


                buttonLeft.setEnabled(true);
                recyclerView.smoothScrollToPosition(nextPos);
                textViewQuestionNumber.setText((nextPos + 1) + " / " + totalNumberOfQuestions);
                currentQuestion = nextPos + 1;
            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int nextPos = ((LinearLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager()))
                        .findFirstVisibleItemPosition() - 1;

                if(nextPos == 0){
                    v.setEnabled(false);
                }


                if(answers.get(nextPos).getAnswered().equals(Extra.YES)){
                    // the question has been answered already
                    String option = answers.get(nextPos).getUserOption();

                    if(option.equals(Extra.OPTION_1)){
                        buttonOption1.setBackgroundResource(R.drawable.rounded_green_background_button);
                        buttonOption2.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption3.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption4.setBackgroundResource(R.drawable.rounded_red_button_background);
                    } else if(option.equals(Extra.OPTION_2)){
                        buttonOption1.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption2.setBackgroundResource(R.drawable.rounded_green_background_button);
                        buttonOption3.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption4.setBackgroundResource(R.drawable.rounded_red_button_background);
                    } else if(option.equals(Extra.OPTION_3)){
                        buttonOption1.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption2.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption3.setBackgroundResource(R.drawable.rounded_green_background_button);
                        buttonOption4.setBackgroundResource(R.drawable.rounded_red_button_background);
                    } else if(option.equals(Extra.OPTION_4)){
                        buttonOption1.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption2.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption3.setBackgroundResource(R.drawable.rounded_red_button_background);
                        buttonOption4.setBackgroundResource(R.drawable.rounded_green_background_button);
                    }

                } else{
                    buttonOption1.setBackgroundResource(R.drawable.rounded_red_button_background);
                    buttonOption2.setBackgroundResource(R.drawable.rounded_red_button_background);
                    buttonOption3.setBackgroundResource(R.drawable.rounded_red_button_background);
                    buttonOption4.setBackgroundResource(R.drawable.rounded_red_button_background);
                }


                buttonRight.setEnabled(true);
                recyclerView.smoothScrollToPosition(nextPos);
                textViewQuestionNumber.setText((nextPos + 1) + " / " + totalNumberOfQuestions);
                currentQuestion = nextPos + 1;
            }
        });


        // fetch the questions
        FirebaseDatabase.getInstance().getReference()
                .child("questions")
                .child(questionKey)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        questionList.add(dataSnapshot.getValue(Question.class));
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


        // the following code shows the time left to the user and closes the fragment on completion
        new CountDownTimer(totalTimeInMillis, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                long secondsUntilFinished = millisUntilFinished/1000;

                long hoursLeft = secondsUntilFinished/3600;
                long minutesLeft = secondsUntilFinished/60 % 60;
                long secondsLeft = secondsUntilFinished%60;

                String hl = String.valueOf(hoursLeft);
                if(hl.length() == 1){
                    hl = "0" + hl;
                }

                String ml = String.valueOf(minutesLeft);
                if(ml.length() == 1){
                    ml = "0" + ml;
                }

                String sl = String.valueOf(secondsLeft);
                if(sl.length() == 1){
                    sl = "0" + sl;
                }

                // update the view
                textViewTimeLeft.setText("Time Remaining: " + hl + ":" + ml + ":" + sl);
            }

            @Override
            public void onFinish() {
                // show alert dialog box informing that text has finished

                String message = "For analysis of this test, view the 'Dashboard'.";

                new AlertDialog.Builder(getContext())
                        .setCancelable(false)
                        .setTitle("Congrats! Test Completed")
                        .setMessage(message)
                        .setIcon(R.drawable.ic_correct)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // close the fragment
                                getFragmentManager().popBackStackImmediate();
                            }})
                        .show();
            }
        }.start();

        return view;
    }
}
