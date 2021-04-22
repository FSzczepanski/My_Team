package com.example.mydaysapp2.data.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    public String name;
    public String admin;
    public ArrayList<String> users;

    public Group(String name, String admin, ArrayList<String> users) {
        this.name = name;
        this.admin = admin;
        this.users = users;
    }

}
