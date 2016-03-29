package com.bolehunt.email;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class Main {

    private Main() { }

    /**
     * Load the Spring Integration Application Context
     *
     * @param args - command line arguments
     */
    public static void main(final String... args) throws InterruptedException {

		new ClassPathXmlApplicationContext("classpath:META-INF/spring-integration-context.xml");
    }
}
