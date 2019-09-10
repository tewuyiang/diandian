package com.diandian.utils.attendance;

import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装回送消息的工具类
 */
public class MessageUtils {

    public static <T> String errorToJson(T data){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status","error");
        jsonObject.put("data", data);
        return jsonObject.toJSONString();
    }


    /**
     * 将数据封装为json对象
     * @param type
     * @param status
     * @param data
     * @param <T>
     * @return
     */
    public static <T> JSONObject messageToJson(String type, String status, T data){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("status", status);
        jsonObject.put("data", data);
        return jsonObject;
    }


    /**
     * 将一个json型字符串转为json对象
     * @param jsonStr
     * @return
     */
    public static JSONObject messageToJson(String jsonStr) {
        return JSONObject.parseObject(jsonStr);
    }

}
