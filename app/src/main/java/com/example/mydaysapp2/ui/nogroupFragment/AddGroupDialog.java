package com.example.mydaysapp2.ui.nogroupFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mydaysapp2.R;
import com.example.mydaysapp2.data.model.GroupOnAdd;
import com.example.mydaysapp2.ui.group.GroupViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class AddGroupDialog extends DialogFragment {
        private NoGroupViewModel mViewModel;
        private String name ="";
        private List<String> users;
        private DialogFragment dialog;
        private FirebaseAuth mAuth;
        private NavController navController;


        public AddGroupDialog(NoGroupViewModel mViewModel) {
            this.mViewModel =mViewModel;
        }


        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle("Add new group");
            View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_group, null);
            builder.setView(view);


            EditText editTextname = view.findViewById(R.id.etName);
            EditText editTextuser1 = view.findViewById(R.id.etUser1);
            EditText editTextuser2 = view.findViewById(R.id.etUser2);
            EditText editTextuser3 = view.findViewById(R.id.etUser3);

            TextView textViewAdd = view.findViewById(R.id.tvAdd);
            TextView textViewCancel = view.findViewById(R.id.tvCancel);
            textViewAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = editTextname.getText().toString();
                    ArrayList<String> users = new ArrayList<>();
                    users.add(editTextuser1.getText().toString());
                    users.add(editTextuser2.getText().toString());
                    users.add(editTextuser3.getText().toString());

                    if ((!name.matches("")) && users.size()>0) {
                         mAuth = FirebaseAuth.getInstance();
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        String adminMail = currentUser.getEmail();

                        GroupOnAdd group = new GroupOnAdd(name,adminMail,users);

                        mViewModel.createGroup(group);

                        navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
                        navController.navigate(R.id.action_noGroupFragment_to_mainPageFragment);
                        AddGroupDialog.this.getDialog().cancel();
                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(), "Enter correct data ", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            textViewCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddGroupDialog.this.getDialog().cancel();
                }
            });



            return builder.create();
        }




}