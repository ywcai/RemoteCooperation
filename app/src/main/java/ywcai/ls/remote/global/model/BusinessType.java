package ywcai.ls.remote.global.model;

/**
 * Created by zmy_11 on 2017/8/4.
 */

public enum BusinessType {
    NONE(19998),FREE(19999),CONTROL(20000),PULL_CAMERA(20001),PULL_DESK(20002),PUSH_CAMERA(20003),PUSH_DESK(20004);
    int type;
    BusinessType(int _type)
    {
        type=_type;
    }
    public int getType() {
        return type;
    }
}
