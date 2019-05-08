package paul.cipherresfeber.careerguidance.Registration.MainFragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import paul.cipherresfeber.careerguidance.Constants.Extra;
import paul.cipherresfeber.careerguidance.R;

public class SignUp extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        final EditText editTextEmailId = view.findViewById(R.id.etEmailId);
        final EditText editTextName = view.findViewById(R.id.etName);
        final EditText editTextPassword = view.findViewById(R.id.etPassword);
        final Spinner spinnerUserType = view.findViewById(R.id.spinnerUserType);

        Button buttonSignUp = view.findViewById(R.id.btnSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog pd = new ProgressDialog(getContext());
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
                pd.setTitle("Please Wait");
                pd.setMessage("Creating new Account");

                String emailId = editTextEmailId.getText().toString().trim();
                final String name = editTextName.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                final String userType = spinnerUserType.getSelectedItem().toString();

                if(!Extra.isValidEmail(emailId)){
                    editTextEmailId.setError("Invalid Email");
                    editTextEmailId.requestFocus();
                    return;
                }

                if(name.length() < 3 || name.length() > 20){
                    editTextName.setError("3 - 20 chars only");
                    editTextName.requestFocus();
                    return;
                }

                if(password.length() < 6 || password.length() > 20){
                    editTextPassword.setError("6 - 20 chars only");
                    editTextPassword.requestFocus();
                    return;
                }

                pd.show();

                final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(emailId,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            pd.cancel();

                            if(task.isSuccessful()){

                                // reset the views
                                editTextEmailId.setText("");
                                editTextName.setText("");
                                editTextPassword.setText("");
                                spinnerUserType.setSelection(0);

                                // save user type and name
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name + "@" + userType)
                                        .build();
                                user.updateProfile(profileUpdates);

                                // finally notify the user
                                String message = "Your account has been successfully created! " +
                                        "You can now login using your credentials!";

                                new AlertDialog.Builder(getContext())
                                        .setCancelable(false)
                                        .setTitle("Congrats!")
                                        .setMessage(message)
                                        .setIcon(R.drawable.ic_correct)
                                        .setPositiveButton("OK", null)
                                        .show();

                            } else{

                                task.getException().printStackTrace();

                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthUserCollisionException e) {
                                    // user already exists exception
                                    editTextEmailId.setError("Email already in use");
                                    editTextEmailId.requestFocus();

                                } catch (FirebaseAuthWeakPasswordException e){
                                    // weak password exception
                                    editTextPassword.setError("Too weak password");
                                    editTextPassword.requestFocus();

                                } catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(getContext(),
                                            "Something went wrong!", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });

            }
        });

        return view;
    }

}
