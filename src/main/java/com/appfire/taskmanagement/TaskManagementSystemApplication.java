package com.appfire.taskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;

import java.util.concurrent.atomic.AtomicReference;

@SpringBootApplication
public class TaskManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TaskManagementSystemApplication.class);
        AtomicReference<String> indexPageLink= new AtomicReference<>("");
        application.addListeners((ApplicationListener<WebServerInitializedEvent>) event -> {
            int localPort = event.getWebServer().getPort();
            indexPageLink.set("http://localhost:" + localPort);
        });
        application.run(args);
        System.out.println(indexPageLink.get());
    }

}
