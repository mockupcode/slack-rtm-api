package com.mockupcode.slack.rtm.api;

import java.net.Proxy;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
public class SlackTest {

    public SlackTest() {
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

    @Test(expected = IllegalArgumentException.class)
    public void testGetSlackConnectionWithNullToken() {
        new Slack(null).getConnection();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSlackConnectionWithEmptyToken() {
        new Slack("").getConnection();
    }

    @Test
    public void testCreateSlackConnectionSuccess() {
        try {
            new Slack("token").getConnection();
        } catch (Exception e) {
            fail("have exception " + e);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSlackConnectionWithProxyButNotProvideProxyType() {
        new Slack("token").setProxy(null, "http://proxy.mockupcode.com", 8080).getConnection();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSlackConnectionWithProxyButNotProvideProxyUrl() {
        new Slack("token").setProxy(Proxy.Type.HTTP, null, 8080).getConnection();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSlackConnectionWithProxyButProvidePortLessThanZero() {
        new Slack("token").setProxy(Proxy.Type.HTTP, "http://proxy.mockupcode.com", -1);
    }

    @Test
    public void testCreateSlackConnectionWithProxySuccess() {
        try {
            new Slack("token").setProxy(Proxy.Type.HTTP, "http://proxy.mockupcode.com", 8080);
        } catch (Exception e) {
            fail("have exception " + e);
        }
    }

}
