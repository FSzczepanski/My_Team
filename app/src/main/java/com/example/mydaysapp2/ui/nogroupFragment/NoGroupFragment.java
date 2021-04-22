package com.example.mydaysapp2.ui.nogroupFragment;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mydaysapp2.MainActivity;
import com.example.mydaysapp2.R;
import com.example.mydaysapp2.ui.utils.TabLayoutDisabler;
import com.google.firebase.auth.FirebaseAuth;

public class NoGroupFragment extends Fragment implements TabLayoutDisabler {

    private NoGroupViewModel mViewModel;
    private DialogFragment dialog;
    private View root;
    public static NoGroupFragment newInstance() {
        return new NoGroupFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.no_group_fragment, container, false);
        hideTabLayout();
        signOut();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NoGroupViewModel.class);
        showAddGroupDialog();
    }

    public void showAddGroupDialog() {
        Button buttonAdd = root.findViewById(R.id.addNewGroupButton);
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog = new AddGroupDialog(mViewModel);
                dialog.show(getParentFragmentManager(), "DialogFragment");

            }
        });
    }

    public void signOut(){

        Button logoutButton = root.findViewById(R.id.logoutButton1);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                final NavController navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
                navController.navigate(R.id.action_noGroupFragment_to_authFragment);
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