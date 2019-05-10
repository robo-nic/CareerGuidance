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

import paul.cipherresfeber.careerguidance.Constants.Extra;
import paul.cipherresfeber.careerguidance.R;
import paul.cipherresfeber.careerguidance.CustomClasses.Question;

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
        View view = LayoutInflater.from(context).inflate(R.layout.item_teacher_question, viewGroup, false);
        return new QuestionAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapterViewHolder v, int i) {

        Question question = list.get(i);

        // set question and category to the view
        v.question.setText(question.getQuestion());
        v.category.setText("Category: " + question.getCategory());

        v.option1.setText("1. " + question.getOption1());
        v.option2.setText("2. " + question.getOption2());
        v.option3.setText("3. " + question.getOption3());
        v.option4.setText("4. " + question.getOption4());

        String correctOption = question.getCorrectOption();

        if(correctOption.equals(Extra.OPTION_1)){
            v.option1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_correct, 0);
            v.option2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_wrong, 0);
            v.option3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_wrong, 0);
            v.option4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_wrong, 0);
        } else if(correctOption.equals(Extra.OPTION_2)){
            v.option1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_wrong, 0);
            v.option2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_correct, 0);
            v.option3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_wrong, 0);
            v.option4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_wrong, 0);
        } else if(correctOption.equals(Extra.OPTION_3)){
            v.option1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_wrong, 0);
            v.option2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_wrong, 0);
            v.option3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_correct, 0);
            v.option4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_wrong, 0);
        } else{
            v.option1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_wrong, 0);
            v.option2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_wrong, 0);
            v.option3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_wrong, 0);
            v.option4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_correct, 0);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class QuestionAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView question;
        TextView category;
        TextView option1;
        TextView option2;
        TextView option3;
        TextView option4;

        public QuestionAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            category = itemView.findViewById(R.id.category);
            option1 = itemView.findViewById(R.id.option1);
            option2 = itemView.findViewById(R.id.option2);
            option3 = itemView.findViewById(R.id.option3);
            option4 = itemView.findViewById(R.id.option4);
        }

    }

}
