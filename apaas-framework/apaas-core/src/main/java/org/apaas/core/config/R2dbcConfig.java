package org.apaas.core.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

@Configuration
public class R2dbcConfig extends AbstractR2dbcConfiguration {
    
    private final ConnectionFactory connectionFactory;
    
    public R2dbcConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
    
    @Override
    public ConnectionFactory connectionFactory() {
        return connectionFactory;
    }
}