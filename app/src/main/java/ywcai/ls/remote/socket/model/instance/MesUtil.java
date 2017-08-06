package ywcai.ls.remote.socket.model.instance;

import com.google.gson.Gson;

import org.apache.mina.core.future.WriteFuture;
import java.io.UnsupportedEncodingException;
import ywcai.ls.mina.socket.ClientSocket;
import ywcai.ls.remote.socket.cfg.ResultCode;
import ywcai.ls.remote.socket.model.ApplicationProtocol;

public class MesUtil {
    public static WriteFuture sendJson(ClientSocket session, int type, String content) {
        String str = getJsonObj(type, content);
        byte[] temp = null;
        try {
            temp = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] data = new byte[temp.length + 1];
        data[0] = ResultCode.byte_head_json;
        System.arraycopy(temp, 0, data, 1, temp.length);
        return session.sentMes(data);
    }
    public static WriteFuture sendByte(ClientSocket session, byte[] payload) {
        byte[] data = new byte[payload.length + 1];
        data[0] = ResultCode.byte_head_byte;
        System.arraycopy(payload, 0, data, 1, payload.length);
        return session.sentMes(data);
    }
    public static ApplicationProtocol getObj(byte[] payload) {
        String s = "";
        try {
            s = new String(payload, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();
        return g.fromJson(s, ApplicationProtocol.class);
    }
    private static String getJsonObj(int type, String content) {
        Gson gson = new Gson();
        ApplicationProtocol applicationProtocol = new ApplicationProtocol();
        applicationProtocol.type = type;
        applicationProtocol.content = content;
        String string = gson.toJson(applicationProtocol);
        return string;
    }
}
