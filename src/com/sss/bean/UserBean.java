package com.sss.bean;

import com.sss.dao.Conversation;
import com.sss.dao.UserDao;
import com.sss.utility.BeanUtility;


public class UserBean {

    private Integer userId;
    private String userName;
    private String userPassword;
    private UserDao userDao;

    public boolean submit(){
        return userDao.query();
    }

    public boolean signIn(){
        try {
            if(BeanUtility.getBean(Conversation.getObjectByName(this),"userPassword").toString().equalsIgnoreCase(getUserPassword())){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }


    public UserBean() {

    }

    public UserBean(Integer userId) {
        this.userId = userId;
    }

    public UserBean(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public UserBean(Integer userId, String userName, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
