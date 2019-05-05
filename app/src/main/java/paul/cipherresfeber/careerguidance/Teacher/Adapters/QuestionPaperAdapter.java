package paul.cipherresfeber.careerguidance.Teacher.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;

import paul.cipherresfeber.careerguidance.Constants.Extra;
import paul.cipherresfeber.careerguidance.R;
import paul.cipherresfeber.careerguidance.Teacher.CustomClasses.QuestionPaper;
import paul.cipherresfeber.careerguidance.Teacher.Fragments.AddQuestionFragment;
import paul.cipherresfeber.careerguidance.Teacher.Fragments.AddQuestionPaperFragment;
import paul.cipherresfeber.careerguidance.Teacher.Fragments.ViewQuestionPaper;

public class QuestionPaperAdapter extends RecyclerView.Adapter<QuestionPaperAdapter.QuestionPaperViewHolder> {

    Context context;
    ArrayList<QuestionPaper> list;
    FragmentManager fragmentManager;

    // constructor for the PasswordAdapter class
    public QuestionPaperAdapter(Context context, ArrayList<QuestionPaper> list, FragmentManager fragmentManager){
        this.context = context;
        this.list = list;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public QuestionPaperViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question_paper, viewGroup,
                false);
        return new QuestionPaperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionPaperViewHolder questionPaperViewHolder, int i) {

        final QuestionPaper data = list.get(i);
        questionPaperViewHolder.textViewTeacherName.setText(data.getTeacherName());
        questionPaperViewHolder.textViewQuestionPaperName.setText(data.getQuestionPaperName());
        questionPaperViewHolder.textViewCreationDate.setText(data.getCreationDate());

        if(data.getIsCompleted().equals(Extra.NO)){
            questionPaperViewHolder.textViewQuestionPaperStatus.setText("Not Published");
            questionPaperViewHolder.relativeLayout
                    .setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        else {
            questionPaperViewHolder.textViewQuestionPaperStatus.setText("Published");
            questionPaperViewHolder.relativeLayout
                    .setBackgroundColor(context.getResources().getColor(R.color.lightGreen));
        }

        questionPaperViewHolder.cardViewParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(data.getIsCompleted().equals(Extra.NO)){

                    // allow editing of questions

                    Bundle bundle = new Bundle();
                    bundle.putString("QuestionnaireKey", data.getQuestionsId());

                    AddQuestionFragment fragment = new AddQuestionFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                            R.anim.enter_from_right, R.anim.exit_to_right);
                    transaction.addToBackStack(null);
                    transaction.add(R.id.frameLayout, fragment, "AddQuestion").commit();

                } else{
                    // open an fragment for viewing the questions

                    Bundle bundle = new Bundle();
                    bundle.putString("QuestionnaireKey", data.getQuestionsId());

                    ViewQuestionPaper fragment = new ViewQuestionPaper();
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                            R.anim.enter_from_right, R.anim.exit_to_right);
                    transaction
                            .addToBackStack(null)
                            .add(R.id.frameLayout, fragment, "ViewQuestionPaper")
                            .commit();

                }

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
        TextView textViewQuestionPaperStatus;
        CardView cardViewParentLayout;
        RelativeLayout relativeLayout;

        public QuestionPaperViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestionPaperName = itemView.findViewById(R.id.txvQuestionPaperName);
            textViewTeacherName = itemView.findViewById(R.id.txvTeacherName);
            textViewCreationDate = itemView.findViewById(R.id.txvDate);
            cardViewParentLayout = itemView.findViewById(R.id.parentLayout);
            textViewQuestionPaperStatus = itemView.findViewById(R.id.txvQuestionPaperStatus);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }

}
