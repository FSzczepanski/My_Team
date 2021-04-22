package com.example.mydaysapp2.ui.group.callbacks;

import com.example.mydaysapp2.data.model.Group;

public interface CallbackUserInfo {
    void onCallback(String groupId,String nameOfCurrentUser, Group group);
}
