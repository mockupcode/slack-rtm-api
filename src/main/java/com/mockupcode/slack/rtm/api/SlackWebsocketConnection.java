package com.mockupcode.slack.rtm.api;

import org.glassfish.tyrus.client.ClientManager;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
public class SlackWebsocketConnection implements SlackConnection{
    
    private final String token;
    private final String proxyUrl;
    private final int proxyPort;

    protected SlackWebsocketConnection(String token, String proxyUrl, int proxyPort) {
        this.token = token;
        this.proxyUrl = proxyUrl;
        this.proxyPort = proxyPort;
    }

    @Override
    public boolean connect() {
        ClientManager client = ClientManager.createClient();
        return true;
    }
    
}
