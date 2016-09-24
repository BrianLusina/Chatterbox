package com.chatterbox.chatterbox.models.pojo;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox
 * Created by lusinabrian on 15/08/16 at 15:39
 * <p/>
 * Description: Properties of the message model displaying incoming messages
 */
public class MessageModel {
    private String id, name, photoUrl,text;

    /*constructor*/
    public MessageModel(){}

    public MessageModel(String name, String photoUrl, String text) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.text = text;
    }

    /*GETTERS AND SETTERS*/
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

    /*CLASS END*/
}
