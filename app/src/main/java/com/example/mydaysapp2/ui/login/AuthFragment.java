package com.example.mydaysapp2.ui.login;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mydaysapp2.MainActivity;
import com.example.mydaysapp2.R;
import com.example.mydaysapp2.ui.utils.TabLayoutDisabler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class AuthFragment extends Fragment implements TabLayoutDisabler {


    private AuthViewModel authViewModel;
    private FirebaseAuth mAuth;
    private Activity activity;
    private DialogFragment dialog;
    private View root;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         root = inflater.inflate(R.layout.auth_fragment, container, false);

        hideTabLayout();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        updateUI(currentUser);

        login();


        return root;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        register();

    }


    public void updateUI(FirebaseUser account) {

        if (account != null) {
            Log.d(TAG, "signIn:success");
            NavController navController = Navigation.findNavController(requireActivity(), R.id.my_nav_host_fragment);
            navController.navigate(R.id.action_authFragment_to_mainPageFragment);

        } else {
            Log.w(TAG, "signIn:failure");
            Toast.makeText(getContext(), "Zaloguj się",
                    Toast.LENGTH_LONG).show();
        }

    }

    private void register() {
        Button registerButton = root.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new RegisterDialog(authViewModel);
                dialog.show(getParentFragmentManager(), "DialogFragment");
            }
        });

    }

    private void login() {
        Button loginButton = root.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()) {
                    return;
                }

                EditText emailEdit = root.findViewById(R.id.loginEmail);
                EditText passwordEdit = root.findViewById(R.id.loginPassword);
                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                //authViewModel.signIn(email,password);
                signIn(email, password);
            }
        });

    }

    private boolean validateForm() {
        boolean valid = true;

        EditText emailEdit = root.findViewById(R.id.loginEmail);
        EditText passwordEdit = root.findViewById(R.id.loginPassword);
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Podaj email",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (!email.trim().matches(emailPattern)) {
            Toast.makeText(getContext(), "Podaj prawidłowy adres email", Toast.LENGTH_SHORT).show();
            valid = false;
        }


        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Podaj hasło",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (password.length() < 6) {
            Toast.makeText(getContext(), "Hasło jest za krótkie",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        }


        return valid;
    }


    public void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            NavController navController = Navigation.findNavController(requireActivity(),
                                    R.id.my_nav_host_fragment);
                            navController.navigate(R.id.action_authFragment_to_mainPageFragment);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Podano nieprawidłowy email lub hasło",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public void hideTabLayout() {
        MainActivity.hideTabLayout();
    }

    @Override
    public void showTabLayout() {

    }
}

