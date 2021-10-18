package com.example.lib;

import java.util.HashMap;

public class MyClass {
    public static void main(String[] a) {
        int duration = 331;
//        log(getTime(duration));
        String msg = "<p>网络未开启，请检查网络设置</p>";
        msg = DelHtmlTagUtil.Html2Text(msg);
        log(msg);

        HashMap<String, String> map = new HashMap<>();
        map.put("businessId", "001");// 规划师id
        map.put("contentSecondType", "");
        map.put("copartnerId", "0025");// 合伙人id规划师不用传
        map.put("contentType", "5");
        map.put("copartnerUserType", "ORDINARY_USER");// 普通用户 用户类型合伙人必传
        map.put("commonId", "2525");// 文章id

        StringBuilder pageBuilder = new StringBuilder("asdhjk/sahdka");
        pageBuilder.append("?");
        for (String key : map.keySet()) {
            String value = map.get(key);
            pageBuilder.append(key + "=" + value + "&");
        }
        String page =pageBuilder.toString().substring(0, pageBuilder.length() - 1);
        log(page);

    }

    private static String getTime(int duration) {
        int oneMinute = 60;
        int oneHour = 3600;

        StringBuilder builder = new StringBuilder();

        long hour = duration / oneHour;//分
        long minutes = (duration % oneHour) / oneMinute;//分
        long seconds = (duration % oneHour) % oneMinute;//秒
        if (hour > 0) {
            if (hour < 10) {
                builder.append("0");
            }
            builder.append(hour).append(":");
        }
        if (minutes < 10) {
            builder.append("0");
        }
        builder.append(minutes).append(":");
        if (seconds < 10) {
            builder.append("0");
        }
        builder.append(seconds);
        return builder.toString();
    }

    private static void log(String msg) {
        System.out.println("日志>>:" + msg);
    }
}