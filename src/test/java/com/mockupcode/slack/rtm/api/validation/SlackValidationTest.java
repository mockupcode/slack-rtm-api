package com.mockupcode.slack.rtm.api.validation;

import static org.hamcrest.core.Is.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
public class SlackValidationTest {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private static SlackValidation slackValidation;
    
    @BeforeClass
    public static void setUpClass() {
        slackValidation = SlackValidation.getInstance();
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
    public void testGetInstance() {
        assertNotNull(slackValidation);
    }
    
    @Test
    public void testTokenValidateWithNullToken() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("token can not be null");
        slackValidation.validateToken(null);
    }
    
    @Test
    public void testTokenValidateWithEmptyToken() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("token can not be empty");
        slackValidation.validateToken("");
    }
    
    @Test
    public void testProxyWhenNullUrl() {
        boolean result = slackValidation.validateProxy(null, -1);
        assertThat(result, is(false));
    }
    
    @Test
    public void testProxyWhenPortLessthanZero(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("proxy port must provide");
        slackValidation.validateProxy("http://proxy.mockupcode.com", -1);
    }
    
    @Test
    public void testProxySuccess(){
        boolean validateProxy = slackValidation.validateProxy("http://proxy.mockupcode.com", 8080);
        assertThat(validateProxy, is(true));
    }
}
