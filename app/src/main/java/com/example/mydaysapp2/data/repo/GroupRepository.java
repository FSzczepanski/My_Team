package com.example.mydaysapp2.data.repo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mydaysapp2.data.model.Chat;
import com.example.mydaysapp2.data.model.Goal;
import com.example.mydaysapp2.data.model.Group;
import com.example.mydaysapp2.ui.group.callbacks.CallbackGoals;
import com.example.mydaysapp2.ui.group.callbacks.CallbackMessages;
import com.example.mydaysapp2.ui.group.callbacks.CallbackGroup;
import com.example.mydaysapp2.ui.group.callbacks.CallbackGroupInfo;
import com.example.mydaysapp2.ui.group.callbacks.CallbackWorks;
import com.example.mydaysapp2.ui.group.callbacks.StringArrayCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GroupRepository {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Context context;
    private FirebaseUser currentUser;
    private CollectionReference referenceGroups;
    private String nameOfCurrentUser;
    private Group group;
    private String groupIdentity;

    public GroupRepository(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    //listening for changes
    //wiem że brzydkie ale oszczędza sie ready na bazie xd
    public void getGroupUpdates(String groupID, String nameOfCurrentUser, CallbackMessages callbackMessages,
                                CallbackGroup callbackGroup, CallbackGoals callbackGoals, CallbackWorks callbackWorks) {
        referenceGroups = db.collection("groups");

        if (groupID!=null)
        db.collection("groups").document(groupID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                //msgs
                if (mAuth.getCurrentUser() != null) {

                    ArrayList<String> msgs = null;
                    msgs = (ArrayList<String>) value.get("notes");
                    ArrayList<Chat> messagesList = new ArrayList();
                    if (msgs != null) {
                        for (int i = 0; i < msgs.size(); i++) {
                            Chat item = new Chat();
                            String[] tab = msgs.get(i).split(": ");
                            item.setMessage(tab[1]);
                            item.setSender(tab[0]);
                            messagesList.add(item);
                        }

                    } else {
                        Log.d("groupVMgettingNote", "error getting messages from server");
                    }
                    callbackMessages.onCallback(messagesList, nameOfCurrentUser);

                    //goals
                    ArrayList<HashMap> goalsHash = null;
                    goalsHash = (ArrayList<HashMap>) value.get("goals");
                    ArrayList<Goal> goals = new ArrayList();
                    if (goalsHash!=null) {
                        for (int i = 0; i < goalsHash.size(); i++) {
                            Goal goal = new Goal(goalsHash.get(i).get("title").toString(), goalsHash.get(i).get("description").toString()
                                    , goalsHash.get(i).get("creator").toString());
                            goals.add(goal);
                        }
                    }

                    callbackGoals.onCallback(goals);
                }
            }
        });
    }



    //just onstart of app to find group for user
    public void getGroupFromServer(CallbackGroupInfo callback) {

        String mail = currentUser.getEmail();
        referenceGroups = db.collection("groups");
        ArrayList data = new ArrayList<>();

        //TODO potrzeba tutaj zaimplementować many to many relationship bo to jest patologia
        //opis: przeszukiwać we wszystkich grupach czy użytkownik jest gdzieś w userach, no i admin musi być zawsze też dopisywany w userach wtedy,

        //1.sprawdzenie czy jest adminem
        referenceGroups.whereEqualTo("admin", mail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<String> listOfUsers = null;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    groupIdentity = document.getId();
                    String name = document.get("name").toString();
                    String adminMail = document.get("admin").toString();
                    data.add(name);
                    data.add(adminMail);

                    listOfUsers = (ArrayList<String>) document.get("users");
                    data.add(listOfUsers);

                }
                if (data.size() > 0) {
                    group = new Group(data.get(0).toString(), data.get(1).toString(), (ArrayList<String>) data.get(2));
                    callback.onCallback(groupIdentity, group);
                } else {
                    //nie jest adminem ale sprawdzamy czy należy do jakiejś grupy
                    Log.d("groupVMGettingGroup", "Trying to look for a user");

                    ArrayList data2 = new ArrayList<>();
                    referenceGroups.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            ArrayList<String> listOfUsersAll = null;

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listOfUsersAll = (ArrayList<String>) document.get("users");

                                for (int i = 0; i < listOfUsersAll.size(); i++) {
                                    if (listOfUsersAll.get(i).equals(mail)) {
                                        groupIdentity = document.getId();
                                        String name = document.get("name").toString();
                                        String adminMail = document.get("admin").toString();
                                        data2.add(name);
                                        data2.add(adminMail);
                                        data2.add(listOfUsersAll);
                                    }
                                }

                            }
                            if (data2.size() > 0) {
                                group = new Group(data2.get(0).toString(), data2.get(1).toString(), (ArrayList<String>) data2.get(2));
                                Log.d("groupVMGettingGroup", "Succesfully gotten data for group ");
                                callback.onCallback(groupIdentity, group);
                            } else {
                                //nie ma grupy
                                Log.d("groupVMGettingGroup", "Problem with getting data");
                                callback.onCallback(null, null);
                            }

                        }
                    });


                }
            }
        });
    }

    public void getUserNamesFromTheirMails(ArrayList users, StringArrayCallback callback) {
        String mail = currentUser.getEmail();
        CollectionReference referenceUsers = db.collection("users");

        referenceUsers.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<String> userNames = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    for (int i = 0; i < users.size(); i++) {
                        if (document.get("email").toString().equals(users.get(i))) {
                            //nie wyświetli wszystkiego co jest wpisywane przy twoerzniu grupy jeżeli nei każdy użytkownik istnieje
                            Log.d("groupVMGettingNames", document.get("displayName").toString());
                            String name = document.get("displayName").toString();
                            userNames.add(name);
                            if (document.get("email").toString().equals(mail)) {
                                nameOfCurrentUser = name;
                            }
                        }
                    }
                }
                callback.onCallback(userNames, nameOfCurrentUser);
            }
        });


    }

   public void sendMessage(String note, String nameOfCurrentUser, String groupID) {
        referenceGroups = db.collection("groups");
        if (nameOfCurrentUser != null && groupID != null) {
            referenceGroups.document(groupID)
                    .update("notes", FieldValue.arrayUnion(nameOfCurrentUser + ": " + note));
        }

    }

}