package com.jaiwo99.playground.walkoncloud.hellocloudservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liang shi
 * @since 19.10.15
 */
@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class HelloCloudServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloCloudServiceApplication.class, args);
    }

    @RequestMapping(value = "/greetings/{name}", method = RequestMethod.GET)
    String hellocloud(@PathVariable String name) {
        return name + " is walking on cloud";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    String home() {
        return "it works";
    }
}
