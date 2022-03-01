package com.example.petphil.userHandle;

public class UserHandle {
    /*
    * 这是一个用来处理用户信息的类
    * */
    private String id=null;
    public UserHandle(String id){
        /*
        * 传入用户id
        * */
        this.id = id;
    }

    public String getImageUrl(){
        /*
        * 通过用户id得到图片资源id
        * */
        //this.id
        return "lujing";
    }

    public String getUserName(){
        /*
        * 得到用户昵称
        * */
        return "zhangsan";
    }

    public String getUserId(){
        return "hello";
    }
}
