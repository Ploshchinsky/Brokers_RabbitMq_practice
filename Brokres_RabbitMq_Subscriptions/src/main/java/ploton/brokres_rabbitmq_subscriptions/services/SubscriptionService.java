package ploton.brokres_rabbitmq_subscriptions.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ploton.integrations.ClientUserDb;
import ploton.models.NotifyDto;
import ploton.models.PublicationDto;
import ploton.models.UserDto;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableRabbit
public class SubscriptionService {
    private static final String TOPIC = "main_exchange";
    private static final String KEY_BODY = "subscriptions.notify.";
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "new_publications")
    public void listen(String message) {
        log.debug("SubscriptionService: Success Received Message - {}", message);
        PublicationDto publication = ClientUserDb.convertToPublicationDto(message);
        UserDto user = ClientUserDb.getUserByUsername(publication.getUsername());
        NotifyDto.NotifyType notifyType = getNotifyType(publication);

        List<String> subs = user.getSubs();
        for (String sub : subs) {
            UserDto receiver = ClientUserDb.getUserByUsername(sub);
            NotifyDto notify = new NotifyDto(receiver, publication, notifyType);
            String rKey = KEY_BODY + receiver.getUsername();
            try {
                String jsonNotify = new ObjectMapper().writeValueAsString(notify);
                rabbitTemplate.convertAndSend(TOPIC, KEY_BODY, jsonNotify);
                log.debug("SubscriptionService: Success Sending Message - {}, rKey - {}, topic - {}",
                        notify, rKey, TOPIC);
            } catch (JsonProcessingException e) {
                log.warn("SubscriptionService: Error Sending Message - {}", e.getMessage());
            }

        }
    }

    private static NotifyDto.NotifyType getNotifyType(PublicationDto publication) {
        if (publication.getBody().contains("Like")) {
            return NotifyDto.NotifyType.LIKE;
        } else if (publication.getBody().contains("Comment")) {
            return NotifyDto.NotifyType.COMMENT;
        }
        return NotifyDto.NotifyType.POST;
    }
}
