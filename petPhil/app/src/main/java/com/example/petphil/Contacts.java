package com.example.petphil;

//user contacts show class
public class Contacts {
    private String name; //用户昵称
    private String desc; //用户个签
    private int imageId; //user image resource id

    public Contacts(String name,String desc,int imageId){
        this.name = name;
        this.desc = desc;
        this.imageId = imageId;
    }

    public String getName(){
        return  name;
    }
    public String getDesc(){
        return desc;
    }
    public int getImageId(){
        return imageId;
    }
}
