package paul.cipherresfeber.careerguidance.Teacher.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import paul.cipherresfeber.careerguidance.R;
import paul.cipherresfeber.careerguidance.Teacher.CustomClasses.QuestionPaper;

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

        QuestionPaper data = list.get(i);
        questionPaperViewHolder.teacherName.setText(data.getTeacherName());
        questionPaperViewHolder.questionPaperName.setText(data.getQuestionPaperName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // view holder class
    public class QuestionPaperViewHolder extends RecyclerView.ViewHolder{

        TextView questionPaperName;
        TextView teacherName;

        public QuestionPaperViewHolder(@NonNull View itemView) {
            super(itemView);
            questionPaperName = itemView.findViewById(R.id.questionPaperName);
            teacherName = itemView.findViewById(R.id.teacherName);
        }
    }

}
