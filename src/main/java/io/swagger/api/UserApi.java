package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.UserApiService;
import io.swagger.api.factories.UserApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.Info;
import io.swagger.model.ReturnModel;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import org.wso2.msf4j.formparam.FormDataParam;
import org.wso2.msf4j.formparam.FileInfo;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;

@Path("/user")
@Consumes({ "application/json", "application/text", "text/plain", "multipart/form-data", "application/x-www-form-urlencoded" })
@Produces({ "application/json", "application/text", "text/plain" })
@io.swagger.annotations.Api(description = "the user API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaMSF4JServerCodegen", date = "2019-07-05T05:08:30.172Z")
public class UserApi  {
   private final UserApiService delegate = UserApiServiceFactory.getUserApi();

    @POST
    @Path("/login")
    @Consumes({ "application/json", "application/text", "text/plain", "multipart/form-data", "application/x-www-form-urlencoded" })
    @Produces({ "application/json", "application/text", "text/plain" })
    @io.swagger.annotations.ApiOperation(value = "登录", notes = "用户登录", response = ReturnModel.class, tags={ "test", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = ReturnModel.class) })
    public Response userLoginPost(@ApiParam(value = "用户信息" ,required=true) Info info
)
    throws NotFoundException {
        return delegate.userLoginPost(info);
    }
}
