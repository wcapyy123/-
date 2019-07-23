package com.tod.wtl.util.resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Set;

/**
 * Created by LiuShaofeng on 2017/3/22.
 */
public class JSONReader extends ResourceReader {

    private JSONObject json;

    public JSONReader(String path) {
        loadFile(path);
    }

    private void loadFile(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            StringBuffer jsonBuffer = new StringBuffer();
            String s = null;
            while ((s = br.readLine()) != null) {
                jsonBuffer.append(s.trim());
            }
            br.close();
            json = JSON.parseObject(jsonBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject getFullJSON() {
        return json;
    }

    @Override
    public int getInteger(String key) {
        return json.getInteger(key);
    }

    @Override
    public String getString(String key) {
        return json.getString(key);
    }

    @Override
    public Set<String> keySet() {
        return json.keySet();
    }
}
