package ploton.brokers_rabbitmq_notifications.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ploton.integrations.ClientUserDb;
import ploton.models.NotifyDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final static String TOPIC = "main_exchange";
    private final static String KEY_BODY = "notify.";

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "subscriptions_notify")
    public void listen(String message) {
        log.debug("NotifyService: Success Received Message - {}", message);
        NotifyDto notify = ClientUserDb.convertToNotifyDto(message);

        try {
            String jsonNotify = new ObjectMapper().writeValueAsString(notify);
            String rKey = getRoutingKey(notify);

            rabbitTemplate.convertAndSend(TOPIC, rKey, jsonNotify);
            log.debug("NotifyService: Success Sending Message - {}, rKey - {}, topic - {}"
                    , jsonNotify, rKey, TOPIC);
        } catch (JsonProcessingException e) {
            log.warn("NotifyService: Error Received Message - {}", e.getMessage());
        }
    }

    private static String getRoutingKey(NotifyDto notify) {
        String rKey = null;
        String username = notify.getReceiver().getUsername();
        switch (notify.getType()) {
            case POST -> rKey = KEY_BODY + "post." + username;
            case LIKE -> rKey = KEY_BODY + "like." + username;
            case COMMENT -> rKey = KEY_BODY + "comment." + username;
        }
        return rKey;
    }

}
