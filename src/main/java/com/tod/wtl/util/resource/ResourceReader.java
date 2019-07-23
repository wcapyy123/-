package com.tod.wtl.util.resource;

import com.alibaba.fastjson.JSONObject;
import com.tod.wtl.util.StringUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Set;

/**
 * 资源文件读取工具类，支持properties和json两种格式。
 */
public abstract class ResourceReader {

    private static Logger logger = Logger.getLogger(ResourceReader.class.getName());

    public static ResourceReader getReader(String path) {
        logger.info("ResourceReader.getReader invoke,path:" + path);
        if (StringUtil.isEmpty(path)) {
            return null;
        }
        String realPath = System.getProperty("user.dir");
        if (!path.startsWith(File.separator) && !realPath.endsWith(File.separator)) {
            realPath += File.separator;
        }
        realPath += path;
        logger.info("ResourceReader.getReader realPath:" + realPath);
        if (path.toLowerCase().endsWith(".json")) {
            return new JSONReader(realPath);
        }

        //如果没有扩展名，则默认为properties文件。
        return new PropertyReader(realPath);
    }

    public abstract JSONObject getFullJSON();

    public abstract int getInteger(String key);

    public abstract String getString(String key);

    public abstract Set<String> keySet();
}
