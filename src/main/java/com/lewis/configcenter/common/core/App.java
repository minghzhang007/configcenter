package com.lewis.configcenter.common.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class App implements ApplicationContextAware, EnvironmentAware {

    private ApplicationContext appCtx;

    private Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appCtx = applicationContext;
    }

    public ApplicationContext getAppCtx() {
        return this.appCtx;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public String activeProfile() {
        if (this.environment != null) {
            String[] activeProfiles = this.environment.getActiveProfiles();
            return activeProfiles[0];
        }
        return null;
    }
}
