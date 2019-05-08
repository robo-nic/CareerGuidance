package paul.cipherresfeber.careerguidance.Student.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import paul.cipherresfeber.careerguidance.Constants.Extra;
import paul.cipherresfeber.careerguidance.Constants.Teacher;
import paul.cipherresfeber.careerguidance.R;
import paul.cipherresfeber.careerguidance.CustomClasses.QuestionPaper;
import paul.cipherresfeber.careerguidance.Student.Fragments.AttemptQuestionPaper;
import paul.cipherresfeber.careerguidance.Teacher.Fragments.AddQuestionFragment;
import paul.cipherresfeber.careerguidance.Teacher.Fragments.ViewQuestionPaper;

public class QuestionPaperAdapter extends RecyclerView.Adapter<QuestionPaperAdapter.QuestionPaperViewHolder> {

    private Context context;
    private ArrayList<QuestionPaper> list;
    private FragmentManager fragmentManager;

    // constructor for the PasswordAdapter class
    public QuestionPaperAdapter(Context context, ArrayList<QuestionPaper> list, FragmentManager fragmentManager){
        this.context = context;
        this.list = list;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public QuestionPaperAdapter.QuestionPaperViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student_question_paper, viewGroup,
                false);
        return new QuestionPaperAdapter.QuestionPaperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionPaperAdapter.QuestionPaperViewHolder questionPaperViewHolder, int i) {

        final QuestionPaper data = list.get(i);
        questionPaperViewHolder.textViewTeacherName.setText(data.getTeacherName());
        questionPaperViewHolder.textViewQuestionPaperName.setText(data.getQuestionPaperName());
        questionPaperViewHolder.textViewCreationDate.setText(data.getCreationDate());

        questionPaperViewHolder.cardViewParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // allow the student to attempt the question paper

                Bundle bundle = new Bundle();
                bundle.putString(Teacher.QUESTIONNAIRE_KEY, data.getQuestionsId());
                bundle.putString(Teacher.QUESTION_PAPER_NAME, data.getQuestionPaperName());
                bundle.putString(Teacher.TEACHER_NAME, data.getTeacherName());
                bundle.putString(Teacher.TIME_PER_QUESTION, data.getTimePerQuestion());
                bundle.putString(Teacher.DATE, data.getCreationDate());
                bundle.putString(Teacher.TOTAL_NUMBER_OF_QUESTIONS, data.getTotalNumberOfQuestions());

                AttemptQuestionPaper fragment = new AttemptQuestionPaper();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_right);
                transaction.addToBackStack(null);
                transaction.add(R.id.frameLayout, fragment, "AttemptQuestionPaper").commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // view holder class
    public class QuestionPaperViewHolder extends RecyclerView.ViewHolder{

        TextView textViewQuestionPaperName;
        TextView textViewTeacherName;
        TextView textViewCreationDate;
        CardView cardViewParentLayout;
        ImageView imageViewStatus;

        public QuestionPaperViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestionPaperName = itemView.findViewById(R.id.txvQuestionPaperName);
            textViewTeacherName = itemView.findViewById(R.id.txvTeacherName);
            textViewCreationDate = itemView.findViewById(R.id.txvDate);
            cardViewParentLayout = itemView.findViewById(R.id.parentLayout);
            imageViewStatus = itemView.findViewById(R.id.imvStatus);
        }
    }

}
