package ploton;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ploton.integrations.ClientUserDb;
import ploton.models.UserDto;

import java.util.List;

public class TestClientUserDb {
    @Test
    public void convertToUserDto_CorrectInput_UserDto() throws Exception {
        String json = "{\"id\": 1, \"username\":\"alex\", \"subs\": [\"sub1\", \"sub2\"]}";

        UserDto expected = new UserDto(1L, "alex", List.of("sub1", "sub2"));
        UserDto actual = ClientUserDb.convertToUserDto(json);

        System.out.println(actual);
        Assertions.assertEquals(expected,actual);
    }

}
