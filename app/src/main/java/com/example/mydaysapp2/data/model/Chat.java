package com.example.mydaysapp2.data.model;

public class Chat {

    private String message;
    private String sender;

    public Chat(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public Chat(){

    }

    public void setMessage(String note) {
        this.message = note;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }
}
