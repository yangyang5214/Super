package com.springboot.common.util;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zl on 2017/3/10.
 */
@Component
public class EmailUtil {


    @Value("${email.encoding:}")
    private String encoding;

    @Value("${email.host:}")
    private String host;

    @Value("${email.username:}")
    private String username;

    @Value("${email.password:}")
    private String password;

    public void sendEmail(String receiver, String subject, String msg , List<String> filepath) {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setHostName(host);
//			email.setSmtpPort(port);
            email.setAuthentication(username, password);
            email.setCharset(encoding);
            email.addTo(receiver.split(","));
            email.setFrom(username);
            email.setSubject(subject);
            email.setMsg(msg);
            email.setSSLOnConnect(true);
            if(Collections3.isNotEmpty(filepath)){
                for(int i=0; i<filepath.size();i++){
                    EmailAttachment attac = new EmailAttachment();
                    attac.setPath(filepath.get(i));
                    email.attach(attac);
                }
            }
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
