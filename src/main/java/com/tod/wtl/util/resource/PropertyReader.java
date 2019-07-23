package com.tod.wtl.util.resource;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.*;

public class PropertyReader extends ResourceReader {
    private ResourceBundle bundle;

    private Properties properties;

    public PropertyReader(String path) {
        try {
            InputStream is = new FileInputStream(new File(path));
            bundle = new PropertyResourceBundle(is);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        properties = new Properties();
        Iterator<String> it = bundle.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            properties.put(key,bundle.getString(key));
        }
    }

    public Properties getProperties(){
        return properties;
    }

    @Override
    public JSONObject getFullJSON() {
        return null;
    }

    @Override
    public int getInteger(String key) {
        return Integer.parseInt(bundle.getString(key));
    }

    @Override
    public String getString(String key) {
        return bundle.getString(key);
    }

    @Override
    public Set<String> keySet() {
        return bundle.keySet();
    }
}
