package com.mockupcode.slack.rtm.api;

import com.mockupcode.slack.rtm.api.websocket.WebSocketEndpoint;
import com.mockupcode.slack.rtm.api.json.connection.SlackInfo;
import java.net.URI;
import javax.websocket.Endpoint;
import javax.websocket.Session;
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
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"org.glassfish.tyrus.spi.*","org.glassfish.tyrus.container.*"})
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
        slackInfo.setUrl("wss://ms21.slack-msgs.com/websocket/oITm2uI4nAa9W1MtuOmHJeo6WQkNl3hU9rBNYXmwQWD9QpfLoe_HUIpkYgq22SvHmOHTtC_ld1046BAAPADLWT92L8DC/aE1J85MwGiaOhg=");

        //mock
        SlackAuthen slackAuthen = PowerMockito.mock(SlackAuthen.class);
        PowerMockito.mockStatic(ClientManager.class);
        WebSocketEndpoint endpoint = PowerMockito.mock(WebSocketEndpoint.class);
        Session session = PowerMockito.mock(Session.class);

        //inject
        PowerMockito.whenNew(SlackAuthen.class).withNoArguments().thenReturn(slackAuthen);
        PowerMockito.when(slackAuthen.tokenAuthen(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(slackInfo);
        PowerMockito.when(ClientManager.createClient()).thenReturn(clientManager);
        
        //spy
        ClientManager clientManagerSpy = Mockito.spy(clientManager);
        Mockito.doReturn(session).when(clientManagerSpy).connectToServer(Mockito.any(WebSocketEndpoint.class), Mockito.any(URI.class));

        //assert
        boolean connected = conn.connect();
        assertThat(connected, is(true));
        assertThat(conn.getSlackInfo().isOk(),is(true));

        //verify
        Mockito.verify(slackAuthen).tokenAuthen(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt());
        //Mockito.verify(clientManagerSpy).connectToServer(endpoint, URI.create(slackInfo.getUrl()));
        
        //verify static
        PowerMockito.verifyStatic();
        ClientManager.createClient();
    }

}
