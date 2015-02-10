package com.mockupcode.slack.rtm.api.validation;

import java.net.Proxy;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
public class SlackValidation {
    
    private static final SlackValidation slackValidation = new SlackValidation();
    
    private SlackValidation(){
    }
    
    public static SlackValidation getInstance(){
        return slackValidation;
    }
    
    public String validateToken(String token){
        if(token == null){
            throw new IllegalArgumentException("token can not be null");
        }
        if(token.isEmpty()){
            throw new IllegalArgumentException("token can not be empty");
        }
        return token;
    }

    public boolean validateProxy(Proxy.Type type, String url, int port) {
        if(url != null && type == null){
            throw new IllegalArgumentException("proxy type must provide");
        }
        if(url == null && type != null){
            throw new IllegalArgumentException("proxy url must provide");
        }
        if(url != null && port < 0){
            throw new IllegalArgumentException("proxy port must provide");
        }
        return !(url == null && type == null && port < 0);
    }
}
