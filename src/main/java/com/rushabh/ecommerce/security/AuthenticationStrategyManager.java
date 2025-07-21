package com.rushabh.ecommerce.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AuthenticationStrategyManager {

    private final Map<String, AuthenticationStrategy> strategies = new ConcurrentHashMap<>();
    private final String configuredStrategy;


    public AuthenticationStrategyManager(List<AuthenticationStrategy> strategyList, @Value("${app.auth.strategy:JWT}") String configuredStrategy) {
        this.configuredStrategy = configuredStrategy;

        strategyList.forEach(strategy -> strategies.put(strategy.getStrategyName(), strategy));

        // Validate that the configured strategy exists
        if (!strategies.containsKey(configuredStrategy)) {
            throw new IllegalArgumentException("Configured authentication strategy '" + configuredStrategy + "' not found. Available strategies: " + strategies.keySet());
        }
    }

    public AuthenticationStrategy getCurrentStrategy() {
        return strategies.get(configuredStrategy);
    }

    public String getCurrentStrategyName() {
        return configuredStrategy;
    }


}
