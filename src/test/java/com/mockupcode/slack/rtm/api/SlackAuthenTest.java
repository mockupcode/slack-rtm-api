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
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertThat;
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
@PowerMockIgnore({"javax.net.ssl.*", "javax.net.*"})
@PrepareForTest({SlackAuthen.class, HttpClient.class, GetMethod.class})
public class SlackAuthenTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public SlackAuthenTest() {
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

        HttpClient httpClient = PowerMockito.mock(HttpClient.class);
        GetMethod getMethod = PowerMockito.mock(GetMethod.class);

        PowerMockito.whenNew(HttpClient.class).withNoArguments().thenReturn(httpClient);
        PowerMockito.whenNew(GetMethod.class).withAnyArguments().thenReturn(getMethod);

        Mockito.when(httpClient.executeMethod(getMethod)).thenReturn(HttpStatus.SC_OK);
        Mockito.when(getMethod.getResponseBodyAsStream()).thenReturn(inputStream);

        SlackInfo tokenAuthen = new SlackAuthen().tokenAuthen("token", null, 0);
        assertThat(tokenAuthen.isOk(), is(false));
    }

    @Test
    public void testTokenAuthenSuccess() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("{\"ok\":true}".getBytes());

        HttpClient httpClient = PowerMockito.mock(HttpClient.class);
        GetMethod getMethod = PowerMockito.mock(GetMethod.class);

        PowerMockito.whenNew(HttpClient.class).withNoArguments().thenReturn(httpClient);
        PowerMockito.whenNew(GetMethod.class).withAnyArguments().thenReturn(getMethod);

        Mockito.when(httpClient.executeMethod(getMethod)).thenReturn(HttpStatus.SC_OK);
        Mockito.when(getMethod.getResponseBodyAsStream()).thenReturn(inputStream);

        SlackInfo tokenAuthen = new SlackAuthen().tokenAuthen("token", null, 0);
        assertThat(tokenAuthen.isOk(), is(true));
    }

    @Test
    public void testTokenAuthenNotHttpOK() throws Exception {
        HttpClient httpClient = PowerMockito.mock(HttpClient.class);

        PowerMockito.whenNew(HttpClient.class).withNoArguments().thenReturn(httpClient);
        Mockito.when(httpClient.executeMethod(Mockito.any(GetMethod.class))).thenReturn(HttpStatus.SC_BAD_GATEWAY);

        SlackInfo tokenAuthen = new SlackAuthen().tokenAuthen("token", null, 0);
        assertThat(tokenAuthen.isOk(), is(false));
        assertThat(tokenAuthen.getError(), equalTo("http_status_" + HttpStatus.SC_BAD_GATEWAY));
    }
}
