package com.example.mydaysapp2.ui.group;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydaysapp2.R;
import com.example.mydaysapp2.data.model.Chat;
import com.example.mydaysapp2.data.model.Goal;
import com.example.mydaysapp2.data.model.Group;
import com.example.mydaysapp2.ui.group.callbacks.CallbackGoals;
import com.example.mydaysapp2.ui.group.callbacks.CallbackGroup;
import com.example.mydaysapp2.ui.group.callbacks.CallbackMessages;
import com.example.mydaysapp2.ui.group.callbacks.CallbackUserInfo;
import com.example.mydaysapp2.ui.group.callbacks.SendDataToOtherFragment;
import com.example.mydaysapp2.ui.group.callbacks.StringArrayCallback;

import java.util.ArrayList;

public class GroupFragment extends Fragment {

    private GroupViewModel mViewModel;
    private DialogFragment dialog;
    private Group group;
    private View teamView;
    private View viewNoTeam;
    private View root;

    private ProgressDialog progressDialog;

    private GroupUsersAdapter groupUsersAdapter;
    private RecyclerView usersRecycler;
    private ArrayList<String> listOfUsers;

    private RecyclerView messagesRecycler;
    private SendDataToOtherFragment SM;

    public static GroupFragment newInstance() {
        return new GroupFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.group_fragment, container, false);
        teamView = root.findViewById(R.id.viewForTeam);
        viewNoTeam = root.findViewById(R.id.viewNoTeam);

        return root;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        progressDialogInit();
        getGroupFromServer();

    }


    private void sendMessage() {
        ImageButton sendButton = root.findViewById(R.id.button_send);
        EditText msgEditText = root.findViewById(R.id.editText_send);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = msgEditText.getText().toString();
                if (!msg.equals("")){
                    mViewModel.sendMessage(getActivity(),msg);
                    msgEditText.setText("");
                    //getMessages();
                }else{
                    Toast.makeText(getActivity(),"You cant send empty message",Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private void getGroupUpdates(){
        mViewModel.getGroupUpdates(getActivity(), new CallbackMessages() {
            @Override
            public void onCallback(ArrayList<Chat> messages, String currentUser) {
                progressDialog.dismiss();
                RecyclerView chatRecycler = root.findViewById(R.id.chatRv);
                ChatAdapter chatAdapter = new ChatAdapter(getActivity(), currentUser, messages);

                chatRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                chatRecycler.setAdapter(chatAdapter);
                //groupUsersAdapter.notifyDataSetChanged();
                sendMessage();
            }
        }, new CallbackGroup() {
            @Override
            public void onCallback(Group group) {

            }
        });

    }

    private void progressDialogInit() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);

        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void getGroupFromServer(){
        mViewModel.getGroupFromServer(getActivity(),new CallbackGroup() {
            @Override
            public void onCallback(Group group) {
                if (group!=null) {
                    setUserList(group);
                }

            }
        });
    }




    private void setUserList(Group group) {
        TextView test = root.findViewById(R.id.testotv);
        test.setText(group.name);
        usersRecycler = root.findViewById(R.id.userNamesRv);
        listOfUsers = group.users;
        String admin = group.admin;
        listOfUsers.add(0,admin);
        mViewModel.getUserNamesFromTheirMails(getActivity(),listOfUsers,new StringArrayCallback() {
            @Override
            public void onCallback(ArrayList<String> items, String currentUserName) {
                groupUsersAdapter = new GroupUsersAdapter(getActivity(),items, mViewModel);

                usersRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                usersRecycler.setAdapter(groupUsersAdapter);
                groupUsersAdapter.notifyDataSetChanged();
                getGroupUpdates();

            }
        });




    }



}