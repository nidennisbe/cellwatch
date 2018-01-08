package com.example.niden.cellwatchsharing.database;

import java.util.Date;

/**
 * Created by niden on 16-Nov-17.
 */

public class PostEntityDatabase {
    private String messageText;
    private String messageUser;
    private long messageTime;

    public PostEntityDatabase(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        messageTime = new Date().getTime();
    }

    public PostEntityDatabase(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

}
