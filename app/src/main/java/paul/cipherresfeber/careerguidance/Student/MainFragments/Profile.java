package paul.cipherresfeber.careerguidance.Student.MainFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import paul.cipherresfeber.careerguidance.R;

public class Profile extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        TextView textViewTeacherName = view.findViewById(R.id.txvTeacherName);
        TextView textViewEmail = view.findViewById(R.id.txvEmail);

        // TODO: if any improvement in UI or UX ---- to be done later

        return view;
    }
}
