package com.example.mydaysapp2.data.model;

public class Goal {
    private String title;
    private String description;
    private String creator;

    public Goal(String title, String description, String creator) {
        this.title = title;
        this.description = description;
        this.creator = creator;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCreator() {
        return creator;
    }
}
