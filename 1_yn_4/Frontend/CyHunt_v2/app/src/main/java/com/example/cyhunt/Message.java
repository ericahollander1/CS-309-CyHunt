package com.example.cyhunt;

/**
 * Ravi Tamada (2017) Android Building Group Chat App using Sockets
 * [Source Code] https://www.androidhive.info/2014/10/android-building-group-chat-app-using-sockets-part-2/
 */
public class Message {
    private String fromName, message;
    private boolean isSelf;

    public Message() {
    }

    public Message(String fromName, String message, boolean isSelf) {
        this.fromName = fromName;
        this.message = message;
        this.isSelf = isSelf;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }

}