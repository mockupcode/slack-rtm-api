package com.mockupcode.slack.rtm.api;

import java.net.Proxy;
import com.mockupcode.slack.rtm.api.validation.SlackValidation;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
public class Slack {
    
    private final SlackValidation slackValidation = SlackValidation.getInstance();
    
    private final String token;
    private Proxy.Type proxyType;
    private String proxyUrl;
    private int proxyPort;

    public Slack(String token) {
        slackValidation.validateToken(token);
        this.token = token;
    }
    
    public Slack setProxy(Proxy.Type proxyType,String proxyUrl,int proxyPort){
        slackValidation.validateProxy(proxyType, proxyUrl, proxyPort);
        this.proxyType = proxyType;
        this.proxyUrl = proxyUrl;
        this.proxyPort = proxyPort;
        return this;
    }
    
    public SlackConnection getConnection(){
        return new SlackWebsocketConnection(token, proxyType, proxyUrl, proxyPort);
    }
}
