package com.ptmsa1.vizage.DataModel;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificationModel {
    String notificationTitle,notificationBody,notificationType ,notificationCode,actionOne,actionTwo,notificationDate,notificationID;

    Map<String,String> pairs =new HashMap<String, String>() {};
    public  NotificationModel (String ID,String title,String body,String type,String code,String date)
    {
        notificationID=ID;
        notificationTitle = title;
        notificationBody = body;
        notificationType = type;
        notificationCode = code;
        notificationDate=date;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String title) {
        this.notificationTitle = title;
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    public void setNotificationBody(String body) {
        this.notificationBody = body;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String type) {
        this.notificationType = type;
    }

    public String getNotificationCode() {
        return notificationCode;
    }

    public void setNotificationCode(String code) {
        this.notificationCode= code;
    }

    public void setActionOne(String actionOne){this.actionOne=actionOne;}

    public String getActionOne() {
        return actionOne;
    }

    public void setActionTwo(String actionTwo) {
        this.actionTwo = actionTwo;
    }

    public String getActionTwo() {
        return actionTwo;
    }

    public void AddToPairs(String key,String value)
    {
        pairs.put(key,value);
    }

    public String getFromPairs(String key)
    {
        return pairs.get(key);
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public String getPairsStr()
    {
        Log.e("getPairs","getPairsStr is triggered");

        JSONArray result = new JSONArray();
        JSONObject temp;
        try {
            result.put(0,new JSONObject().put("code",getNotificationCode()));

        }
        catch (Exception e)
        {

        }

        for (Map.Entry<String, String> entry : pairs.entrySet())
        {
            try {
                temp=new JSONObject();
                //temp.put("book_id",entry.getKey());
                temp.put(entry.getKey(),entry.getValue());
                Log.e("getPairsStr",temp.toString());

                result.put(temp);
            }
            catch (Exception e)
            {
                Log.e("getPairsStr",e.getMessage());

            }
        }
        return result.toString();
    }
}
