package com.techdevs.practis;


import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class LoginUnitTest {
    @Test
    public void testIsEmailValid() {
        String testEmail = "name@email.com";
        Assert.assertThat(String.format("Email Validity Test failed for %s ", testEmail), Login.checkEmailForValidity(testEmail), is(true));
    }
    @Test
    public void testIsEmailInvalid() {
        String testEmail = "name@emailcom";
        Assert.assertThat(String.format("Email Validity Test failed for %s ", testEmail), Login.checkEmailForValidity(testEmail), is(false));
    }
}

