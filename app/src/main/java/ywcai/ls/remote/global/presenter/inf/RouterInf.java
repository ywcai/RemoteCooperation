package ywcai.ls.remote.global.presenter.inf;

import ywcai.ls.remote.global.model.User;


public interface RouterInf {
    void startLogin();
    void createNewSession(User user);
    void recoverySession();
}
