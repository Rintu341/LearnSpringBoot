package com.sujan.config;

import org.glassfish.jersey.server.ResourceConfig;

public class RestConfig extends ResourceConfig {
    public RestConfig() {
        packages("com.sujan.controller");
    }
}
