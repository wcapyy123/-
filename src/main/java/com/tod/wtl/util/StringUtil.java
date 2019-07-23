package com.tod.wtl.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class StringUtil {

    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    public static String getUUIDNoLine(){
        String uuid = getUUID();
        return uuid.replaceAll("\\-", "");
    }

    public static boolean isEmpty(String str) {
        return str == null || str.toLowerCase().equals("null") || str.trim().length() == 0;
    }

    public static boolean isEmpty(JSONObject json) {
        return json == null || json.isEmpty();
    }

    public static boolean isJSON(String str) {
        boolean result = true;

        if (isEmpty(str)) {
            result = false;
        }
        try {
            JSON.parseObject(str);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * 判断JSON对象是否包含一个以"."分隔的属性链。
     */
    public static boolean containsKeychain(JSONObject json, String keychain) {
        if (json == null && isEmpty(keychain)) {
            return false;
        }

        String[] keys = keychain.split("\\.");
        JSONObject curJson = json;
        for (int i = 0; i < keys.length; i++) {
            if (curJson == null) {

                return false;
            }
            if (!curJson.containsKey(keys[i])) {
                return false;
            }

            if (!(curJson.get(keys[i]) instanceof JSONObject)) {
                return i == keys.length - 1;
            }
            curJson = curJson.getJSONObject(keys[i]);
        }
        return true;
    }

    public static boolean checkJSONValue(JSONObject json, String key, String value) {
        if (json == null || key == null || !json.containsKey(key)) {
            return false;
        }

        return json.getString(key).equals(value);
    }

    public static void main(String[] args) {

/*        JSONObject json = new JSONObject();
        System.out.println(json.get("aaa"));

        String str = "spec.template.spec";

        String[] keys = str.split(".");

        for (String s : keys) {
            System.out.println(s);
        }*/

        String userOrgan = "北京市北京市东城区";
        if(getCount(userOrgan,"北京市")>1){
            userOrgan = userOrgan.substring(3);
        }
        System.out.println(userOrgan);
    }

    public static String getBase64(String str) {
        byte[] strByte = null;
        String strBase64 = null;
        try {
            strByte = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (strByte != null) {
            strBase64 = new BASE64Encoder().encode(strByte);
        }
        return strBase64.replaceAll("[\\s*\t\n\r]", "");
    }

    public static String getFromBase64(String str) {
        byte[] strByte = null;
        String result = null;
        if (str != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                strByte = decoder.decodeBuffer(str);
                result = new String(strByte, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static int getCount(String source, String sub) {
        int count = 0;
        int length = source.length() - sub.length();
        for (int i = 0; i < length; i++) {
            String sourceBak = source.substring(i, i + sub.length());
            int index = sourceBak.indexOf(sub);
            if (index != -1) {
                count++;
            }
        }
        return count;
    }
}
