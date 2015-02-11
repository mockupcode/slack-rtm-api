package com.mockupcode.slack.rtm.api;

import com.mockupcode.slack.rtm.api.json.connection.SlackInfo;
import com.mockupcode.slack.rtm.api.validation.SlackValidation;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
public class SlackWebsocketConnection implements SlackConnection {
    
    private SlackInfo slackInfo;

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
        slackInfo = new SlackAuthen().tokenAuthen(token, proxyUrl, proxyPort);
        ClientManager client = ClientManager.createClient();
        if (SlackValidation.getInstance().validateProxy(proxyUrl, proxyPort)) {
            client.getProperties().put(ClientProperties.PROXY_URI, proxyUrl + ":" + proxyPort);
        }
        return true;
    }

    @Override
    public SlackInfo getSlackInfo() {
        return slackInfo;
    }

}
