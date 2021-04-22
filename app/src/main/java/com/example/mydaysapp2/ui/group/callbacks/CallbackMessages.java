package com.example.mydaysapp2.ui.group.callbacks;

import com.example.mydaysapp2.data.model.Chat;
import com.example.mydaysapp2.data.model.Group;

import java.util.ArrayList;

public interface CallbackMessages {
    void onCallback(ArrayList<Chat> messages, String currentUser);
}
