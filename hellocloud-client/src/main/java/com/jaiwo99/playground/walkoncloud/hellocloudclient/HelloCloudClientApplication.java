package com.jaiwo99.playground.walkoncloud.hellocloudclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

/**
 * @author liang shi
 * @since 19.10.15
 */
@EnableCircuitBreaker
@EnableZuulProxy
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class HelloCloudClientApplication {

    @Bean
    CommandLineRunner dc(final DiscoveryClient discoveryClient) {
        return args -> discoveryClient.getInstances("hellocloud-service").forEach(si ->
            System.out.println(String.format("(%s) %s:%s", si.getServiceId(), si.getHost(), si.getPort()))
        );
    }

    @Bean
    CommandLineRunner rt(RestTemplate restTemplate) {
        return args -> {

            ResponseEntity<String> result = restTemplate.exchange("http://hellocloud-service/greetings/liang", HttpMethod.GET, null, String.class);
            System.out.println("RESULT BODY: " + result.getBody());
        };
    }

    @Bean
    CommandLineRunner fc(HelloCloudClient helloCloudClient) {
        return args -> System.out.println("RESULT BODY FROM FEIGN: " + helloCloudClient.getGreeting("alex"));
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloCloudClientApplication.class, args);
    }
}

@FeignClient("hellocloud-service")
interface HelloCloudClient {

    @RequestMapping(value = "/greetings/{name}", method = RequestMethod.GET)
    String getGreeting(@PathVariable("name") String name);
}
