package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.TestApiService;
import io.swagger.api.factories.TestApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

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

@Path("/test")
@Consumes({ "application/json", "application/text", "text/plain", "multipart/form-data", "application/x-www-form-urlencoded" })
@Produces({ "application/json", "application/text", "text/plain" })
@io.swagger.annotations.Api(description = "the test API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaMSF4JServerCodegen", date = "2019-07-04T10:03:07.723Z")
public class TestApi  {
   private final TestApiService delegate = TestApiServiceFactory.getTestApi();

    @GET
    @Path("/link/{info}")
    @Consumes({ "application/json", "application/text", "text/plain", "multipart/form-data", "application/x-www-form-urlencoded" })
    @Produces({ "application/json", "application/text", "text/plain" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "连接测试", response = ReturnModel.class, tags={ "test", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = ReturnModel.class) })
    public Response testLinkInfoGet(@ApiParam(value = "信息",required=true) @PathParam("info") String info
)
    throws NotFoundException {
        return delegate.testLinkInfoGet(info);
    }
}
