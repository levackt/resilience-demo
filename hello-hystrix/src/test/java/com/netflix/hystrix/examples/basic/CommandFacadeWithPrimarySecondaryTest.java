package com.netflix.hystrix.examples.basic;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandFacadeWithPrimarySecondaryTest {


    @Test
    public void testPrimary() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            ConfigurationManager.getConfigInstance().setProperty("primarySecondary.usePrimary", true);
            assertEquals("responseFromPrimary-20", new CommandFacadeWithPrimarySecondary(20).execute());
        } finally {
            context.shutdown();
            ConfigurationManager.getConfigInstance().clear();
        }
    }

    @Test
    public void testSecondary() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            ConfigurationManager.getConfigInstance().setProperty("primarySecondary.usePrimary", false);
            assertEquals("responseFromSecondary-20", new CommandFacadeWithPrimarySecondary(20).execute());
        } finally {
            context.shutdown();
            ConfigurationManager.getConfigInstance().clear();
        }
    }

}