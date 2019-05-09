package paul.cipherresfeber.careerguidance.Registration.MainFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import paul.cipherresfeber.careerguidance.Constants.Extra;
import paul.cipherresfeber.careerguidance.R;
import paul.cipherresfeber.careerguidance.Student.StudentActivity;
import paul.cipherresfeber.careerguidance.Teacher.TeacherActivity;

public class Login extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container,false);

        final EditText editTextEmailId = view.findViewById(R.id.etEmailId);
        final EditText editTextPassword = view.findViewById(R.id.etPassword);
        Button buttonLogin = view.findViewById(R.id.btnLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog pd = new ProgressDialog(getContext());
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
                pd.setTitle("Please Wait");
                pd.setMessage("Logging In");

                String email = editTextEmailId.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if(!Extra.isValidEmail(email)){
                    editTextEmailId.setError("Invalid Email");
                    editTextEmailId.requestFocus();
                    return;
                }

                if(password.length() < 6 || password.length() > 20){
                    editTextPassword.setError("6 - 20 chars only");
                    editTextPassword.requestFocus();
                    return;
                }

                pd.show();

                final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                pd.cancel();

                                if(task.isSuccessful()){

                                    // get the user name and accordingly open an activity
                                    String userName = mAuth.getCurrentUser().getDisplayName();
                                    if(userName.split("@")[1].equals(Extra.USER_TYPE_TEACHER)){
                                        startActivity(new Intent(getActivity(), TeacherActivity.class));
                                        getActivity().finish();
                                    } else{
                                        startActivity(new Intent(getActivity(), StudentActivity.class));
                                        getActivity().finish();
                                    }

                                } else{
                                    try {
                                      throw task.getException();
                                    } catch (FirebaseAuthInvalidCredentialsException e){
                                        editTextPassword.setError("Wrong Password!");
                                        editTextPassword.requestFocus();

                                    } catch (FirebaseAuthInvalidUserException e){
                                        editTextEmailId.setError("User not found!");
                                        editTextEmailId.requestFocus();

                                    } catch (Exception e){
                                        Toast.makeText(getContext(),
                                                "Something went wrong!", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();

                                    }
                                }

                            }
                        });

            }
        });

        return view;
    }
}
