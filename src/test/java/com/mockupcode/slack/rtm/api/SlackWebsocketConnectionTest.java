package com.mockupcode.slack.rtm.api;

import com.mockupcode.slack.rtm.api.websocket.WebSocketEndpoint;
import com.mockupcode.slack.rtm.api.json.connection.SlackInfo;
import java.net.URI;
import java.util.Map;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import static org.hamcrest.core.Is.*;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"org.glassfish.tyrus.spi.*", "org.glassfish.tyrus.container.*"})
@PrepareForTest({SlackWebsocketConnection.class, SlackAuthen.class, ClientManager.class})
public class SlackWebsocketConnectionTest {

    static SlackInfo slackInfo;

    public SlackWebsocketConnectionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        slackInfo = new SlackInfo();
        slackInfo.setOk(true);
        slackInfo.setUrl("wss://ms21.slack-msgs.com");
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
    public void testAuthenBeforeConnect() throws Exception {
        SlackWebsocketConnection slack = new SlackWebsocketConnection("token", null, 8080);

        SlackAuthen slackAuthen = PowerMockito.mock(SlackAuthen.class);
        PowerMockito.whenNew(SlackAuthen.class).withNoArguments().thenReturn(slackAuthen);
        PowerMockito.when(slackAuthen.tokenAuthen(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(slackInfo);

        ClientManager clientManager = PowerMockito.mock(ClientManager.class);
        PowerMockito.mockStatic(ClientManager.class);
        PowerMockito.when(ClientManager.createClient()).thenReturn(clientManager);

        boolean connect = slack.connect();
        assertThat(connect, is(true));
        assertThat(slack.getSlackInfo().isOk(), is(true));

        Mockito.verify(slackAuthen).tokenAuthen(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt());
    }

    @Test
    public void testCreateWebSoketClient() throws Exception {
        SlackWebsocketConnection slack = new SlackWebsocketConnection("token", null, 8080);

        SlackAuthen slackAuthen = PowerMockito.mock(SlackAuthen.class);
        PowerMockito.whenNew(SlackAuthen.class).withNoArguments().thenReturn(slackAuthen);
        PowerMockito.when(slackAuthen.tokenAuthen(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(slackInfo);

        ClientManager clientManager = PowerMockito.mock(ClientManager.class);
        PowerMockito.mockStatic(ClientManager.class);
        PowerMockito.when(ClientManager.createClient()).thenReturn(clientManager);

        boolean connect = slack.connect();
        assertThat(connect, is(true));

        PowerMockito.verifyStatic();
        ClientManager.createClient();
    }

    @Test
    public void testCreateWebSoketClientWithProxy() throws Exception {
        SlackWebsocketConnection slack = new SlackWebsocketConnection("token", "proxy", 8080);

        SlackAuthen slackAuthen = PowerMockito.mock(SlackAuthen.class);
        PowerMockito.whenNew(SlackAuthen.class).withNoArguments().thenReturn(slackAuthen);
        PowerMockito.when(slackAuthen.tokenAuthen(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(slackInfo);

        ClientManager clientManager = PowerMockito.mock(ClientManager.class);
        PowerMockito.mockStatic(ClientManager.class);
        PowerMockito.when(ClientManager.createClient()).thenReturn(clientManager);
        Map<String, Object> properties = Mockito.mock(Map.class);
        PowerMockito.when(clientManager.getProperties()).thenReturn(properties);

        boolean connect = slack.connect();
        assertThat(connect, is(true));

        Mockito.verify(clientManager.getProperties()).put(Mockito.eq(ClientProperties.PROXY_URI), Mockito.anyString());
        PowerMockito.verifyStatic();
        ClientManager.createClient();
    }

    @Test
    public void testCreateWebSocketAndConnectToServer() throws Exception {
        SlackWebsocketConnection slack = new SlackWebsocketConnection("token", null, 8080);

        SlackAuthen slackAuthen = PowerMockito.mock(SlackAuthen.class);
        PowerMockito.whenNew(SlackAuthen.class).withNoArguments().thenReturn(slackAuthen);
        PowerMockito.when(slackAuthen.tokenAuthen(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(slackInfo);

        ClientManager clientManager = PowerMockito.mock(ClientManager.class);
        PowerMockito.mockStatic(ClientManager.class);
        PowerMockito.when(ClientManager.createClient()).thenReturn(clientManager);

        boolean connect = slack.connect();
        assertThat(connect, is(true));

        Mockito.verify(clientManager).connectToServer(Mockito.any(WebSocketEndpoint.class), Mockito.any(URI.class));
        PowerMockito.verifyStatic();
        ClientManager.createClient();
    }
    
    @Test
    public void testCreateWaitThreadAfterConnect() throws Exception{
        SlackAuthen slackAuthen = PowerMockito.mock(SlackAuthen.class);
        PowerMockito.whenNew(SlackAuthen.class).withNoArguments().thenReturn(slackAuthen);
        PowerMockito.when(slackAuthen.tokenAuthen(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(slackInfo);
        
        ClientManager clientManager = PowerMockito.mock(ClientManager.class);
        PowerMockito.mockStatic(ClientManager.class);
        PowerMockito.when(ClientManager.createClient()).thenReturn(clientManager);
        
        SlackWebsocketConnection slackWebsocketConnection = PowerMockito.spy(new SlackWebsocketConnection("token", null, 8080));
        slackWebsocketConnection.connect();
        PowerMockito.doNothing().when(slackWebsocketConnection,"await");
        PowerMockito.verifyPrivate(slackWebsocketConnection, VerificationModeFactory.atLeastOnce()).invoke("await");
    }

}
