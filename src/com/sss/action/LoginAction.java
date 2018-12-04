package com.sss.action;

import com.sss.bean.UserBean;

public class LoginAction implements Login{

    private UserBean userBean;


    public String submit(){
        if(userBean.submit()){
            return "welcome";
        }else {
            return "failure";
        }

    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public UserBean getUserBean(){
        return this.userBean;
    }

    public String handleLogin(){
        if(Math.random()*10 < 2){
            return "failure";
        }else {

            return "success";
        }
    }
}
