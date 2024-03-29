package paul.cipherresfeber.careerguidance.Teacher.MainFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import paul.cipherresfeber.careerguidance.R;

public class Dashboard extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_dashboard, container, false);

        TextView textViewTeacherName = view.findViewById(R.id.txvTeacherName);
        TextView textViewEmail = view.findViewById(R.id.txvEmail);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        textViewTeacherName.setText(user.getDisplayName().split("@")[0]);
        textViewEmail.setText(user.getEmail());

        return view;
    }
}
