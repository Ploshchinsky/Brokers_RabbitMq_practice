package ploton.brokres_rabbitmq_subscriptions.cfg;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RabbitConfig {
    //Exchange
    @Value("${rabbitmq.topic.exchange.name}")
    private String topicExchangeName;
    //RoutingKeys
    @Value("${rabbitmq.routingKey.output.name}")
    private String outputRoutingKey;
    //Queues
    @Value("${rabbitmq.queue.output.name}")
    private String outputQueue;

    @Value("${rabbitmq.username}")
    private String username;
    @Value("${rabbitmq.password}")
    private String password;
    @Value("${rabbitmq.address}")
    private String address;

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(address);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    @Bean
    AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(topicExchangeName, true, false);
    }

    @Bean
    Queue newSubscriptionsNotify() {
        return new Queue(outputQueue, false);
    }

    @Bean
    Binding bindingSubscriptionsNotifyQueue(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(outputRoutingKey);
    }

}
