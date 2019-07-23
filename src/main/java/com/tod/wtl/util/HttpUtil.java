package com.tod.wtl.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/8.
 */
public class HttpUtil {
    private static Logger logger = Logger.getLogger(HttpUtil.class.getName());
    /**
     * httpPost
     * @param url  路径
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam){
        return httpPost(url, jsonParam, false,"application/json");
    }

    public static JSONObject httpPostCustomType(String url, JSONObject jsonParam,String type){
        return httpPost(url, jsonParam, false,type);
    }

    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static JSONObject httpPost(String url,JSONObject jsonParam, boolean noNeedResponse,String type){
        //post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                //logger.info("^^^^^^^^^^^^^^^^^^^^^^^^^"+jsonParam.toString());
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType(type);
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /**把json字符串转换成json对象**/
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        return jsonResult;
    }


    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static JSONObject httpGet(String url){
        //get请求返回结果
        JSONObject jsonResult = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                jsonResult = JSONObject.parseObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
            }
        } catch (IOException e) {
        }
        return jsonResult;
    }

    /**
     * post请求内容被base64加密
     * @param url         url地址
     * @param jsonParam     参数
     * @return
     */
    public static JSONObject httpPostAddBase64(String url,JSONObject jsonParam){
        //post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                String param = StringUtil.getBase64(jsonParam.toJSONString());
                logger.info("httpPostAddBase64 entity:"+param);
                StringEntity entity = new StringEntity((param), "utf-8");
                entity.setContentEncoding("UTF-8");
                //entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            //url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据并解密**/
                    str = EntityUtils.toString(result.getEntity());
                    logger.info("httpPostAddBase64 resStr:"+str);
                    /**把json字符串转换成json对象**/
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        return jsonResult;
    }

    public static String httpUrlConnPost(String urlPath, byte[] data){
        // HttpClient 6.0被抛弃了
        String result = "";
        BufferedReader reader = null;
        try {

            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(100000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置文件类型:
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            // 设置接收类型否则返回415错误
            //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
            conn.setRequestProperty("accept","application/json");

            // 设置文件长度
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            OutputStream outwritestream = conn.getOutputStream();
            outwritestream.write(data);
            outwritestream.flush();
            outwritestream.close();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                result = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /*百度api提供*/
    public static String postToBaidu(String requestUrl, String accessToken, String params)
            throws Exception {
        String contentType = "application/x-www-form-urlencoded";
        return HttpUtil.postToBaidu(requestUrl, accessToken, contentType, params);
    }

    /*百度api提供*/
    public static String postToBaidu(String requestUrl, String accessToken, String contentType, String params)
            throws Exception {
        String encoding = "UTF-8";
        if (requestUrl.contains("nlp")) {
            encoding = "GBK";
        }
        return HttpUtil.postToBaidu(requestUrl, accessToken, contentType, params, encoding);
    }

    /*百度api提供*/
    public static String postToBaidu(String requestUrl, String accessToken, String contentType, String params, String encoding)
            throws Exception {
        String url = requestUrl + "?access_token=" + accessToken;
        return HttpUtil.postGeneralUrl(url, contentType, params, encoding);
    }

    /*百度api提供*/
    public static String postGeneralUrl(String generalUrl, String contentType, String params, String encoding)
            throws Exception {
        URL url = new URL(generalUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 得到请求的输出流对象
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.write(params.getBytes(encoding));
        out.flush();
        out.close();

        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> headers = connection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String key : headers.keySet()) {
            System.err.println(key + "--->" + headers.get(key));
        }
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = null;
        in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), encoding));
        String result = "";
        String getLine;
        while ((getLine = in.readLine()) != null) {
            result += getLine;
        }
        in.close();
        System.err.println("result:" + result);
        return result;
    }


    private static void testPushTask(){
        //从数据库中获取task
        String url = "http://localhost:8080/task/queryTask/";
        String param = "40284e816367a416016367a61c9f0003"+"/"+"null"+"/"+"null"+"/"+0+"/"+0;
        System.out.println(HttpUtil.httpGet(url+param));
        JSONObject js = new JSONObject();
        js.put("task", HttpUtil.httpGet(url+param).getJSONArray("data"));
        String url1 = "http://localhost:8080/task/pushTask";
        System.out.println(HttpUtil.httpPost(url1,js));
    }

    private static void testUploadTask(){
        JSONObject ob1 = new JSONObject();
        String a = "{\n" +
                "\"id\": \"40284e816361bc84016361bfa2800001\",\n" +
                "\"checkPeople\": [\"13585\", \"3491\"],\n" +
                "\"checkResult\": \"0\",\n" +
                "\"checkTime\": \"2018-05-09\",\n" +
                "\"checkTypeId\": \"1\",  \n" +
                "\"checkTypeName\": \"日常检查\",\n" +
                "\"content\": \"\",\n" +
                "\"entCode\": \"150000014899\",\n" +
                "\"imageUrls\": [],\n" +
                "\"initiator\": \"内蒙管理员\",\n" +
                "\"inspectionTaskId\": \"\",\n" +
                "\"isOrder\": \"1\",\n" +
                "\"modeContentid\": [\"e2784198cd594aeb8586b3ffab7fa5cf\", \"ea08aa53f444487e89cadfaf15cd8fbb\", \"7ab25240e5134bdc838a1711ba02aa2f\", \"5299e4f2099e400c83232f1d344b52a8\", \"148200a238e646148bd9f96152f00036\", \"8528c17b6f8a4ea89fdc8deefd197350\", \"8e54ee8175314c0b882666111e2ef92a\", \"430c8e82091344019c709add43f43ab9\", \"8d3119377613442681ce7f4e2e634eb0\", \"16b3c11a3e9a4722b15725c0d8a0ecf4\", \"69c6be73e5f643d78f95ef525652a221\", \"1424c947ecc4410c82f3b4646386639c\", \"a5c81be75c494386951e9d1ae8b85bf4\", \"d47241f9e5a746ec86aa960dec35c2c4\", \"7d14acde6dfb42fa89456f41eb74856e\", \"1c91b4d6bc2b409d81c2837fea460a84\", \"24db522823224ae99b08e393040f43c6\", \"5cbb687c067444cda073186ed15f4b9f\", \"5cd12a4c782842b38243bfb46a883bbf\", \"6fd018111e55447f89d0ce88ed07dec0\", \"4aee5fbbb561417495c67fee8aa73cf2\", \"6463cf77a23e49c8ae5103886c5766a3\", \"3453f13322ec46d8b125356e1b6809fc\", \"0bfbccc39f9142378dcd121424446446\", \"22bdba3ee1de4574a88318d632ea304c\", \"0f58e4473f514b3f8bb678915d11c4c6\", \"2e83dc184f4f437ca681b1011f3ba373\"],\n" +
                "\"remarks\": \"哈哈\",\n" +
                "\"scores\": [\"1\", \"0\", \"1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\", \"-1\"],\n" +
                "\"tableId\": \"8a901d185cca08c4015cfd5edc8e045e\",\n" +
                "\"taskState\": \"2\",\n" +
                "\"userId\": \"13585\"\n" +
                "}";
        JSONObject aa = new JSONObject();
        JSONObject cc = new JSONObject();
        aa = JSONObject.parseObject(a);
        aa.put("accountId","12345");
        JSONArray bb = new JSONArray();
        cc.put("notify","111");
        bb.add(cc);
        aa.put("result",bb);
        String url = "http://localhost:8080/taskResult/upload";
        System.out.println(HttpUtil.httpPost(url,aa));
    }

    private static void testGetTask(){
        JSONObject ob1 = new JSONObject();
        String url = "http://localhost:8080/task/download/"+"13585"+"/"+0+"/"+0;
        JSONArray b = HttpUtil.httpGet(url).getJSONArray("data");
        System.out.println(b.toString());
    }

    private static void testLogin(){
        JSONObject ob1 = new JSONObject();
        ob1.put("account","essence");
        ob1.put("password","itouch123");
        String url = "http://localhost:8118/user/login";
        System.out.println(HttpUtil.httpPost(url,ob1));
    }

    public static void main(String[] args) {
        //HttpUtil.testPushTask();
        //HttpUtil.testUploadTask();
        //HttpUtil.testGetTask();
        HttpUtil.testLogin();
        //HttpUtil.testgetTypes();
        //HttpUtil.testFaceLogin();
    }
}
