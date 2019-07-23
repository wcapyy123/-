package com.tod.wtl.service;

import io.swagger.api.NotFoundException;
import io.swagger.api.TestApiService;
import io.swagger.model.ReturnModel;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import javax.ws.rs.core.Response;

/**
 * @Author: liukai.
 * @Date: 2019-07-04 18:43 .
 * @Version 1.0 .
 */
public class  TestServiceBase extends TestApiService {

    private Logger logger = Logger.getLogger(TestApiService.class.getName());

    @Override
    public Response testLinkInfoGet(String info) throws NotFoundException {
        logger.info("testLinkInfoGet info :" + info);
        ReturnModel returnModel = new ReturnModel();

        returnModel.code("0"); // 返回状态码
        returnModel.errMsg("SUCCESS"); //返回状态说明信息
        returnModel.data(info); //返回数据

        return Response.status(200)
                .entity(returnModel).build();
    }
}
