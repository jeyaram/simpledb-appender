/*
 * Copyright 2009-2010 Kikini Limited and contributors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.kikini.logging.simpledb;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.ImmutableMap;

/**
 * Data representation of a row to be written to SimpleDB. Implements
 * {@link Delayed} so it may be added to a {@link BlockingQueue}.
 * 
 * @author Gabe Nell
 */
class SimpleDBRow implements Delayed {

    private final Delayed delayed;

    // Properties
    private String msg;
    private String host;
    private String context;
    private String logger;
    private String level;
    private long time;
    private String threadName;
    private Map<String, String> mdcPropertyMap;

    SimpleDBRow(String msg, String host, String context, String logger, String level, String threadName, long time, long granularity, Map<String, String> mdcPropertyMap) {
        this.msg = msg;
        this.host = host;
        this.context = context;
        this.logger = logger;
        this.level = level;
        this.threadName = threadName; 
        this.time = time;
        this.delayed = new GranularDelay(granularity);
        this.mdcPropertyMap = ImmutableMap.copyOf(mdcPropertyMap);
    }

    public String getMsg() {
        return msg;
    }

    public String getHost() {
        return host;
    }

    public String getContext() {
        return context;
    }

    public String getLogger() {
        return logger;
    }

    public String getLevel() {
        return level;
    }

    public String getThreadName() {
        return threadName;
    }

    public long getTime() {
        return time;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return delayed.getDelay(unit);
    }

    @Override
    public int compareTo(Delayed o) {
        return delayed.compareTo(o);
    }

    public Map<String, String> getMDCPropertyMap() {
        return mdcPropertyMap;
    }
}
