package ploton.brokers_rabbitmq_notifications.cfg;

import lombok.RequiredArgsConstructor;
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
public class RabbitConfig {
    //Exchange
    @Value("${rabbitmq.topic.exchange.name}")
    private String topicExchangeName;
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
    Queue notifyLikeQueue() {
        return new Queue("new_like");
    }

    @Bean
    Queue notifyCommentsQueue() {
        return new Queue("new_comment");
    }

    @Bean
    Queue notifyPostQueue() {
        return new Queue("new_post");
    }

    @Bean
    Binding bindingNotifyLikeToExchange(TopicExchange topicExchange){
        return BindingBuilder.bind(notifyLikeQueue()).to(topicExchange).with("notify.like.#");
    }
    @Bean
    Binding bindingNotifyPostToExchange(TopicExchange topicExchange){
        return BindingBuilder.bind(notifyPostQueue()).to(topicExchange).with("notify.post.#");
    }
    @Bean
    Binding bindingNotifyCommentToExchange(TopicExchange topicExchange){
        return BindingBuilder.bind(notifyCommentsQueue()).to(topicExchange).with("notify.comment.#");
    }

}
