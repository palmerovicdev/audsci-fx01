package com.suehay.audscifx.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Injector {
    public static ApplicationContext getContext() {
        return new AnnotationConfigApplicationContext("org.suehay");
    }
}