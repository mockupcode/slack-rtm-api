package com.mockupcode.slack.rtm.api;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
public class SlackWebsocketConnectionTest {

    public SlackWebsocketConnectionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testConnect() {
        SlackConnection conn;
        conn = new SlackWebsocketConnection("token","http://proxy.mockupcode.com", 8080);
        boolean connected = conn.connect();
        Assert.assertThat(connected,Is.is(true));
    }

}
