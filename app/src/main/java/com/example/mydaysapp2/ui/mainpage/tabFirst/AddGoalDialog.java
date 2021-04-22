package com.example.mydaysapp2.ui.mainpage.tabFirst;

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

import com.example.mydaysapp2.R;
import com.example.mydaysapp2.data.model.Goal;

import java.util.List;

public class AddGoalDialog extends DialogFragment {
    private GoalsViewModel mViewModel;
    private String title ="";
    private String description="";
    private List<String> goals;
    private DialogFragment dialog;
    private String currentUser;

    public AddGoalDialog(GoalsViewModel mViewModel, String currentUser) {
        this.mViewModel = mViewModel;
        this.currentUser = currentUser;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Add new goal");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_goal, null);
        builder.setView(view);


        EditText editTextTitle = view.findViewById(R.id.etTitle);
        EditText editTextGoalDescription = view.findViewById(R.id.etGoal);


        TextView textViewAdd = view.findViewById(R.id.tvAdd);
        TextView textViewCancel = view.findViewById(R.id.tvCancel);

        textViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = editTextTitle.getText().toString();
                description = editTextTitle.getText().toString();

                if ((!title.matches(""))) {
                    Toast.makeText(getActivity().getApplicationContext(), "worken ", Toast.LENGTH_SHORT).show();


                    Goal goal = new Goal(title,description,currentUser);
                    mViewModel.createGoal(goal);
                    AddGoalDialog.this.getDialog().cancel();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Enter correct data ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddGoalDialog.this.getDialog().cancel();
            }
        });



        return builder.create();
    }




}