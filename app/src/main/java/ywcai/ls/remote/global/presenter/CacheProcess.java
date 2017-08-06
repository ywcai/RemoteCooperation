package ywcai.ls.remote.global.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ywcai.ls.remote.global.model.DeviceInfo;
import ywcai.ls.remote.global.model.User;
import ywcai.ls.remote.welcome.model.instance.MainApplication;

public class CacheProcess {
    File file = MainApplication.getInstance().getFilesDir();
    public void setCacheUser(User user) {
        user.setSign();
        Gson gson = new Gson();
        String cache = gson.toJson(user, User.class);
        setCache("user",cache);
    }
    public User getCacheUser() {
        String cache = getCache("user");
        if (cache.equals("null")) {
            return null;
        }
        Gson gson = new Gson();
        User user = gson.fromJson(cache, User.class);
        if(user==null)
        {
            return null;
        }
        if (!user.isVal()) {
            return null;
        }
        return user;
    }
    public List<DeviceInfo> getDevices()
    {
        List<DeviceInfo> devices=new ArrayList<>();
        String cache = getCache("devices");
        if (cache.equals("null")||cache.equals("")) {
            return devices;
        }
        Gson gson = new Gson();
        List<DeviceInfo> temp=gson.fromJson(cache,new TypeToken<List<DeviceInfo>>() {
        }.getType());
        if(temp==null)
        {
            return devices;
        }
        devices.addAll(temp);
        return devices;
    }
    public void setDevices(String cache)
    {
        setCache("devices",cache);
    }
    public void setDevice(String dev)
    {
        List<DeviceInfo> devices=getDevices();
        Gson gson=new Gson();
        DeviceInfo device=gson.fromJson(dev,DeviceInfo.class);
        for (DeviceInfo temp:devices)
        {
            if(temp.accessCode.equals(device.accessCode))
            {
                temp.status=device.status;
                String cache=gson.toJson(devices,new TypeToken<List<DeviceInfo>>() {
                }.getType());
                setCache("devices",cache);
                return;
            }
        }
        devices.add(device);
        String cache=gson.toJson(devices,new TypeToken<List<DeviceInfo>>() {
        }.getType());
        setCache("devices",cache);
    }
    public void delDevices() {
        File f = new File(file, "devices");
        f.delete();
    }
    private void  setCache(String fileName,String cache)
    {
        File f = new File(file, fileName);
        FileOutputStream fos = null;
        if(!f.exists())
        {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fos=MainApplication.getInstance().getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(cache.getBytes("utf-8"));
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCache(String fileName) {
        String cache = "null";
        File f = new File(file,fileName);
        if (f.exists()) {
            try {
                FileInputStream in = new FileInputStream(f);
                InputStreamReader inputStreamReader = new InputStreamReader(in , "utf-8");
                BufferedReader reader = new BufferedReader(inputStreamReader);
                cache = "";
                String s="";
                while ((s=reader.readLine())!=null)
                {
                    cache+=s+"\n";
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("ywcai", "getCache err: "+e);
                return "null";
            }
        }
        return cache;
    }


}
