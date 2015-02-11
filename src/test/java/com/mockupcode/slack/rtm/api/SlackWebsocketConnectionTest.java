package com.mockupcode.slack.rtm.api;

import com.mockupcode.slack.rtm.api.json.connection.SlackInfo;
import org.glassfish.tyrus.client.ClientManager;
import static org.hamcrest.core.Is.*;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SlackWebsocketConnection.class, SlackAuthen.class, ClientManager.class})
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
    public void testConnect() throws Exception {
        //prepare
        SlackConnection conn = new SlackWebsocketConnection("token", "http://proxy.mockupcode.com", 8080);
        ClientManager clientManager = ClientManager.createClient();
        SlackInfo slackInfo = new SlackInfo();
        slackInfo.setOk(true);

        //mock
        SlackAuthen slackAuthen = PowerMockito.mock(SlackAuthen.class);
        PowerMockito.mockStatic(ClientManager.class);

        //inject
        PowerMockito.whenNew(SlackAuthen.class).withNoArguments().thenReturn(slackAuthen);

        //invoke
        PowerMockito.when(slackAuthen.tokenAuthen("token", "http://proxy.mockupcode.com", 8080)).thenReturn(slackInfo);
        PowerMockito.when(ClientManager.createClient()).thenReturn(clientManager);

        //assert
        boolean connected = conn.connect();
        assertThat(connected, is(true));
        assertThat(conn.getSlackInfo().isOk(),is(true));

        //verify
        Mockito.verify(slackAuthen).tokenAuthen("token", "http://proxy.mockupcode.com", 8080);
        PowerMockito.verifyStatic();
        ClientManager.createClient();
    }

}
