package com.netflix.hystrix.examples.basic;

import org.junit.Test;

import static org.junit.Assert.*;

public class CommandWithStubbedFallbackTest {

    @Test
    public void test() {
        CommandWithStubbedFallback command = new CommandWithStubbedFallback(1234, "ca");
        CommandWithStubbedFallback.UserAccount account = command.execute();
        assertTrue(command.isFailedExecution());
        assertTrue(command.isResponseFromFallback());
        assertEquals(1234, account.getCustomerId());
        assertEquals("ca", account.getCountryCode());
        assertEquals(true, account.isFeatureXPermitted());
        assertEquals(true, account.isFeatureYPermitted());
        assertEquals(false, account.isFeatureZPermitted());
    }
}