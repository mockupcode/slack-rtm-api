/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockupcode.slack.rtm.api;

import com.mockupcode.slack.rtm.api.json.connection.SlackInfo;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.easymock.EasyMock;
import static org.hamcrest.core.Is.*;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.net.ssl.*", "javax.net.*"})
@PrepareForTest({SlackAuthen.class, HttpClient.class, GetMethod.class})
public class SlackAuthenTest {

    public SlackAuthenTest() {
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
    public void testTokenAuthenWithInvalidToken() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("{\"ok\":false}".getBytes());

        HttpClient httpClient = PowerMock.createMock(HttpClient.class);
        GetMethod getMethod = PowerMock.createMock(GetMethod.class);

        PowerMock.expectNew(HttpClient.class).andReturn(httpClient);
        PowerMock.expectNew(GetMethod.class, "https://slack.com/api/rtm.start?token=token").andReturn(getMethod);

        EasyMock.expect(httpClient.executeMethod(getMethod)).andReturn(HttpStatus.SC_OK);
        EasyMock.expect(getMethod.getResponseBodyAsStream()).andReturn(inputStream);
        getMethod.releaseConnection();

        PowerMock.replay(httpClient, HttpClient.class, getMethod, GetMethod.class);

        SlackInfo tokenAuthen = new SlackAuthen().tokenAuthen("token",null,0);
        assertThat(tokenAuthen.isOk(), is(false));
    }

    @Test
    public void testTokenAuthenSuccess() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("{\"ok\":true}".getBytes());

        HttpClient httpClient = PowerMock.createMock(HttpClient.class);
        GetMethod getMethod = PowerMock.createMock(GetMethod.class);

        PowerMock.expectNew(HttpClient.class).andReturn(httpClient);
        PowerMock.expectNew(GetMethod.class, "https://slack.com/api/rtm.start?token=token").andReturn(getMethod);

        EasyMock.expect(httpClient.executeMethod(getMethod)).andReturn(HttpStatus.SC_OK);
        EasyMock.expect(getMethod.getResponseBodyAsStream()).andReturn(inputStream);
        getMethod.releaseConnection();

        PowerMock.replay(httpClient, HttpClient.class, getMethod, GetMethod.class);

        SlackInfo tokenAuthen = new SlackAuthen().tokenAuthen("token",null,0);
        assertThat(tokenAuthen.isOk(), is(true));
    }

}
