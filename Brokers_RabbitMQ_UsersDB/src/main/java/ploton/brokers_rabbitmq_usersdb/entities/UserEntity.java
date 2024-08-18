package ploton.brokers_rabbitmq_usersdb.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @ElementCollection
    @CollectionTable(
            name = "user_subs",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "sub_username")
    private List<String> subs;

}
