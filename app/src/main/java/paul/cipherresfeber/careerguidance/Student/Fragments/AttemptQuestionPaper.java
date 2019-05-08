package paul.cipherresfeber.careerguidance.Student.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import paul.cipherresfeber.careerguidance.Constants.Teacher;
import paul.cipherresfeber.careerguidance.CustomClasses.Question;
import paul.cipherresfeber.careerguidance.R;

public class AttemptQuestionPaper extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attempt_question_paper, container, false);

        final TextView textView = view.findViewById(R.id.textView);

        Bundle bundle = getArguments();
        String questionKey = bundle.getString(Teacher.QUESTIONNAIRE_KEY);
        String questionPaperName = bundle.getString(Teacher.QUESTION_PAPER_NAME);
        String teacherName = bundle.getString(Teacher.TEACHER_NAME);
        String timePerQuestion = bundle.getString(Teacher.TIME_PER_QUESTION);
        String date = bundle.getString(Teacher.DATE);
        String totalNumberOfQuestions = bundle.getString(Teacher.TOTAL_NUMBER_OF_QUESTIONS);

        // get total allotted time in minutes
        int totalTime = Integer.parseInt(timePerQuestion) * Integer.parseInt(totalNumberOfQuestions);
        long totalTimeInMillis = totalTime * 60 * 1000;

        FirebaseDatabase.getInstance().getReference()
                .child("questions")
                .child(questionKey)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {



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


        totalTimeInMillis = 10*1000; // todo remove this line
        // the following code shows the time left to the user and closes the fragment on completion
        new CountDownTimer(totalTimeInMillis, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished/1000;
                // update the view
                textView.setText("Time Remaining: " + secondsLeft/60 + ":" + secondsLeft%60);
            }

            @Override
            public void onFinish() {
                // show alert dialog box informing that text has finished

                String message = "For analysis of this test, view the 'Past Results' section.";

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
