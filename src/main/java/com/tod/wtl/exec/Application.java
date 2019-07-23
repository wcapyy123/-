package com.tod.wtl.exec;

import io.swagger.api.TestApi;
import io.swagger.api.UserApi;
import org.apache.log4j.Logger;
import org.wso2.msf4j.MicroservicesRunner;

/**
 * @Author: liukai.
 * @Date: 2019-07-04 16:20 .
 * @Version 1.0 .
 */
public class Application {

    /**
     * 日志.
     */
    private static Logger logger = Logger.getLogger(Application.class.getName());

    /**
     * 启动函数.
     *
     * @param args .
     */
    public static void main(String[] args) {
        start(args);
    }

    /**
     * 启动服务.
     *
     * @param args .
     */
    private static void start(String[] args) {
        try {
            logger.info("Wit test server init .");
            MicroservicesRunner server = new MicroservicesRunner(8080);
            server.deploy(new UserApi());
            server.deploy(new TestApi());
            server.start();
            logger.info("Wit test server start success.");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 停止服务.
     *
     * @param args .
     */
    public static void stop(String[] args) {
        System.exit(0);
    }

}
