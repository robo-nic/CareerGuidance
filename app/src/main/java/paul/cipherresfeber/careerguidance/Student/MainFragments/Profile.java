package paul.cipherresfeber.careerguidance.Student.MainFragments;

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

public class Profile extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        TextView textViewStudentName = view.findViewById(R.id.txvStudentName);
        TextView textViewEmail = view.findViewById(R.id.txvEmail);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        textViewStudentName.setText(user.getDisplayName().split("@")[0]);
        textViewEmail.setText(user.getEmail());


        return view;
    }
}
