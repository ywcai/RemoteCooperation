package ywcai.ls.remote.login.model;

import ywcai.ls.remote.global.model.User;

/**
 * Created by zmy_11 on 2017/7/15.
 */

public class HttpRequest {

    public LoginResult requestAuth(User user)
    {
        LoginResult loginResult=new LoginResult();
        loginResult.isLogin=true;
        loginResult.accessToken="testToken";
        return loginResult;
    }
}
