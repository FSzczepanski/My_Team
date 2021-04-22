package com.example.mydaysapp2.data.model;

public class UserInfo {
    private String userName;
    private String groupId;
    private Group group;

    public UserInfo(String userName, String groupId, Group group) {
        this.userName = userName;
        this.groupId = groupId;
        this.group = group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public String getUserName() {
        return userName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
