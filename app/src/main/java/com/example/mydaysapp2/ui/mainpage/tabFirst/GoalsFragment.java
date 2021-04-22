package com.example.mydaysapp2.ui.mainpage.tabFirst;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mydaysapp2.R;
import com.example.mydaysapp2.data.model.Goal;
import com.example.mydaysapp2.ui.group.callbacks.CallbackGoals;
import com.example.mydaysapp2.ui.group.callbacks.StringArrayCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class GoalsFragment extends Fragment {

    private GoalsViewModel mViewModel;
    private View root;
    private RecyclerView goalsRecycler;
    private ProgressDialog progressDialog;
    private DialogFragment dialog;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
     NavController navController;

    public static GoalsFragment newInstance() {
        return new GoalsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.goals_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GoalsViewModel.class);
        progressDialogInit();
        showAddGoalDialog();
        getGoalsFromServer();
    }

    public void getGoalsFromServer() {


        mViewModel.getGoalsFromServerAndUpdate(getActivity(), new CallbackGoals() {
            @Override
            public void onCallback(ArrayList<Goal> goals) {
                if (goals==null){
                    progressDialog.dismiss();
                    goToNoGroupFragment();
                }
                else {
                    goalsRecycler = root.findViewById(R.id.goalsRv);
                    String currentUserMail = currentUser.getEmail();

                    GoalsAdapter goalsAdapter = new GoalsAdapter(getActivity(), goals, mViewModel, currentUserMail);
                    goalsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                    goalsRecycler.setAdapter(goalsAdapter);
                    goalsAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }
        });


    }

    public void goToNoGroupFragment(){
        navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
        navController.navigate(R.id.action_mainPageFragment_to_noGroupFragment);
    }

    public void showAddGoalDialog() {
        FloatingActionButton buttonAdd = root.findViewById(R.id.addNewGoalButton);
        ArrayList<String> users = new ArrayList<>();
        users.add(currentUser.getEmail().toString());

        mViewModel.getUserNamesFromTheirMails(getActivity(), users, new StringArrayCallback() {
            @Override
            public void onCallback(ArrayList<String> items, String currentUserName) {
                buttonAdd.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        dialog = new AddGoalDialog(mViewModel, currentUserName);
                        dialog.show(getParentFragmentManager(), "DialogFragment");

                    }
                });
            }
        });

    }



    private void progressDialogInit() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);

        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }


}