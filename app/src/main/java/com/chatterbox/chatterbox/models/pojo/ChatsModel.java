package com.chatterbox.chatterbox.models.pojo;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox.models
 * Created by lusinabrian on 02/09/16 at 16:37
 * <p/>
 * Description: Contains the model contract of the Chats that will appear in the ChatsFragment
 */
public class ChatsModel {
    private String id, name, photoUrl,text, time;

    public ChatsModel(){}

    public ChatsModel(String name, String photoUrl, String text, String time) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.text = text;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
