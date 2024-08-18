package ploton.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifyDto {
    private UserDto receiver;
    private PublicationDto publication;
    private NotifyType type;

    public static enum NotifyType {
        POST, LIKE, COMMENT;
    }
}
