package com.mockupcode.slack.rtm.api;

import com.mockupcode.slack.rtm.api.json.connection.SlackInfo;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
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
    public void testAuthenWithToken() {
        HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod("https://slack.com/api/rtm.start?token=xoxp-3431624407-3447796844-3634940065-120096");

        try {
            int executeMethod = client.executeMethod(getMethod);
            if (executeMethod == HttpStatus.SC_OK) {
                ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
                mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                SlackInfo readValue = mapper.readValue(getMethod.getResponseBodyAsStream(), SlackInfo.class);
                System.out.println(readValue.isOk());
            }
        } catch (Exception ex) {
            Logger.getLogger(SlackWebsocketConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
