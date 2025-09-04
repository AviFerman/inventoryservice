package com.orderprocessing.inventoryservice.config;

import com.orderprocessing.inventoryservice.strategy.ProviderStrategyFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ServiceLocatorFactoryBean providerStrategyFactory() {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(ProviderStrategyFactory.class);
        return factoryBean;
    }
}
