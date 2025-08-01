package org.apaas.flow.execution.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    public static final String FLOW_EXECUTION_EXCHANGE = "flow.execution.exchange";
    public static final String FLOW_EXECUTION_QUEUE = "flow.execution.queue";
    public static final String FLOW_EXECUTION_ROUTING_KEY = "flow.execution.routing.key";
    
    @Bean
    public DirectExchange flowExecutionExchange() {
        return new DirectExchange(FLOW_EXECUTION_EXCHANGE);
    }
    
    @Bean
    public Queue flowExecutionQueue() {
        return new Queue(FLOW_EXECUTION_QUEUE, true);
    }
    
    @Bean
    public Binding flowExecutionBinding() {
        return BindingBuilder.bind(flowExecutionQueue())
                .to(flowExecutionExchange())
                .with(FLOW_EXECUTION_ROUTING_KEY);
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}