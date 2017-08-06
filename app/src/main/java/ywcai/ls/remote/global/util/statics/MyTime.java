package ywcai.ls.remote.global.util.statics;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MyTime {

    public static String getNowTime() {
        String time = "";
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddHHMMSS");
        time = simpleDateFormat.format(date);

        Log.i("ywcai", time);
        return time;
    }

    public static String getNowDate() {
        String time = "";
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYMMdd");
        time = simpleDateFormat.format(date);
        Log.i("ywcai", time);
        return time;
    }
}
