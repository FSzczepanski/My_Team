package com.example.mydaysapp2.ui.group.callbacks;

import com.example.mydaysapp2.data.model.Group;
import com.google.firebase.database.core.view.Event;

import java.util.ArrayList;

public interface CallbackGroup {
    void onCallback(Group group);
}