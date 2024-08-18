package ploton.brokers_rabbitmq_activity.cfg.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ploton.integrations.ClientUserDb;
import ploton.models.NotifyDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

    @RabbitListener(queues = "new_like")
    public void listenLikeQueue(String message) {
        NotifyDto notifyDto = ClientUserDb.convertToNotifyDto(message);
        log.debug("ActivityService - Likes : Success Receive Message - From {}, Type {}, Body {}",
                notifyDto.getReceiver().getUsername(), notifyDto.getType(), notifyDto.getPublication().getBody());
    }

    @RabbitListener(queues = "new_post")
    public void listenPostQueue(String message) {
        NotifyDto notifyDto = ClientUserDb.convertToNotifyDto(message);
        log.debug("ActivityService - Posts : Success Receive Message - From {}, Type {}, Body {}",
                notifyDto.getReceiver().getUsername(), notifyDto.getType(), notifyDto.getPublication().getBody());
    }

    @RabbitListener(queues = "new_comment")
    public void listenCommentQueue(String message) {
        NotifyDto notifyDto = ClientUserDb.convertToNotifyDto(message);
        log.debug("ActivityService - Comments : Success Receive Message - From {}, Type {}, Body {}",
                notifyDto.getReceiver().getUsername(), notifyDto.getType(), notifyDto.getPublication().getBody());
    }
}
