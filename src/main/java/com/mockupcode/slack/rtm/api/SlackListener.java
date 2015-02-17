/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockupcode.slack.rtm.api;

import com.mockupcode.slack.rtm.api.json.message.SlackMessage;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
public interface SlackListener {
    public void onMessage(SlackMessage slackMessage);
}
