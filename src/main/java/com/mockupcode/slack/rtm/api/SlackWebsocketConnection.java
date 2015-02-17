package com.mockupcode.slack.rtm.api;

import com.mockupcode.slack.rtm.api.json.connection.SlackInfo;
import com.mockupcode.slack.rtm.api.json.message.SlackMessage;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
public class SlackWebsocketConnection implements SlackConnection {
    
    private SlackInfo slackInfo;
    private Session session;
    private final List<SlackListener> slackListeners = new ArrayList<>();
    
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
        if (proxyUrl != null) {
            client.getProperties().put(ClientProperties.PROXY_URI, proxyUrl + ":" + proxyPort);
        }
        try {
            session = client.connectToServer(new Endpoint() {
                
                @Override
                public void onOpen(Session session, EndpointConfig config) {
                    session.addMessageHandler(new MessageHandler.Whole<String>() {
                        
                        @Override
                        public void onMessage(String message) {
                            for (SlackListener slackListener : slackListeners) {
                                slackListener.onMessage(new SlackMessage(message));
                            }
                        }
                    });
                }
            }, URI.create(slackInfo.getUrl()));
            await();
        } catch (DeploymentException | IOException ex) {
            Logger.getLogger(SlackWebsocketConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    @Override
    public SlackInfo getSlackInfo() {
        return slackInfo;
    }
    
    private void await() {
        Thread thread = new Thread(new Runnable() {
            
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SlackWebsocketConnection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        thread.start();
    }
    
    @Override
    public void addSlackListener(SlackListener slackListener) {
        if (!slackListeners.contains(slackListener)) {
            slackListeners.add(slackListener);
        }
    }
    
}
