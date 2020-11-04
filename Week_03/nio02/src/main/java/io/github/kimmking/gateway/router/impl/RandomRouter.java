package io.github.kimmking.gateway.router.impl;

import io.github.kimmking.gateway.router.HttpEndpointRouter;

import java.util.List;
import java.util.Random;

public class RandomRouter implements HttpEndpointRouter {
    @Override
    public String route(List<String> endpoints) {
        Random random = new Random(System.currentTimeMillis());
        String selectedEndpoint = endpoints.get(random.nextInt(endpoints.size()));
        System.out.println(String.format("select endpoint:%s, from endpoints:%s", selectedEndpoint, endpoints));
        return selectedEndpoint;
    }
}
