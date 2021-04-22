package com.example.mydaysapp2.ui.group.callbacks;

import com.example.mydaysapp2.data.model.Group;

public interface SendDataToOtherFragment {
    void sendData(String groupId,String nameOfCurrentUser, Group group);
}
