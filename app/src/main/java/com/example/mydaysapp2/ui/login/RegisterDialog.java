package com.example.mydaysapp2.ui.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mydaysapp2.R;
import com.example.mydaysapp2.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class RegisterDialog extends DialogFragment {
    private FirebaseAuth mAuth;
    private AuthViewModel mViewModel;
    private EditText editTextMail;
    private EditText editTextpass;
    private EditText editTextname;
    private boolean blad =false;

    public RegisterDialog(AuthViewModel mViewModel) {
        this.mViewModel = mViewModel;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Register");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_register, null);
        builder.setView(view);


        TextView textViewAdd = view.findViewById(R.id.tvAdd);
        TextView textViewCancel = view.findViewById(R.id.tvCancel);
        textViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(view);
            }
        });

        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterDialog.this.getDialog().cancel();
            }
        });


        return builder.create();
    }


    private void register(View view) {
        editTextMail = view.findViewById(R.id.etmailreg);
        editTextpass = view.findViewById(R.id.etpassReg);
        editTextname = view.findViewById(R.id.etNameReg);

        if (!validateForm(view)) {
            return;
        }

        String email = editTextMail.getText().toString();
        String password = editTextpass.getText().toString();
        String name = editTextname.getText().toString();


        createAccount(email, name, password);
        RegisterDialog.this.getDialog().cancel();

    }

    private boolean validateForm(View view) {
        boolean valid = true;

        String email = editTextMail.getText().toString();
        String password = editTextpass.getText().toString();
        String name = editTextname.getText().toString();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Podaj email",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (!email.trim().matches(emailPattern)) {
            Toast.makeText(getContext(), "Podaj prawidłowy adres email", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (TextUtils.isDigitsOnly(name)) {
            Toast.makeText(getContext(), "Podaj name",
                    Toast.LENGTH_SHORT).show();
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


    public void createAccount(String email,String name, String password) {
        //Log.d(TAG, "createAccount:" + user.getEmail());
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            if(currentUser!=null && isNewUser){
                                User user = new User(email, name, currentUser.getUid());
                                mViewModel.createUser(user);

                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }

                    }


                });
        RegisterDialog.this.getDialog().cancel();
    }


}