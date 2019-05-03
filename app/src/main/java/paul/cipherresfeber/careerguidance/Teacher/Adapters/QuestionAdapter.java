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
import paul.cipherresfeber.careerguidance.Teacher.CustomClasses.Question;
import paul.cipherresfeber.careerguidance.Teacher.CustomClasses.QuestionPaper;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionAdapterViewHolder> {

    Context context;
    ArrayList<Question> list;
    FragmentManager fragmentManager;

    public QuestionAdapter(Context context, ArrayList<Question> list, FragmentManager fragmentManager) {
        this.context = context;
        this.list = list;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public QuestionAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, viewGroup, false);
        return new QuestionAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapterViewHolder v, int i) {

        Question question = list.get(i);

        v.question.setText(question.getQuestion());
        v.category.setText(question.getCategory());
        v.correctOption.setText(question.getCorrectOption());
        v.option1.setText(question.getOption1());
        v.option2.setText(question.getOption2());
        v.option3.setText(question.getOption3());
        v.option4.setText(question.getOption4());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class QuestionAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView question;
        TextView category;
        TextView correctOption;
        TextView option1;
        TextView option2;
        TextView option3;
        TextView option4;

        public QuestionAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            category = itemView.findViewById(R.id.category);
            correctOption = itemView.findViewById(R.id.correctOption);
            option1 = itemView.findViewById(R.id.option1);
            option2 = itemView.findViewById(R.id.option2);
            option3 = itemView.findViewById(R.id.option3);
            option4 = itemView.findViewById(R.id.option4);
        }

    }

}
