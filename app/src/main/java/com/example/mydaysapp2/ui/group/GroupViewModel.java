package com.example.mydaysapp2.ui.group;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.mydaysapp2.data.model.Chat;
import com.example.mydaysapp2.data.model.Goal;
import com.example.mydaysapp2.data.model.Group;
import com.example.mydaysapp2.data.model.GroupOnAdd;
import com.example.mydaysapp2.data.repo.GroupRepository;
import com.example.mydaysapp2.ui.group.callbacks.CallbackGoals;
import com.example.mydaysapp2.ui.group.callbacks.CallbackGroup;
import com.example.mydaysapp2.ui.group.callbacks.CallbackGroupInfo;
import com.example.mydaysapp2.ui.group.callbacks.CallbackMessages;
import com.example.mydaysapp2.ui.group.callbacks.CallbackUserInfo;
import com.example.mydaysapp2.ui.group.callbacks.CallbackWorks;
import com.example.mydaysapp2.ui.group.callbacks.StringArrayCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class GroupViewModel extends ViewModel {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private GroupRepository groupRepository;
    private CollectionReference referenceGroups;
    private Group groupToSend;
    private String groupID;
    private String currentNameOfUser;
    FirebaseUser currentUser;


    public GroupViewModel() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }




    public void getGroupFromServer(Context context, CallbackGroup callback) {
        groupRepository = new GroupRepository(context);
        groupRepository.getGroupFromServer(new CallbackGroupInfo() {
            @Override
            public void onCallback(String groupId, Group group) {
                groupID =groupId;
                groupToSend = group;
                callback.onCallback(group);
            }
        });
    }


    public void getUserNamesFromTheirMails(Context context,ArrayList users, StringArrayCallback callback) {
        groupRepository = new GroupRepository(context);
        groupRepository.getUserNamesFromTheirMails(users, new StringArrayCallback() {

            @Override
            public void onCallback(ArrayList<String> items, String currentUserName) {
                currentNameOfUser=currentUserName;
                callback.onCallback(items,currentUserName);
            }
        });

    }


   public void sendMessage(Context context,String note) {
        groupRepository = new GroupRepository(context);
        groupRepository.sendMessage(note,currentNameOfUser, groupID);
    }


    public void getGroupUpdates(Context context, CallbackMessages callbackMessages, CallbackGroup callbackGroup) {
        groupRepository = new GroupRepository(context);
        groupRepository.getGroupUpdates(groupID, currentNameOfUser, new CallbackMessages() {
            @Override
            public void onCallback(ArrayList<Chat> messages, String currentUser) {
                callbackMessages.onCallback(messages, currentUser);
            }
        }, new CallbackGroup() {
            @Override
            public void onCallback(Group group) {

            }

        }, new CallbackGoals() {
            @Override
            public void onCallback(ArrayList<Goal> goals) {

            }
        }, new CallbackWorks() {
            @Override
            public void onCallback(ArrayList<String> works) {

            }
        });
    }
}
