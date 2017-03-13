package com.springboot.common.util;

import com.google.gson.Gson;
import com.springboot.common.dto.ResponseDto;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zl on 2017/3/13.
 */
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String callServiceByPostForJson(String requestJson, String path) {
        final HttpClient httpClient = new HttpClient();
        // 链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(300000);
        // 读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(300000);
        final PostMethod method = new PostMethod(path);
        String contentType = "application/json; charset=UTF-8";
        String charset = "UTF-8";
        method.setRequestHeader("Content-type", contentType);
        String response = "";
        try {
            method.setRequestEntity(new StringRequestEntity(requestJson, contentType, charset));
            int status = httpClient.executeMethod(method);
            if (status >= 300 || status < 200) {
                logger.error("callservice error", method.getResponseBodyAsString());
                if (status == 400) {
                    Gson gson = new Gson();
                    ResponseDto dto = gson.fromJson(method.getResponseBodyAsString(), ResponseDto.class);
                } else {
                    logger.error("error message", method.getResponseBodyAsString());
                }
            }
            response = parserResponse(method);
        } catch (IOException e) {
            logger.error("callservice error", e);
        } finally {
            method.releaseConnection();
        }

        return response;
    }

    public static String callServiceByPost(NameValuePair[] param, String path) {
        final HttpClient httpClient = new HttpClient();
        // 链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(120000);
        // 读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(120000);
        final PostMethod method = new PostMethod(path);
        method.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
        method.setRequestBody(param);
        String response = "";
        try {
            int status = httpClient.executeMethod(method);
            if (status >= 300 || status < 200) {
                logger.error("callservice error", method.getResponseBodyAsString());
            }
            response = HttpUtil.parserResponse(method);
        } catch (IOException e) {
            logger.error("callservice error", e);
        } finally {
            method.releaseConnection();
        }
        return response;
    }

    public static String callServiceByGet(String path) {
        final HttpClient httpClient = new HttpClient();
        // 链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(120000);
        // 读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(120000);
        final GetMethod method = new GetMethod(path);
        method.getParams().setContentCharset("UTF-8");
        method.setRequestHeader("Content-type", "application/json; charset=UTF-8");
        String response = "";
        try {
            int status = httpClient.executeMethod(method);
            if (status >= 300 || status < 200) {
                if (status == 400) {
                    Gson gson = new Gson();
                    ResponseDto dto = gson.fromJson(method.getResponseBodyAsString(), ResponseDto.class);
                    logger.error("callservice 400", dto.getMessage());
                } else {
                    logger.error("callservice error 400", method.getResponseBodyAsString());
                }
            }
            response = parserResponse(method);
        } catch (IOException e) {
            logger.error("callservice error 503", e);
        } finally {
            method.releaseConnection();
        }
        return response;
    }

    /**
     * 解析http请求的response
     *
     * @param method
     * @return 请求结果
     * @throws IOException
     */
    public static String parserResponse(HttpMethodBase method) throws IOException {
        StringBuffer contentBuffer = new StringBuffer();
        InputStream in = method.getResponseBodyAsStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, method.getResponseCharSet()));
        String inputLine = null;
        while ((inputLine = reader.readLine()) != null) {
            contentBuffer.append(inputLine);
        }
        in.close();
        return contentBuffer.toString();
    }



}
