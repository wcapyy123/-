package com.tod.wtl.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tod.wtl.util.HttpUtil;
import com.tod.wtl.util.StringUtil;
import io.swagger.api.NotFoundException;
import io.swagger.api.UserApiService;
import io.swagger.model.Info;
import io.swagger.model.ReturnModel;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;

/**
 * @Author: liukai.
 * @Date: 2019-07-05 13:11 .
 * @Version 1.0 .
 */
public class UserServiceBase extends UserApiService {
    /**
     * 日志.
     */
    private Logger logger = Logger.getLogger(UserServiceBase.class.getName());

    private static int SUCCESS_STATUS = 200;

    private static String LOGIN_FAILED = "0";
    private static String LOGIN_FAILED_MSG = "登录失败";

    private static String LOGIN_SUCCESS = "1";
    private static String LOGOUT_SUCCESS_MSG = "登录成功";

    @Override
    public Response userLoginPost(Info info) throws NotFoundException {
        ReturnModel returnModel = new ReturnModel();
        logger.info("login user : " + info);
        String data = info.getData();
        JSONObject user = JSONObject.parseObject(data);
        try {
            // 检查是否有字段缺失
            JSONObject userInfoResult = new JSONObject();
            if (StringUtil.isEmpty(user) || !user.containsKey("account")
                    || !user.containsKey("password")) {
                logger.error("登录参数缺失 account || password || clientId");
                returnModel.code(LOGIN_FAILED);
                returnModel.errMsg(LOGIN_FAILED_MSG);
                return Response.status(SUCCESS_STATUS)
                        .entity(new ReturnModel()).build();
            }
            // 检查是否有ClientID
            String userName = user.getString("account");
            String password = user.getString("password");
            String clientId = "wtl_gx_demo";

            if (StringUtil.isEmpty(clientId)) {
                logger.error(LOGIN_FAILED_MSG);
                returnModel.code(LOGIN_FAILED);
                returnModel.errMsg(LOGIN_FAILED_MSG);
                return Response.status(SUCCESS_STATUS)
                        .entity(new ReturnModel()).build();
            }
            // 根据用户名密码和客户端id发送请求给数据中心,获得token、refreshtoken和userId
            String checkUrl = "https://iam.irsaas.com/apigw/common/LoginApi/upwd";
            JSONObject checkPasswordParam = new JSONObject();
            checkPasswordParam.put("username", userName);
            checkPasswordParam.put("password", password);
            checkPasswordParam.put("resource", clientId);
            logger.info("login with data center param:" + checkPasswordParam.toJSONString());
            //根据用户名、密码和客户端id获取token、refreshtoken和userId
            JSONObject responseObj = HttpUtil.httpPostAddBase64(checkUrl, checkPasswordParam);
            logger.info("login with data center param responseTokenAndId:" + responseObj);
            // 返回数据中心错误
            if (StringUtil.isEmpty(responseObj) || !responseObj.containsKey("ReasonCode") || !responseObj.containsKey("Data")) {
                logger.error(LOGIN_FAILED_MSG);
                returnModel.code(LOGIN_FAILED);
                returnModel.errMsg(LOGIN_FAILED_MSG);
                return Response.status(SUCCESS_STATUS)
                        .entity(new ReturnModel()).build();
            }
            // 取出数据
            JSONObject dcData = JSON.parseObject(StringUtil.getFromBase64(responseObj.getString("Data")));
            String userId = dcData.getString("userId");
            userInfoResult.putAll(dcData);
            returnModel.code(LOGIN_SUCCESS);
            returnModel.errMsg(LOGOUT_SUCCESS_MSG);
            returnModel.data(userInfoResult);
            return Response.status(SUCCESS_STATUS)
                    .entity(returnModel).build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            returnModel.code(LOGIN_FAILED);
            returnModel.errMsg(LOGIN_FAILED_MSG);
            return Response.status(SUCCESS_STATUS)
                    .entity(new ReturnModel()).build();
        }
    }
}
