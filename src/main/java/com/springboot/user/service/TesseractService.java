package com.springboot.user.service;

import com.springboot.common.dao.BaseDao;
import com.springboot.common.util.OCRUtil;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import com.springboot.user.entity.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;

/**
 * Created by zl on 2017/3/9.
 */
@Service
public class TesseractService {

    public String URL_BASE = "http://61.167.199.232/";  // 基础地址
    public static final String URL_CODE = "verifycode.servlet"; // 验证码地址
    public static final String URL_LOGIN = "Logon.do?method=logon";  // 登陆地址
    public static final String URL_MAIN = "framework/main.jsp";  // 登录成功的首地址
    public static final String URL_LOGIN2 = "Logon.do?method=logonBySSO";
    public static final String URL_CJ = "xszqcjglAction.do?method=queryxscj";
    public static final String ROOTFOLDER = "D:\\temp\\imageCode";
    public static HttpClient client = new HttpClient();
    public static StringBuffer cookie = null;
    public static String code = null;
    public static int status;

    private Logger logger = LoggerFactory.getLogger(TesseractService.class);

    @Autowired
    private BaseDao baseDao;

    private Boolean login(String username, String password){
        getCookie();
        for (int i= 0;i<10;i++){
            getCode();
            status = loginByPwd(username,password);
            if (status == 302){
                return true;
            }
            if (i==10){
               return false;
            }
        }
        return false;
    }

    public Boolean saveNamePwd(String username, String password){
        Boolean isSuccess = login(username,password);
        if (isSuccess){
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            baseDao.persist(user);
        }
        return isSuccess;
    }

    private int loginByPwd(String username, String password){
        PostMethod post = new PostMethod(URL_BASE + URL_LOGIN);
        NameValuePair[] data = {
                new NameValuePair("USERNAME", username),
                new NameValuePair("PASSWORD", password),
                new NameValuePair("useDogCode", ""),
                new NameValuePair("useDogCode", ""),
                new NameValuePair("RANDOMCODE", code),
                new NameValuePair("x", +(int) (Math.random() * 1000 % 62) + ""),
                new NameValuePair("y", +(int) (Math.random() * 1000 % 22) + "")
        };
        post.setRequestHeader("Cookie", cookie.toString());
        post.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        post.setRequestHeader("Accept-Encoding", "gzip, deflate");
        post.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
        post.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
        post.setRequestHeader("Cache-Control", "no-cache");
        post.setRequestHeader("Content-Length", "87");
        post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        post.setRequestHeader("Host", "61.167.199.232");
        post.setRequestHeader("Origin", "http://61.167.199.232");
        post.setRequestHeader("Pragma", "no-cache");
        post.setRequestHeader("Referer:http", "//61.167.199.232/Logon.do?method=logon");
        post.setRequestHeader("Upgrade-Insecure-Requests", "1");
        post.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
        post.setRequestBody(data);
        try {
            status = client.executeMethod(post);
            return status;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void getCookie(){
        GetMethod getHttp = new GetMethod(URL_BASE);
        cookie = new StringBuffer();
        try {
            client.executeMethod(getHttp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Cookie[] cookies = client.getState().getCookies();
        for (Cookie c : cookies) {
            cookie.append(c.toString());
        }
    }

    private void getCode(){
        GetMethod get = new GetMethod(URL_BASE + URL_CODE);
        get.setRequestHeader("Cookie", cookie.toString());
        try {
            client.executeMethod(get);
            InputStream inputStream = get.getResponseBodyAsStream();
            File imageFile = saveImageCode(inputStream);
            try {
                code = new OCRUtil().recognizeText(imageFile);
            } catch (IOException e) {
                logger.error("get code error",e);
            } catch (Exception e) {
                logger.error("file error",e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File saveImageCode(InputStream is) {
        UUID uuid = UUID.randomUUID();
        String fileName = ROOTFOLDER + "/code-" + uuid.toString() + ".png";
        File file = new File(fileName);
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            byte buffer[] = new byte[4 * 1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
