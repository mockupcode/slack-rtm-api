/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockupcode.slack.rtm.api.websocket;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
public class WebSocketEndpoint extends Endpoint{

    @Override
    public void onOpen(final Session session, EndpointConfig config) {
        
        session.addMessageHandler(new MessageHandler.Whole<String>() {

            @Override
            public void onMessage(String message) {
                System.out.println(">>>>>> " + message);
            }
        });
    }

}
