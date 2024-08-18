package ploton.integrations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ploton.models.NotifyDto;
import ploton.models.PublicationDto;
import ploton.models.UserDto;

import java.lang.reflect.Field;
import java.util.Map;


public class ClientUserDb {
    private static RestTemplate restTemplate;
    private static ObjectMapper objectMapper;
    private static final String URL = "http://localhost:8080/api/v1/users/";

    static {
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
    }

    public static UserDto getUserById(Long id) {
        String url = URL + "/id/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return convertToUserDto(response.getBody());
        } else {
            return null;
        }
    }

    public static UserDto getUserByUsername(String username) {
        String url = URL + "/username/" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return convertToUserDto(response.getBody());
        } else {
            return null;
        }
    }

    public static UserDto convertToUserDto(String jsonUserEntity) {
        try {
            return objectMapper.readValue(jsonUserEntity, UserDto.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static PublicationDto convertToPublicationDto(String jsonPublicationDto) {
        try {
            return objectMapper.readValue(jsonPublicationDto, PublicationDto.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static NotifyDto convertToNotifyDto(String jsonNotifyDto) {
        try {
            return objectMapper.readValue(jsonNotifyDto, NotifyDto.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
