package paul.cipherresfeber.careerguidance.Teacher.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import paul.cipherresfeber.careerguidance.R;

public class AddQuestionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_question, container, false);

        String questionnaireKey = getArguments().getString("QuestionnaireKey");
        Toast.makeText(getContext(), questionnaireKey, Toast.LENGTH_SHORT).show();

        return view;
    }
}
