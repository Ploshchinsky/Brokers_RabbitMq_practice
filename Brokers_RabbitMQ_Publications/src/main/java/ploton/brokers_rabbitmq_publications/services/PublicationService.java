package ploton.brokers_rabbitmq_publications.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ploton.integrations.ClientUserDb;
import ploton.models.PublicationDto;
import ploton.models.UserDto;

import java.util.List;
import java.util.Random;
import java.util.stream.LongStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class PublicationService {
    private static final String TOPIC = "main_exchange";
    private static final String KEY = "publications.new.";

    private final RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void publicationService() {
        List<PublicationDto> publications = LongStream.range(1, 6)
                .mapToObj(number -> {
                    UserDto user = ClientUserDb.getUserById(number);
                    String body = generateBodyMessage(number) + number;
                    return new PublicationDto(number, user.getUsername(), body);
                })
                .toList();

        publications.forEach(message -> {
            String jsonPublicationDto = null;
            try {
                String routingKey = KEY + message.getUsername();
                jsonPublicationDto = new ObjectMapper().writeValueAsString(message);
                rabbitTemplate.convertAndSend(TOPIC, routingKey, jsonPublicationDto);
                log.debug("PublicationService: Sending message to Rabbit - {}, routingKey - {}, topic - {}"
                        , message, routingKey, TOPIC);
            } catch (JsonProcessingException e) {
                log.warn("PublicationService: Error Sending Message - " + e.getMessage());
            }
        });
    }

    private String generateBodyMessage(long number) {
        if (number % 2 == 0) {
            return "Publication ID - ";
        } else if (number % 3 == 0) {
            return "Like ID - ";
        } else if (number % 5 == 0) {
            return "Comment ID - ";
        }
        return "Post ID - ";
    }

}
