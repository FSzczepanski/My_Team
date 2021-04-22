package com.example.mydaysapp2.data.model;

import java.util.ArrayList;

public class GroupOnAdd {
        public String name;
        public String admin;
        public ArrayList<String> users;

        public GroupOnAdd(String name, String admin, ArrayList<String> users) {
            this.name = name;
            this.admin = admin;
            this.users = users;
        }

    }


