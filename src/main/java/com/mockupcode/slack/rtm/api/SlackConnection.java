package com.mockupcode.slack.rtm.api;

import com.mockupcode.slack.rtm.api.listener.SlackListener;
import com.mockupcode.slack.rtm.api.json.connection.SlackInfo;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
public interface SlackConnection {
    public boolean connect();
    public SlackInfo getSlackInfo();
    public void addSlackListener(SlackListener slackListener);
}
