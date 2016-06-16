/*
 * Copyright (C) 2015 Red Hat, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.fabric8.kubeflix.examples.hellohystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.concurrent.atomic.AtomicBoolean;

public class CrashyCommand extends HystrixCommand<String> {

    private static AtomicBoolean atomicBoolean = new AtomicBoolean();

    protected CrashyCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("CrashyGroup"));
    }

    @Override
    protected String run() throws Exception {

        if (atomicBoolean.getAndSet(false)) {
            return "OK";
        } else {
            atomicBoolean.set(true);
            throw new IllegalStateException("forced");
        }
    }
}
