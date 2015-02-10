package com.mockupcode.slack.rtm.api;

import java.net.Proxy;
import org.glassfish.tyrus.client.ClientManager;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
public class SlackWebsocketConnection implements SlackConnection{

    protected SlackWebsocketConnection(String token, Proxy.Type proxyType, String proxyUrl, int proxyPort) {
    }

    @Override
    public SlackConnection connect() {
        ClientManager client = ClientManager.createClient();
        return this;
    }
    
}
