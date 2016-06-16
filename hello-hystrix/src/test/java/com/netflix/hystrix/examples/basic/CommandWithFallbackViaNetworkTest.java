package com.netflix.hystrix.examples.basic;

import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixInvokableInfo;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandWithFallbackViaNetworkTest {

    @Test
    public void test() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            assertEquals(null, new CommandWithFallbackViaNetwork(1).execute());

            HystrixInvokableInfo<?> command1 = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().toArray(new HystrixInvokableInfo<?>[2])[0];
            assertEquals("GetValueCommand", command1.getCommandKey().name());
            assertTrue(command1.getExecutionEvents().contains(HystrixEventType.FAILURE));

            HystrixInvokableInfo<?> command2 = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().toArray(new HystrixInvokableInfo<?>[2])[1];
            assertEquals("GetValueFallbackCommand", command2.getCommandKey().name());
            assertTrue(command2.getExecutionEvents().contains(HystrixEventType.FAILURE));
        } finally {
            context.shutdown();
        }
    }
}